<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>角色信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
		// 弹出添加角色界面
		function newRole() {
			$('#dlg').dialog('open').dialog('setTitle', '添加角色');
			$('#fm').form('clear');
			url = '${ctx}/role/addRole';
		}
		// 弹出修改角色界面
		function editRole(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '修改角色');
			$('#fm').form('load', row);
			url = '${ctx}/role/editRole?id=' + row.id;
		}
		// 保存角色
		function saveRole() {
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
		// 删除角色
		function deleteRole(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该角色?', function(r) {
					if (r) {
						$.post('${ctx}/role/deleteRole', { id : row.id }, function(result) {
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
		// 查询角色
		function searchRole(){
			$('#dg').datagrid('load',{
				name: $('#name').val()
			});
		}
		// 格式化
		function formatType(value, rowData, rowIndex){// 角色类型
			if (value == 0) {
				return "超级角色";
			} else if (value == 1) {
				return "普通角色";
			}
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
					str += '<a href="#" onclick="editRole(' + rowIndex + ')">修改</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="deleteRole(' + rowIndex + ')">删除</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="grantResource(' + rowIndex + ')">授予资源</a>'
				}
			}
			return str;
		}
		// 弹出授予资源界面
		function grantResource(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg1').dialog('open').dialog('setTitle', '选择资源');
			url = '${ctx}/resource/saveResourcesByRoleId?roleId=' + row.id;
			$('#tt').tree({
			    url:'${ctx}/resource/resourceTreeByRoleId?roleId=' + row.id,
			    checkbox:true
			});
		}
		// 保存角色和资源的关系
		function saveRoleAndResource() {
			var nodes = $('#tt').tree('getChecked');
			var resourceIds = '';
			for(var i=0; i<nodes.length; i++){
				if (resourceIds != '') {
	            	resourceIds += ',';
	            } 
				resourceIds += nodes[i].id;
			}
			$.post(url, { resourceIds : resourceIds }, function(result) {
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
<body>

	<div id="tb" style="padding:3px">
			<span>名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchRole()">查询</a>
	</div>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newRole()">新增</a> 
	</div>

	<table id="dg" 
		class="easyui-datagrid" 
		data-options="url:'${ctx}/role/dataGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="name" width="200">角色名称</th>
				<th field="type" formatter="formatType" width="100">角色类型</th>
				<th field="dataScope" width="100">数据范围</th>
				<th field="useFlag" formatter="formatUseFlag" width="100">是否可用</th>
				<th field="remarks" width="200">备注</th>
				<th field="action" formatter="actionType" width="150">操作</th>
			</tr>
		</thead>
	</table>

	<div id="dlg" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true', buttons:'#dlg-buttons'" 
		style="width: 600px; height: 370px; padding: 10px;">
		<form id="fm" method="post">
			<table class="fitem">
				<tr>
					<td>角色名称</td>
					<td><input name="name" class="easyui-validatebox" required="required"></td>
					<td>类型</td>
					<td>
						<select id="type" class="easyui-combobox" name="type" panelHeight="auto" style="width:170px;">
							<option value="0">超级角色</option>
							<option value="1">普通角色</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>数据范围</td> 
					<td><input name="dataScope" class="easyui-validatebox" required="required"></td>
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
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">保存</a> 
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
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveRoleAndResource()">保存</a>
			</div>
		</div>
	</div>

</body>
</html>