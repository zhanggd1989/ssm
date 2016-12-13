<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>已办任务信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
	// 查询任务
	function searchTask(){
		$('#dg').datagrid('load',{
			realName: $('#name').val()
		});
	}
	// 查看批注信息
	function viewCommentMessage(index) {
		$('#dg').datagrid('selectRow',index);
		var row = $('#dg').datagrid('getSelected');
		$('#dlg1').dialog('open').dialog('setTitle', '批注信息');
		$('#dg1').datagrid({
		    url:'${ctx}/workFlow/viewHisCommentByTaskId?taskId=' + row.id
		})
	}
	// 格式化
	function actionType (value, rowData, rowIndex) {// 操作
		var str = '';
		if(true) {
			if (true) {
				str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
				str += '<a href="#" onclick="viewCommentMessage(' + rowIndex + ')">查看审核信息</a>'
			}
		}
		return str;
	}
	</script>
	
	<body>
		<div id="tb" style="padding:3px">
			<span>任务名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTask()">查询</a>
		</div>
		
		<table id="dg" 
			class="easyui-datagrid" 
			data-options="url:'${ctx}/workFlow/taskDoneGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
			style="width:100%; height:600px;">
			<thead>
				<tr>
					<th id = "id" field="id" width="100">任务ID</th>
					<th field="name" width="200">任务名称</th>
					<th field="createTime" width="300">创建时间</th>
					<th field="assignee" width="200">办理人</th>
					<th field="action" formatter="actionType" width="250">操作</th>
				</tr>
			</thead>
		</table>
		
		<div id="dlg1" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true'" 
		style="width:700px; height:400px;">
			<table id="dg1" 
			class="easyui-datagrid" 
			data-options="rownumbers:'true', singleSelect:'true'"
			style="height:100%;">
				<thead>
					<tr>
						<th field="time" width="100">时间</th>
						<th field="userId" width="200">批注人</th>
						<th field="fullMessage" width="300">批注信息</th>
					</tr>
				</thead>
			</table>
	</div>
	
	</body>