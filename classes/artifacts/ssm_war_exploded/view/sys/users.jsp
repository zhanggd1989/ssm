<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>用户信息</title>
        <jsp:include page="../inc.jsp"></jsp:include>
        <link rel="stylesheet" href="${ctx}/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
        <script src="${ctx}/js/jquery-3.2.1/jquery-3.2.1.min.js"></script>
        <script src="${ctx}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="panel panel-default">
            <div class="panel-heading">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="inputName">姓名</label>
                        <input type="text" class="form-control" id="inputName" placeholder="姓名">
                    </div>
                    <button type="submit" class="btn btn-default">查询
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                </form>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                        <button type="button" class="btn btn-primary btn-sm">新增
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </button>
                        <button type="button" class="btn btn-danger btn-sm">删除
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
                    <div class="col-md-6">
                        当前第${pageInfo.pageNum}页,
                        总共${pageInfo.pages}页，
                        总共${pageInfo.total}条记录
                    </div>
                    <div class="col-md-6">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <c:if test="${pageInfo.hasPreviousPage}">
                                    <li>
                                        <a href="${ctx}/list?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                                <li><a href="${ctx}/list?pn=1">首页</a></li>

                                <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                                    <c:if test="${page_Num == pageInfo.pageNum}">
                                        <li class="active"><a href="#">${page_Num}</a></li>
                                    </c:if>
                                    <c:if test="${page_Num != pageInfo.pageNum}">
                                        <li><a href="${ctx}/list?pn=${page_Num}">${page_Num}</a></li>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${pageInfo.hasNextPage}">
                                    <li>
                                        <a href="${ctx}/list?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                                <li><a href="${ctx}/list?pn=${pageInfo.pages}">末页</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url:"${ctx}/user/dataGrid",
                data:"pn=1",
                type:"GET",
                success : function(result) {
                    build_user(result);
                }

            })
        })

        function build_user(result){
            var emps = result.extend.pageInfo.list;
            $.each(emps, function(index, item){
                var loginName = $("<td></td>").append(item.loginName);
                var realName = $("<td></td>").append(item.loginName);
                var type = $("<td></td>").append(item.type == 0 ? '管理员' : '普通用户');
                var company = $("<td></td>").append(item.loginName);
                var department = $("<td></td>").append(item.loginName);
                var sex =$("<td></td>").append( item.sex == 1?'男':'女');
                var email = $("<td></td>").append(item.loginName);
                var phone = $("<td></td>").append(item.loginName);
                var fax = $("<td></td>").append(item.loginName);
                var userFlag = $("<td></td>").append(item.loginName);
                var remarks = $("<td></td>").append(item.loginName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                                .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var deleteBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var btn = $("<td></td>").append(editBtn);
                $("<tr></tr>").append(loginName).append(realName).append(sex).append(btn).appendTo("#user tbody");
            })
        }
        function build_page_nav(result) {

        }
    </script>

    </body>
</html>
