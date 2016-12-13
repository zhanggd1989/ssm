<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
	<title>主页</title>
	<jsp:include page="inc.jsp"></jsp:include>
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	<script src='${ctx}/js/index/index.js'type="text/javascript" ></script>
    <script type="text/javascript">
	var _menus = {
		basic : [ {
			"menuid" : "1",
			"icon" : "icon-sys",
			"menuname" : "系统管理",
			"menus" : [ {
				"menuid" : "11",
				"menuname" : "用户管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/user/list"
			}, {
				"menuid" : "12",
				"menuname" : "角色管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/role/list"
			}, {
				"menuid" : "13",
				"menuname" : "资源管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/resource/list"
			}, {
				"menuid" : "14",
				"menuname" : "机构管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/organization/list"
			} ]
		}, {
			"menuid" : "2",
			"icon" : "icon-sys",
			"menuname" : "字典管理",
			"menus" : [ {
				"menuid" : "21",
				"menuname" : "字典管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/user/list"
			} ]
		} ],
		point : [{
			"menuid" : "3",
			"icon" : "icon-sys",
			"menuname" : "任务管理",
			"menus" : [ {
				"menuid" : "31",
				"menuname" : "待办管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/workFlow/taskTodo"
			}, {
				"menuid" : "32",
				"menuname" : "已办管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/workFlow/taskDone"
			}]
	
		}, {
			"menuid" : "4",
			"icon" : "icon-sys",
			"menuname" : "业务管理",
			"menus" : [ {
				"menuid" : "41",
				"menuname" : "请假管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/leaveBill/list"
			}]
	
		}, {
			"menuid" : "5",
			"icon" : "icon-sys",
			"menuname" : "流程管理",
			"menus" : [ {
				"menuid" : "51",
				"menuname" : "定义管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/workFlow/defination"
			}, {
				"menuid" : "52",
				"menuname" : "部署管理",
				"icon" : "icon-nav",
				"url" : "${ctx}/workFlow/deployment"
			}]
	
		}]
	};
	
	$(function() {
    	// 打开修改密码窗口
		$('#editPass').click(function() {
        	$('#w').window('open');
		});
    	// 确定
		$('#btnEp').click(function() {
			serverLogin();
		});
		//取消
		$('#btnCancel').click(function(){
	  		$('#w').window('close');
		});
		// 安全退出
		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
				if (r) {
					$.post( '${ctx}/logout', function(result) {
						if(result.success){
							window.location.href='${ctx}/login';
						}
					}, 'json');
				}
			});
		})
	});
	
	//修改密码
	function serverLogin() {
	    var $newpass = $('#txtNewPass');
	    var $rePass = $('#txtRePass');
	
	    if ($newpass.val() == '') {
	        msgShow('系统提示', '请输入密码！', 'warning');
	        return false;
	    }
	    if ($rePass.val() == '') {
	        msgShow('系统提示', '请在一次输入密码！', 'warning');
	        return false;
	    }
	
	    if ($newpass.val() != $rePass.val()) {
	        msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
	        return false;
	    }
	
	    $.post('${ctx}/user/editUserPass?newPass=' + $newpass.val() + '&loginName=' + '${loginName}', function(msg) {
	        msgShow('系统提示', '恭喜，密码修改成功', 'info');
	        $newpass.val('');
	        $rePass.val('');
	        $('#w').window('close');
	    })
	    
	}
	</script>
	</head>
	<!-- 主界面 -->
	<body class="easyui-layout">
		<!-- north -->
	    <div id="north" data-options="region:'north',split:true">
	        <span style="float:left; padding-left:10px; font-size:16px">
	        	<img src="img/index/blocks.png" style="width:20px; height:20px" align="absmiddle"/> JavaWeb 整合框架 
	        </span>
	        <span style="float:right; padding-right:10px; font-size:12px;">欢迎 ${loginName}
	        	<a href="#" id="editPass" style="padding-left:5px; color:white; text-decoration:underline;">
	        		<img src="img/index/editPass.png" style="width:20px; height:20px" align="absmiddle"/>
	        	</a> 
	        	<a href="#" id="loginOut" style="padding-left:5px; color:white; text-decoration:underline;">
	        		<img src="img/index/loginOut.png" style="width:20px; height:20px" align="absmiddle"/>
	        	</a>
	        </span>
			<ul id="css3menu" style="padding:0px; margin:0px;list-type:none; float:left; margin-left:40px;">
				<li><a class="active" name="basic" href="javascript:;" title="系统管理">系统管理</a></li>
				<li><a name="point" href="javascript:;" title="工作流">工作流</a></li>
			</ul>
	    </div>
	    <!-- south -->
	    <div id="south" data-options="region:'south',split:true">
	        	Copyright © 2016态度决定高度<div id="bgclock"></div>
	    </div>
	    <!-- west -->
	    <div id="west" data-options="region:'west',split:true,title:'导航菜单'">
			<div id='wnav' class="easyui-accordion" fit="true" border="false">
				<!--  导航内容 -->
			</div>
	    </div>
	   <!-- center -->
	    <div id="center" data-options="region:'center',split:true">
	    	<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div id="home" title="欢迎使用" style="padding:20px;">
					<h4>1.平台简介</h4>
					<h1>在Spring Framework基础上搭建的一个Java基础开发平台，以Spring MVC为模型视图控制器，MyBatis为数据访问层，
					    Apache Shiro为权限授权层，Ehcahe对常用数据进行缓存，Activit为工作流引擎。是JavaEE界的最佳整合。</h1>
					<hr>
					<h4>2.内置功能</h4>
					<h1>用户管理：用户是系统操作者，该功能主要完成系统用户配置。</h1>
					<h1>角色管理：角色菜单权限分配。</h1>
					<h1>资源管理：配置系统菜单。</h1>
					<h1>机构管理：配置系统组织机构（公司、部门），树结构展现，可随意调整上下级。</h1>
					<hr>
					<h4>3.技术选型</h4>
					<h3>后端</h3>
					<h1>核心框架：Spring Framework 4.0</h1>
					<h1>安全框架：Apache Shiro 1.2</h1>
					<h1>视图框架：Spring MVC 4.0</h1>
					<h1>工作流引擎：Activiti 5.21</h1>
					<h1>持久层框架：MyBatis 3.3</h1>
					<h3>前端</h3>
					<h1>JS框架：EasyUI</h1>
				</div>
			</div>
	    </div>
	    
		<!--修改密码窗口-->
		<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
			maximizable="false" icon="icon-save" style="width:300px; height:150px; padding:5px; background:#fafafa;">
			<div class="easyui-layout" fit="true">
		    <div region="center" border="false" style="padding:10px; background:#fff; border:1px solid #ccc;">
			    <table cellpadding=3>
			        <tr>
			            <td>新密码：</td>
			            <td><input id="txtNewPass" type="Password" class="txt01" /></td>
			        </tr>
			        <tr>
			            <td>确认密码：</td>
			            <td><input id="txtRePass" type="Password" class="txt01" /></td>
			        </tr>
			    </table>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
		            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
		            <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
		        </div>
		    </div>
		</div>
		<!-- 右键菜单 -->
		<div id="mm" class="easyui-menu" style="width:150px;">
			<div id="mm-tabupdate">刷新</div>
			<div class="menu-sep"></div>
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseall">全部关闭</div>
			<div id="mm-tabcloseother">除此之外全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">当前页右侧全部关闭</div>
			<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">退出</div>
		</div>
	</body>
</html>