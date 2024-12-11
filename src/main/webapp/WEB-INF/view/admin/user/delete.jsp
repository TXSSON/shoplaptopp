<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Trần Xuân Sơn - Dự án laptopshop" />
                <meta name="author" content="Trần Xuân Sơn" />
                <title>Dashboard - Trần Xuân Sơn</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Delete User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin/user">Users</a>
                                    </li>
                                    <li class="breadcrumb-item active">Delete</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <h3>Delete user with id = ${user.id}</h3>
                                            <hr />
                                            <div class="alert alert-danger" role="alert">
                                                Bạn có chắc chắn muốn xóa người dùng này không ?
                                            </div>
                                            <form:form action="/admin/user/delete" method="post" modelAttribute="user">
                                                <input type="hidden" name="id" value="${user.id}">
                                                <div class="d-flex justify-content-between">
                                                    <button type="submit" class="btn btn-danger">Delete user</button>
                                                    <a href="/admin/user" class="btn btn-primary"
                                                        style="margin-top: 5px">Back</a>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>