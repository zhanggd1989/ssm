<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>待办任务信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
 
	<script type="text/javascript">
		// 查询任务
		function searchTask(){
			$('#dg').datagrid('load',{
				realName: $('#name').val()
			});
		}
		// 弹出办理任务的界面
		function openTaskForm(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '办理任务');
			$.ajax({  
	            type: "POST",  
	            url: '${ctx}/leaveBill/viewLeaveBillByTaskId?taskId=' + row.id, 
	            cache: false,  
	            dataType : "json",  
	            success: function( data ) {  
	            	$('#fm').form('load', data);  
					$("#taskId").val(row.id);// 任务ID
					$("#applicantId").val(row.assigneeId);// 申请人ID
					$("#applicantName").val(row.assignee);// 申请人名称
				}  
			});
		}
		// 提交任务
		$(function(){
		    $('#btn').bind('click', function(){
		    	var outcome = $('#btn').val();
				$('#fm').form('submit', {
						url : '${ctx}/workFlow/submitTask',
						onSubmit : function(param) {
							param.outcome = outcome;
							return $(this).form('validate');
						},
						success : function(result) {
							var result = eval('(' + result + ')');
							if (result.success) {
								$.messager.alert("提示信息","提交成功");
								$('#dlg').dialog('close');
								$('#dg').datagrid('reload');
							} else {
								$.messager.alert("提示信息",result.errorMsg);
							}
						}
					});
		    });
		});
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
					str += '<a href="#" onclick="openTaskForm(' + rowIndex + ')">办理任务</a>'
				}
				if (true) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a target="_blank"  href="${ctx}/workFlow/viewCurrentImage?taskId=' + rowData['id'] + '">查看当前流程图</a>'
				}
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
			data-options="url:'${ctx}/workFlow/taskTodoGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
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
		
		<div id="dlg" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true'" 
		style="width:700px; height:600px;">
		<form id="fm" method="post">
			<h3>请假信息</h3>
			<hr style="height:3px;border:none;border-top:3px ridge black;" />
			<table class="fitem" >
				<tr>
					<!-- 任务ID -->
					<input id="taskId" name="taskId"  type="hidden">
					<!-- 请假单ID -->
					<input name="id" type="hidden">
					<!-- 申请人ID -->
					<input id="applicantId" name="applicant.id" type="hidden">
					<td>申请人</td>
					<td><input id="applicantName" name="applicant.name" class="easyui-validatebox" readonly="true"></td>
					<td>申请时间</td>
					<td><input name="applyTime" class="easyui-validatebox" readonly="true"></td>
				</tr>
				<tr>
					<td>开始时间</td> 
					<td><input name="startTime" class="easyui-validatebox" readonly="true"></td>
					<td>结束时间</td>
					<td><input name="endTime" class="easyui-validatebox" readonly="true"></td>
				</tr>
				<tr>
					<td>请假事由</td>
					<td colspan="3"><textarea name="content" class="easyui-validatebox" style="width:100%;height:45px" readonly="true"></textarea></td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3"><textarea name="remarks" class="easyui-validatebox" style="width:100%;height:45px" readonly="true"></textarea></td>
				</tr>
				<tr>
					<td>审批人</td>
					<td  colspan="3"><input id="approval" 
										class="easyui-combobox" 
										name="approval"  
										data-options="valueField:'id',textField:'text',url:'${ctx}/user/userTree',panelHeight:'auto', required: 'true'"
										style="width:100%">
					</td>
				</tr>
				<tr>
					<td>批注</td>
					<td colspan="3"><textarea name="comment" class="easyui-validatebox" style="width:100%;height:45px" required="true"></textarea></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align:center;">
					<!-- 使用连线的名称作为按钮 -->
			 		<c:forEach var="outcome" items="${sessionScope.outcomeList}">
			 			<input id="btn" type="button" value='${outcome}'/> 
			 		</c:forEach> 
					</td>
				</tr>
			</table>
		</form>
	</div>
	
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