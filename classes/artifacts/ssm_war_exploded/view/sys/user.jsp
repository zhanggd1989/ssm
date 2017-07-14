<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>

<html>
<head>
    <title>用户信息</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${ctx}/js/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/js/select2-4.0.3/css/select2.min.css">
    <link rel="stylesheet" href="${ctx}/js/select2-4.0.3/css/select2-bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/user.css">
    <script src="${ctx}/js/jquery-3.2.1/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="${ctx}/js/select2-4.0.3/js/select2.js"></script>

    <script src="${ctx}/js/jquery-validation-1.16.0/jquery.validate.js"></script>
    <script src="${ctx}/js/jquery-validation-1.16.0/jquery.metadata.js"></script>
    <script src="${ctx}/js/jquery-validation-1.16.0/messages_zh.js"></script>
</head>
<body>
    <!-- 用户添加/修改界面 -->
    <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="userLabel"></h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">登录名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="loginName" name="loginName">
                                <span class="help-block"></span>
                            </div>
                            <label class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control" id="password" name="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" name="realName">
                            </div>
                            <label class="col-sm-2 control-label">类型</label>
                            <div class="col-sm-4">
                                <select class="form-control" id="type" name="type">
                                    <option value="0">管理员</option>
                                    <option value="1">普通用户</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">所属单位</label>
                            <div class="col-sm-4">
                                <select class="form-control" id="company" name="company.id"></select>
                            </div>
                            <label class="col-sm-2 control-label">所属部门</label>
                            <div class="col-sm-4">
                                <select class="form-control" id="department" name="department.id"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-4">
                                <label class="radio-inline">
                                    <input type="radio" id="sex1" name="sex" value="0"> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="sex2" name="sex" value="1"> 女
                                </label>
                            </div>
                            <label class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control required email" id="email" name="email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="phone" name="phone">
                            </div>
                            <label class="col-sm-2 control-label">传真</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="fax" name="fax">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否可用</label>
                            <div class="col-sm-4">
                                <select class="form-control" id="useFlag" name="useFlag">
                                    <option value="0">是</option>
                                    <option value="1">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3" id="remarks" name="remarks"></textarea>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="user_save_btn"></button>
                </div>
            </div>
        </div>
    </div>
    <!-- 用户查询界面-->
    <div class="panel panel-default">
        <div class="panel-heading">
            <form class="form-inline">
                <div class="form-group">
                    <label for="inputRealName">姓名</label>
                    <input type="text" class="form-control" id="inputRealName" name="inputRealName">
                </div>
                <button type="submit" class="btn btn-default">查询
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
            </form>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-md-2">
                    <button type="button" class="btn btn-primary btn-sm" id="user_add_btn">新增
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-danger btn-sm" id="user_delete_btn">删除
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table-bordered" id="user">
                        <thead>
                        <tr>
                            <th>登录名</th>
                            <th>姓名</th>
                            <th>类型</th>
                            <th>所属单位</th>
                            <th>所属部门</th>
                            <th>性别</th>
                            <th>邮箱</th>
                            <th>电话</th>
                            <th>传真</th>
                            <th>是否可用</th>
                            <th>备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="panel-footer navbar-fixed-bottom">
            <div class="row">
                <div class="col-md-6" id="page_info_area"></div>
                <div class="col-md-6" id="page_nav_area"></div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var totalRecord;
        var id;
        // 初始化
        $(function () {
            loadPage(1);
            $(function(){
                $("#myform").validate();
            });
        });
        // 加载页面
        function loadPage(page) {
            $.ajax({
                url: "${ctx}/user/getUsers",
                data: "page=" + page,
                type: "GET",
                success: function (result) {
                    // 加载用户信息
                    build_user(result);
                    // 加载条数信息
                    build_page_info(result);
                    // 加载分页信息
                    build_page_nav(result);
                }

            })
        };
        // 加载用户信息
        function build_user(result) {
            $("#user tbody").empty();
            var users = result.extend.userPage.list;
            $.each(users, function (index, item) {
                var loginName = $("<td></td>").append(item.loginName);
                var realName = $("<td></td>").append(item.realName);
                var type = $("<td></td>").append(item.type == 0 ? '管理员' : '普通用户');
                var companyName = $("<td></td>").append(item.company.name);
//                var departmentName = $("<td></td>").append(item.department.name);
                var sex = $("<td></td>").append(item.sex == 0 ? '男' : '女');
                var email = $("<td></td>").append(item.email);
                var phone = $("<td></td>").append(item.phone);
                var fax = $("<td></td>").append(item.fax);
                var useFlag = $("<td></td>").append(item.useFlag);
                var remarks = $("<td></td>").append(item.remarks);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-xs edit-btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                    .append("编辑");
                editBtn.attr("edit-id",item.id);
                var deleteBtn = $("<button></button>").addClass("btn btn btn-danger btn-xs delete-btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash"))
                    .append("删除");
                deleteBtn.attr("delete-id",item.id);
                var btn = $("<td></td>").append(editBtn).append("&nbsp;").append(deleteBtn);
                $("<tr></tr>").append(loginName).append(realName).append(type).append(companyName)//.append(departmentName)
                    .append(sex).append(email).append(phone).append(fax).append(useFlag).append(remarks).append(btn)
                    .appendTo("#user tbody");
            })
        }
        // 加载条数信息
        function build_page_info(result) {
            $("#page_info_area").empty();
            $("#page_info_area").append("当前" + result.extend.userPage.pageNum + "页,")
                .append("总共" + result.extend.userPage.pages + "页，")
                .append("总" + result.extend.userPage.total + "条记录");
            totalRecord = result.extend.userPage.total;
        }
        // 加载分页信息
        function build_page_nav(result) {
            $("#page_nav_area").empty();
            var ul = $("<ul></ul>").addClass("pagination");
            //首页+上一页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
            if (result.extend.userPage.hasPrevisousPage == false) {
                firstPageLi.addClass("disabled");
                prePageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    loadPage(1);
                });
                prePageLi.click(function () {
                    loadPage(result.extend.userPage.pageNum - 1);
                });
            }
            //下一页+末页
            var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
            var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
            if (result.extend.userPage.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    loadPage(result.extend.userPage.pageNum + 1);
                });
                lastPageLi.click(function () {
                    loadPage(result.extend.userPage.pages);
                });
            }
            ul.append(firstPageLi).append(prePageLi);

            $.each(result.extend.userPage.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.extend.userPage.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    loadPage(item);
                })
                ul.append(numLi);
            })
            ul.append(nextPageLi).append(lastPageLi);

            var navEle = $("<nav></nav>").append(ul);
            navEle.appendTo("#page_nav_area");
        }
        function reset_form(element) {
            $(element)[0].reset();
            $(element).find('*').removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }
        // 弹出新增用户界面
        $("#user_add_btn").click(function () {
            $('#userModal form')[0].reset();
            getCompanys();
            $('#userModal').modal({
                backdrop: 'static'
            });
            $('#userLabel').html('添加用户');
            $('#user_save_btn').text('保存');
        })
        // 获取单位信息
        function getCompanys() {
            $('#company').empty();
            $.ajax({
                url: "${ctx}/organization/organizationTree",
                type: "GET",
                success: function (result) {
                    $.each(result,function (i,n) {
                        var optionEle = $('<option></option>').append(n.text).attr("value",n.id).attr("children", n.children);
                        optionEle.appendTo('#company');
                    })
                    $('#company').select2();
                }
            })
        }
        // 获取部门信息
        $('#department').click(function () {
            var companyId = $('#company').val();
            $.ajax({
                url : "",
                type : "GET",
                success : function (result) {
                    $('#department').select2({
                        width : '170px',
                        data : [{
                            id : '1',
                            text : '行政部'
                        },{
                            id : '2',
                            text : '财务部'
                    }]
                    })
                }
            })
        })
        // 校验
        function validate_add_form(){
            var loginName = $('#loginName').val();
            var regName = /^[a-zA-Z0-9_-]{3,16}$/;
            if(!regName.test(loginName)){
                show_validata_msg('#loginName', 'error', '用户名必须是3到16位的字母与数字组合');
                return false;
            } else{
                show_validata_msg('#loginName', 'success', '');
            };
//            var email = $('#emal').val();
//            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
//            if(!regEmail.test(email)){
//                alert('邮箱格式不正确');
//                return false;
//            }
            return true;
        }
        function show_validata_msg(element, status, msg) {
            $(element).parent().removeClass("has-error has-success");
            $(element).next('span').text();
            if('success' == status) {
                $(element).parent().attr("has-success");
            } else if('error' == status){
                $(element).parent().attr("has-error");
            }
            $(element).next("span").text(msg);
        };
        $('#loginName').change(function () {
            $.ajax({
                url : '${ctx}/user/checkUser',
                data :  'loginName=' + $('#loginName').val(),
                type : 'POST',
                success : function (result) {
                    if(result.code == 100){
                        show_validata_msg('#loginName',"success","用户名可用");
                        $('#user_save_btn').attr("ajax-va","success");
                    } else {
                        show_validata_msg('#loginName', "error",result.extend.va_msg);
                        $('#user_save_btn').attr("ajax-va","error");
                    }
                }
            })
        })
        // 保存用户信息
        $('#user_save_btn').click(function () {
            var url;
            if(!validate_add_form()){
                return false;
            };
            if($(this).attr("ajax-va") == 'error') {
                return false;
            }
            if($(this).text() == '保存') {
                url = '${ctx}/user/addUser';
            } else {
                var id = $('#user_save_btn').attr('edit_id');
                url = '${ctx}/user/editUser/' + id;
            }
            $.ajax({
                url : url,
                type : 'POST',
                data : $('#userModal form').serialize(),
                success : function(result) {
                    alert(result.extend.code + ":" +result.extend.msg);
                    if(result.extend.code == 100) {
                        $('#userModal').modal('hide');
                        loadPage(totalRecord);
                    } else {
                        if(undefined != result.extend.fieldErrors.email) {
                            show_validata_msg('#email','error', result.extend.fieldErrors.email);
                        }
                        if(undefined != result.extend.fieldErrors.loginName) {
                            show_validata_msg('#email','error', result.extend.fieldErrors.loginName);
                        }
                    }

                }

            })
        });
        // 弹出修改用户界面
        $(document).on('click','.edit-btn',function () {
            getCompanys();
            getUser($(this).attr("edit-id"));
            $('#userModal').modal({
                backdrop: 'static'
            });
            $('#userLabel').html('修改用户');
            $('#user_save_btn').text('更新');
            $('#user_save_btn').attr('edit_id',$(this).attr('edit-id'));
        });

        function getUser(id) {
            $.ajax({
                url : '${ctx}/user/getUserById/' + id,
                type : 'GET',
                success : function (result) {
                    var userElement = result.extend.user;
                    $('#loginName').val(userElement.loginName);
                    $('#realName').val(userElement.realName);
                    $('#email').val(userElement.email);
                    $('#userModal input[name=sex]').val([userElement.sex]);
                    $('#userModal select[name=company]').val([userElement.company.id]);
//                    $('#userModal select[name=department]').val([userElement.department.id]);
                }
            })
        }
        
    </script>
</body>
</html>