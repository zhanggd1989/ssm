<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
	<head>
	<title>用户信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>

	<script type="text/javascript">
		// 初始化所属单位+所属部门，支持联动
		$(function() {  
			var companyId = '';
			$('#company').combotree({
				onClick : function(){  
		            $("#department").combotree("setValue",'');  
		            var companyId = $('#company').combotree('getValue');   
		              
		            $.ajax({  
			            type: "POST",  
			            url: '${ctx}/organization/departmentTreeByCompanyId?companyId=' + companyId, 
			            cache: false,  
			            dataType : "json",  
			            success: function( data ) {  
			            	$("#department").combotree("loadData",data);  
						}  
					});       
				} 
			});
		})
		// 弹出添加用户界面
		function newUser() {
			$('#dlg').dialog('open').dialog('setTitle', '添加用户');
			$('#fm').form('clear');
			$('#loginName').attr("disabled",false);
			$('#password').attr("disabled",false);
			url = '${ctx}/user/addUser';
		}
		// 弹出修改用户界面
		function editUser(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '修改用户');
			$('#fm').form('load', row);
			$("#company").combotree('setValue', row['company']["id"]);
			$("#department").combotree('setValue', row['department']["id"]);
			$('#loginName').attr("disabled",true);
			$('#password').attr("disabled",true);
			url = '${ctx}/user/editUser?id=' + row.id;
		}
		// 保存用户
		function saveUser() {
			$('#fm').form('submit', {
				url : url,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.success) {
						$.messager.alert("提示信息","保存成功");
						$('#dlg').dialog('close');
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
			});
		}
		// 删除用户
		function deleteUser(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该用户?', function(r) {
					if (r) {
						$.post('${ctx}/user/deleteUser', { id : row.id }, function(result) {
							if (result.success) {
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({
									title : 'Error',
									msg : result.errorMsg
								});
							}
						}, 'json');
					}
				});
			}
		}
		// 查询用户
		function searchUser(){
			$('#dg').datagrid('load',{
				realName: $('#realName').val()
			});
		}
		// 格式化
		function formatType(value, rowData, rowIndex){// 类型
			if (value == 0) {
				return "管理员";
			} else if (value == 1) {
				return "普通用户";
			}
		};
		function formatCompany(value, rowData, rowIndex){// 单位
			return rowData["company"]["name"];
		};
		function formatDepartment(value, rowData, rowIndex){// 部门
				return rowData["department"]["name"];
		};
		function formatUseFlag(value, rowData, rowIndex){// 是否可用
			if (value == 0) {
				return "是";
			} else if (value == 1) {
				return "否";
			}
		};
		function actionType (value, rowData, rowIndex) {// 操作
			var str = '';
			if(true){
				if (true) {
					str += '<a href="#" onclick="editUser(' + rowIndex + ')">修改</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="deleteUser(' + rowIndex + ')">删除</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="grantRole(' + rowIndex + ')">授予角色</a>'
				}
			}
			return str;
		}
		// 弹出授予角色界面
		function grantRole(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg1').dialog('open').dialog('setTitle', '选择角色');
			url = '${ctx}/role/saveRolesByUserId?userId=' + row.id;
			$('#tt').tree({
			    url:'${ctx}/role/roleTreeByUserId?userId=' + row.id,
			    checkbox:true
			});
		}
		// 保存用户-角色关系
		function saveUserAndRole() {
			var nodes = $('#tt').tree('getChecked');
			var roleIds = '';
			for(var i=0; i<nodes.length; i++) {
	            if (roleIds != '') {
	            	roleIds += ',';
	            }
	            roleIds += nodes[i].id;
			}
			$.post(url, { roleIds : roleIds }, function(result) {
				if (result.success) {
					$.messager.alert("提示信息","授权成功");
					$('#dlg1').dialog('close');
				} else {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}, 'json');
		}
		// 全选
		function checkAll() {
			var nodes = $('#tt').tree('getChecked', 'unchecked');
			if (nodes && nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					$('#tt').tree('check', nodes[i].target);
				}
			}
		}
		// 取消
		function uncheckAll() {
			var nodes = $('#tt').tree('getChecked');
			if (nodes && nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					$('#tt').tree('uncheck', nodes[i].target);
				}
			}
		}
		// 反选
		function checkInverse() {
			var unchecknodes = $('#tt').tree('getChecked', 'unchecked');
			var checknodes = $('#tt').tree('getChecked');
			if (unchecknodes && unchecknodes.length > 0) {
				for ( var i = 0; i < unchecknodes.length; i++) {
					$('#tt').tree('check', unchecknodes[i].target);
				}
			}
			if (checknodes && checknodes.length > 0) {
				for ( var i = 0; i < checknodes.length; i++) {
					$('#tt').tree('uncheck', checknodes[i].target);
				}
			}
		}
	</script>

