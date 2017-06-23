<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>资源信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>

	<script type="text/javascript">
		// 弹出添加资源界面
		function newResource() {
			$('#dlg').dialog('open').dialog('setTitle', '添加资源');
			$('#fm').form('clear');
			url = '${ctx}/resource/addResource';
		};
		// 弹出修改资源界面
		function editResource(index) {
			$('#dg').treegrid('select',index);
			var row = $('#dg').treegrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '修改资源');
			$('#fm').form('load', row);
			$("#parentResource").combotree('setValue', row['parent']["id"]);
			url = '${ctx}/resource/editResource?id=' + row.id;
		}
		// 保存资源
		function saveReosurce() {
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
		// 删除资源
		function deleteResource(index) {
			$('#dg').treegrid('select',index);
			var row = $('#dg').treegrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该资源及子资源?', function(r) {
					if (r) {
						$.post('${ctx}/resource/deleteResource', { id : row.id }, function(result) {
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
		// 查询资源
		function searchResource(){
			$('#dg').treegrid('load',{
				name: $('#name').val()
			});
		}
		// 格式化
		function formatType(value, rowData, rowIndex){// 资源类型
			if (value == 0) {
				return "菜单";
			} else if (value == 1) {
				return "按钮";
			}
		};
		function formatUseFlag(value, rowData, rowIndex){// 是否可用
			if (value == 0) {
				return "是";
			} else if (value == 1) {
				return "否";
			}
		};
		// 格式化
		function actionType (value, rowData) {// 操作
			var str = '';
			if(true){
				if (true) {
					str += '<a href="#" onclick="editResource(' + rowData.id + ')">编辑</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="deleteResource(' + rowData.id + ')">删除</a>'
				}
			}
			return str;
		}
	</script>
<body>

	<div id="tb" style="padding:3px">
			<span>名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchResource()">查询</a>
	</div>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newResource()">新增</a> 
	</div>

	<table id="dg" 
		class="easyui-treegrid" 
		data-options="url:'${ctx}/resource/dataGrid', toolbar:'#toolbar', rownumbers:'true', idField:'id', treeField:'name'"
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="name" width="200">资源名称</th>
				<th field="type" formatter="formatType" width="100">资源类型</th>
				<th field="href" width="300">资源链接</th>
				<th field="permission" width="200">资源权限</th>
				<th field="useFlag" formatter="formatUseFlag" width="100">是否可用</th>
				<th field="remarks" width="200">备注</th>
				<th field="action" formatter="actionType" width="100">操作</th>
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
					<td>资源名称</td>
					<td><input name="name" class="easyui-validatebox" required="required"></td>
					<td>资源类型</td>
					<td>
						<select id="type" class="easyui-combobox" name="type" panelHeight="auto" style="width:170px;">
							<option value="0">菜单</option>
							<option value="1">按钮</option>
						</select>
					</td>
					
				</tr>
				<tr>
					<td>资源链接</td>
					<td><input name="href" class="easyui-validatebox" required="required"></td>
					<td>资源权限</td> 
					<td><input name="permission" class="easyui-validatebox" required="required"></td>
				</tr>
				<tr>
					<td>所属资源</td>
					<td><select id= "parentResource" class="easyui-combotree" name="parent.id" data-options="url:'${ctx}/resource/resourceTreeByRoleId?roleId=0', panelHeight:'auto', required:'required'" style="width:170px;"></select></td>
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
					<td colspan="3"><input name="remarks" class="easyui-validatebox" style="height:45px;width:100%"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReosurce()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
</body>
</html>