<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>

<html>
	<head>
	<title>流程定义信息</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	</head>
	
	<script type="text/javascript">
		// 弹出上传流程定义界面
		function openUpload() {
			$('#dlg').dialog('open').dialog('setTitle', '上传流程定义');
			$('#fm').form('clear');
			url = '${ctx}/workFlow/upload';
		};
		// 保存上传流程定义
		function saveUpload() {
			$('#fm').form('submit', {
				url : url,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.success) {
						$.messager.alert("提示信息","上传成功");
						$('#dlg').dialog('close');
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
			});
		}
		// 查询上传流程定义
		function searchDefination() {
			$('#dg').datagrid('load',{
				realName: $('#name').val()
			});
		}
		// 查看流程图
		function viewImage(index) {
			$('#dg').datagrid('selectRow',index);
			var row = $('#dg').datagrid('getSelected');
			$('#dlg1').dialog({
			    title: '查看流程图',
			    height: 600,
			    width: 800,
			    closed: false,
			    cache: false,
			    content:'<img src="${ctx}/workFlow/viewImage?deploymentId=' + row['deploymentId'] + '"/>',
			    modal: true
			});
		}
		// 格式化
		function actionType (value, rowData, rowIndex) {// 操作
			var str = '';
			if (true) {
				str += '<a href="#" onclick="viewImage(' + rowIndex + ')">查看流程图</a>'
			}
//			if(true){
//				if (true) {
					// 查看流程图
//					str += '<a target="_blank" href="${ctx}/workFlow/viewImage?deploymentId=' + rowData['deploymentId'] + '">查看流程图</a>'
//				}
//			}
			return str;
		}
	</script>
	
	<body>
		<div id="tb" style="padding:3px">
			<span>名称:</span>
			<input id="name" style="line-height:20px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDefination()">查询</a>
	</div>
	
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openUpload()">上传</a> 
	</div>
	
	<table id="dg" 
		class="easyui-datagrid"
		data-options="url:'${ctx}/workFlow/definationGrid', toolbar:'#toolbar', rownumbers:'true', singleSelect:'true', pagination:'true'"
		style="width:100%; height:600px;">
		<thead>
			<tr>
				<th field="id" width="100">ID</th>
				<th field="name" width="200">名称</th>
				<th field="key" width="100">流程定义的KEY</th>
				<th field="version" width="100">流程定义的版本</th>
				<th field="resourceName" width="200">流程定义的规则文件名称</th>
				<th field="diagramResourceName" width="200">流程定义的规则图片名称</th>
				<th field="deploymentId" width="100">部署ID</th>
				<th field="action" formatter="actionType" width="100">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="dlg" 
		class="easyui-dialog" 
		data-options="iconCls:'icon-save', closed:'true', buttons:'#dlg-buttons'" 
		style="width:500px; height:200px; padding:10px;">
		<form id="fm" method="post" enctype="multipart/form-data">
			<table class="fitem">
				<tr>
					<td>流程名称:</td>
					<td>
						<input class="easyui-textbox" name="name" data-options="required:true" style="width:250px">
					</td>
				</tr>
				<tr>
					<td>流程文件:</td>
					<td>
						<input class="easyui-filebox" name="sourceFile" data-options="required:true" style="width:250px">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpload()">上传</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
	<div id="dlg1">
	</div>
	
	</body>