<body >

	<div id="tb" style="padding:3px">
			<span>姓名:</span>
			<input id="realName" style="line-height:20px; border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser()">查询</a>
	</div>
	
	<hr>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newUser()">新增</a> 
	</div>
	
	<table id="dg" 
		class="easyui-datagrid" 
		data-options="url:'${ctx}/user/dataGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="loginName" width="100">登录名</th>
				<th field="realName" width="100">姓名</th>
				<th field="type" formatter="formatType" width="100">类型</th>
				<th field="company.name" formatter="formatCompany" width="150">所属单位</th>
				<th field="department.name" formatter="formatDepartment" width="150">所属部门</th>
				<th field="sex" width="100">性别</th>
				<th field="email" width="100">邮箱</th>
				<th field="phone" width="100">电话</th>
				<th field="fax" width="100">传真</th>
				<th field="useFlag" formatter="formatUseFlag" width="100">是否可用</th>
				<th field="remarks" width="200">备注</th>
				<th field="action" formatter="actionType" width="250">操作</th>
			</tr>
		</thead>
	</table>

	<div id="dlg" 
		 class="easyui-dialog" 
		 data-options="iconCls:'icon-save', closed:'true', buttons:'#dlg-buttons'" 
		 style="width:650px; height:400px; padding:10px;">
		<form id="fm" method="post">
			<table class="fitem">
				<tr>
					<td>登录名</td>
					<td><input id="loginName" name="loginName" class="easyui-validatebox" required="required"></td>
					<td>密码</td>
					<td><input id="password" name="password" type="password" class="easyui-validatebox" required="required"></td>
				</tr>
				<tr>
					<td>姓名</td> 
					<td><input name="realName" class="easyui-validatebox" required="required"></td>
					<td>类型</td> 
					<td>
						<select id="type" class="easyui-combobox" name="type" panelHeight="auto" style="width:170px;">
							<option value="0">管理员</option>
							<option value="1">普通用户</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>所属单位</td> 
					<td><select id="company" class="easyui-combotree" name="company.id" data-options="url:'${ctx}/organization/organizationTree', panelHeight:'auto', required:'true'"></select></td>
					<td>所属部门</td> 
					<td><select id="department" class="easyui-combotree" name="department.id" data-options="panelHeight:'auto', required:'true'" style="width:170px"></select></td>
				</tr>
				<tr>
					<td>性别</td> 
					<td>
						<select id="sex" class="easyui-combobox" name="sex" panelHeight="auto" style="width:170px;">
							<option value="0">男</option>
							<option value="1">女</option>
						</select>
					</td>
					<td>邮箱</td> 
					<td><input name="email" class="easyui-validatebox" validType="email"></td>
				</tr>
				<tr>
					<td>电话</td> 
					<td><input name="phone" class="easyui-validatebox"></td>
					<td>传真</td> 
					<td><input name="fax" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>是否可用</td> 
					<td>
						<select id="useFlag" class="easyui-combobox" name="useFlag" panelHeight="auto" style="width:170px;">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>备注</td> 
					<td colspan="3"><textarea name="remarks" class="easyui-validatebox" style="height:45px;width:100%"></textarea></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

	<div id="dlg1" class="easyui-dialog" data-options="iconCls:'icon-save'" style="width: 300px; height: 600px; padding: 10px;" closed="true" buttons="#dlg1-buttons">
		<div class="easyui-layout" style="width:100%;height:100%;" fit="true">
		    <div data-options="region:'north'" style="height:30px; text-align:left">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="checkAll()">全选</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="checkInverse()">反选</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="uncheckAll()">取消</a>
			</div>
			<div data-options="region:'center'" style="height: 100%;">
				<ul id="tt"></ul>
			</div>
			<div data-options="region:'south'" style="height: 30px; text-align:right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveUserAndRole()">保存</a>
			</div>
		</div>
	</div>
	
</body>
</html>