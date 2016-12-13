<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>机构信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
		// 弹出添加机构界面
		function newOrganization() {
			$('#dlg').dialog('open').dialog('setTitle', '添加机构');
			$('#fm').form('clear');
			url = '${ctx}/organization/addOrganization';
		};
		// 弹出修改机构界面
		function editOrganization(index) {
			$('#dg').treegrid('select',index);
			var row = $('#dg').treegrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '修改机构');
			$('#fm').form('load', row);
			$("#master").combobox('setValue', row['master']["id"]);
			$("#parentOrganization").combotree('setValue', row['parent']["id"]);
			url = '${ctx}/organization/editOrganization?id=' + row.id;
		}
		// 保存机构
		function saveOrganization() {
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
						$('#dg').treegrid('reload');
					} else {
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
			});
		}
		// 删除机构
		function deleteOrganization(index) {
			$('#dg').treegrid('select',index);
			var row = $('#dg').treegrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该机构及子机构?', function(r) {
					if (r) {
						$.post('${ctx}/organization/deleteOrganization', { id : row.id }, function(result) {
							if (result.success) {
								$('#dg').treegrid('reload');
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
		// 查询机构
		function searchOrganization(){
			$('#dg').treegrid('load',{
				name: $('#name').val()
			});
		}
		// 格式化
		function formatType(value, rowData, rowIndex) {// 机构类型
			if(value == 0) {
				return "单位";
			} else if (value == 1) {
				return "部门";
			}
		}
		function formatMaster(value, rowData, rowIndex){// 负责人
			return rowData["master"]["realName"];
		};
		function formatUseFlag(value, rowData, rowIndex) {// 是否可用
			if(value == 0) {
				return "是";
			} else if (value == 1) {
				return "否";
			}
		}
		function actionType (value, rowData) {// 操作
			var str = '';
			if(true){
				if (true) {
					str += '<a href="#" onclick="editOrganization(' + rowData.id + ')">编辑</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="deleteOrganization(' + rowData.id + ')">删除</a>'
				}
			}
			return str;
		}
	</script>

<body>

	<div id="tb" style="padding:3px">
			<span>名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchOrganization()">查询</a>
	</div>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newOrganization()">新增</a> 
	</div>

	<table id="dg" 
		class="easyui-treegrid"
		data-options="url:'${ctx}/organization/dataGrid', toolbar:'#toolbar', rownumbers:'true', idField:'id', treeField:'name'" 
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="name" width="200">机构名称</th>
				<th field="type" formatter="formatType" width="100">机构类型</th>
				<th field="postcode" width="100">邮政编码</th>
				<th field="address" width="100">联系地址</th>
				<th field="master.name" formatter="formatMaster" width="100">负责人</th>
				<th field="useFlag" formatter="formatUseFlag" width="100">是否可用</th>
				<th field="remarks" width="200">备注</th>
				<th field="action" formatter="actionType" width="100">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="dlg" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true', buttons:'#dlg-buttons'" 
		style="width:600px; height:370px; padding:10px;">
		<form id="fm" method="post">
			<table class="fitem">
				<tr>
					<td>机构名称</td>
					<td><input name="name" class="easyui-validatebox" required="required"></td>
					<td>机构类型</td> 
					<td>
						<select id="type" class="easyui-combobox" name="type" panelHeight="auto" style="width:170px;">
							<option value="0">单位</option>
							<option value="1">部门</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>邮政编码</td> 
					<td><input name="postcode" class="easyui-validatebox"></td>
					<td>联系地址</td> 
					<td><input name="address" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>负责人</td> 
					<td><input id="master" class="easyui-combobox" name="master.id" data-options="url:'${ctx}/user/userTree', valueField:'id', textField:'text', panelHeight:'auto'"></td>
					<td>是否可用</td> 
					<td>
						<select id="useFlag" class="easyui-combobox" name="useFlag" panelHeight="auto" style="width:170px;">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>所属机构</td>
					<td><select id="parentOrganization" class="easyui-combotree" name="parent.id" data-options="url:'${ctx}/organization/organizationTree', panelHeight:'auto', required:'required'" style="width:170px;"></select></td>
				</tr>
				<tr>
					<td>备注</td> 
					<td colspan="3"><input name="remarks" class="easyui-validatebox" style="height:45px;width:100%"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrganization()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

</body>
</html>