<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>请假信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
		// 初始化日期格式
		$(function(){  
			$('#startTime').datebox({
			    formatter: function(date){ 
			    	return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();},
				parser: function(date) {
					var t = Date.parse(date);
					if (!isNaN(t)){
						return new Date(t);
					} else {
						return new Date();
					}
				}
			});
			$('#endTime').datebox({
			    formatter: function(date){ 
			    	return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();},
				parser: function(date) {
					var t = Date.parse(date);
					if (!isNaN(t)){
						return new Date(t);
					} else {
						return new Date();
					}
				}
			});
		}); 
		// 弹出添加请假单界面
		function newLeaveBill() {
			$('#dlg').dialog('open').dialog('setTitle', '添加请假单');
			$('#fm').form('clear');
			$('#applicantId').attr("value", '${sessionScope.userInfo.id}');
			$('#applicantName').attr("value", '${sessionScope.userInfo.realName}');
			$('#applyTime').attr("value", formatterDate(new Date()));
			url = '${ctx}/leaveBill/addLeaveBill';
		}
		// 弹出修改请假单界面
		function editLeaveBill(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg').dialog('open').dialog('setTitle', '修改请假单');
			$('#fm').form('load', row);
			$('#applicantId').attr("value", '${sessionScope.userInfo.id}');
			$('#applicantName').attr("value", '${sessionScope.userInfo.realName}');
			url = '${ctx}/leaveBill/editLeaveBill?id=' + row.id;
		}
		// 保存请假单
		function saveLeaveBill() {
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
		// 删除请假单
		function deleteLeaveBill(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('提示信息', '确定删除该请假单?', function(r) {
					if (r) {
						$.post('${ctx}/leaveBill/deleteLeaveBill', { id : row.id }, function(result) {
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
		// 查询请假单
		function searchLeaveBill(){
			$('#dg').datagrid('load',{
				state: $('#state').val()
			});
		}
		// 申请请假
		function startLeaveBill(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$.post( '${ctx}/leaveBill/startLeaveBill?leaveBillId=' + row.id, { id : row.id }, function(result) {
				if (result.success) {
					$.messager.alert("提示信息","申请成功");
					$('#dg').datagrid('reload');
				} else {
					$.messager.alert("提示信息",result.errorMsg);
				}
			}, 'json');
		}
		// 格式化
		function formatApplicant(value, rowData, rowIndex) {// 申请人
			return rowData["applicant"]["realName"];
		}
		function formatState(value, rowData, rowIndex){// 请假状态
			if (value == 0) {
				return "初始录入";
			} else if (value == 1) {
				return "开始审批";
			} else if (value == 2) {
				return "审批完成"
			}
		};
		function actionType (value, rowData, rowIndex) {// 操作
			var state = rowData["state"];
			var str = '';
			if(true) {
				if (true && state == 0) {
					str += '<a href="#" onclick="editLeaveBill(' + rowIndex + ')">编辑</a>'
				}
				if (true && state == 0) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="deleteLeaveBill(' + rowIndex + ')">删除</a>'
				}
				if (true && state == 0) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="startLeaveBill(' + rowIndex + ')">申请请假</a>'
				}
				if (true && state == 1 || state == 2) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += '<a href="#" onclick="viewHisComment(' + rowIndex + ')">查看审核记录</a>'
				}
			}
			return str;
		}
		// 查看审核记录
		function viewHisComment(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg1').dialog('open').dialog('setTitle', '审核记录');
			$('#table1').datagrid({
			    url:'${ctx}/leaveBill/viewHisCommentByLeaveBillId?leaveBillId=' + row.id
			})
		}
	</script>
	
<body>

	<div id="tb" style="padding:3px">
			<span>请假状态:</span>
			<input id="state" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchLeaveBill()">查询</a>
	</div>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newLeaveBill()">新增</a> 
	</div>

	<table id="dg" 
		class="easyui-datagrid" 
		data-options="url:'${ctx}/leaveBill/dataGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="applicant.name" formatter="formatApplicant" width="100">申请人</th>
				<th field="applyTime" width="200">申请时间</th>
				<th field="content" width="300">请假事由</th>
				<th field="startTime" width="200">开始时间</th>
				<th field="endTime" width="200">结束时间</th>
				<th field="remarks" width="250">备注</th>
				<th field="state" formatter="formatState" width="100">请假状态</th>
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
					<td>申请人</td>
					<input id="applicantId" name="applicant.id" class="easyui-validatebox" hidden="true">
					<td><input id="applicantName" name="applicant.name" class="easyui-validatebox" readonly="true"></td>
					<td>申请时间</td>
					<td><input id="applyTime" name="applyTime" class="easyui-validatebox" readonly="true"></td>
				</tr>
				<tr>
					<td>开始时间</td> 
					<td><input id="startTime" name="startTime" class="easyui-datebox" style="width:100%;height:26px"></td>
					<td>结束时间</td>
					<td><input id="endTime" name="endTime"class="easyui-datebox" style="width:100%;height:26px"></td>
				</tr> 
				<tr>
					<td>请假事由</td>
					<td colspan="3"><textarea name="content" class="easyui-validatebox" style="width:100%;height:45px"></textarea></td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3"><textarea name="remarks" class="easyui-validatebox" style="width:100%;height:45px"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveLeaveBill()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
	<div id="dlg1" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true', buttons:'#dlg1-buttons'" 
		style="width:700px; height:400px;">
		<table id="table1" 
			class="easyui-datagrid" 
			data-options="rownumbers:'true', singleSelect:'true'"
			style="height:100%;">
			<thead>
				<tr>
					<th field="time" width="100">时间</th>
					<th field="userId" width="150">批注人</th>
					<th field="fullMessage" width="300">批注信息</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="dlg1-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">关闭</a>
	</div>
</body>
</html>