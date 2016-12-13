<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>流程部署信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
		// 删除部署的流程
		function deleteDeployment(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该流程?', function(r) {
					if (r) {
						$.post('${ctx}/workFlow/deleteDeployment', { id : row.id }, function(result) {
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
		// 格式化
		function actionType (value, rowData, rowIndex) {// 操作
			var str = '';
			if(true){
				if (true) {
					str += '<a href="#" onclick="deleteDeployment(' + rowIndex + ')">删除</a>'
				}
			}
			return str;
		}
	</script>
	
	<body>
		<div id="tb" style="padding:3px">
			<span>名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDefination()">查询</a>
		</div>
	
		<table id="dg" 
			class="easyui-datagrid" 
			data-options="url:'${ctx}/workFlow/deploymentGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
			style="width:100%; height:600px;">
			<thead>
				<tr>
					<th field="id" width="100">ID</th>
					<th field="name" width="200">流程名称</th>
					<th field="deploymentTime" width="100">发布时间</th>
					<th field="action" formatter="actionType" width="100">操作</th>
				</tr>
			</thead>
		</table>
	
	</body>