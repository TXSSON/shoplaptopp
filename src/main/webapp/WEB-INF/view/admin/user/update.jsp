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
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        const avatarPreview = $("#avatarPreview");

                        // Lấy URL hình ảnh cũ từ server nếu có
                        const orglImage = "${user.avatar}";
                        if (orglImage) {
                            const urlImage = "/images/avatar/" + orglImage;
                            avatarPreview.attr("src", urlImage);
                            avatarPreview.css({ "display": "block" });
                            // Lưu đường dẫn ảnh vào localStorage và kèm theo ID của user hiện tại
                            localStorage.setItem('avatar', JSON.stringify({ url: urlImage, userId: "${user.id}" }));
                        }

                        // Khi người dùng thay đổi ảnh
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            avatarPreview.attr("src", imgURL);
                            avatarPreview.css({ "display": "block" });
                            // Lưu đường dẫn ảnh tạm thời vào localStorage và kèm theo ID user hiện tại
                            localStorage.setItem('avatar', JSON.stringify({ url: imgURL, userId: "${user.id}" }));
                        });

                        // Khi trang load lại, kiểm tra localStorage để phục hồi ảnh
                        const savedAvatar = JSON.parse(localStorage.getItem('avatar'));
                        if (savedAvatar && savedAvatar.userId === "${user.id}") {
                            // Đảm bảo ảnh thuộc về user hiện tại
                            avatarPreview.attr("src", savedAvatar.url);
                            avatarPreview.css({ "display": "block" });
                        } else {
                            // Nếu không đúng user hoặc không có ảnh, ẩn avatarPreview
                            avatarPreview.css({ "display": "none" });
                        }
                    });


                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Update User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin/user">Users</a>
                                    </li>
                                    <li class="breadcrumb-item active">Update</li>
                                </ol>
                                <div class=" mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update user with id = ${user.id}</h3>
                                            <hr />
                                            <form:form class="mb-3" method="post" action="/admin/user/update/"
                                                modelAttribute="user" enctype="multipart/form-data">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">id:</label>
                                                    <form:input type="number" class="form-control" path="id" />
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <label class="form-label">Email:</label>
                                                        <form:input type="email" class="form-control" path="email"
                                                            readonly="true" />
                                                    </div>
                                                    <div class="col">
                                                        <label class="form-label">Phone number:</label>
                                                        <form:input type="text" class="form-control" path="phone" />
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <c:set var="errorFullName">
                                                            <form:errors path="fullName" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Full Name:</label>
                                                        <form:input type="text"
                                                            class="form-control  ${not empty errorFullName ? 'is-invalid' : ''}"
                                                            path="fullName" />
                                                        ${errorFullName}
                                                    </div>
                                                    <div class="col">
                                                        <label class="form-label">Address:</label>
                                                        <form:input type="text" class="form-control" path="address" />
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <label class="form-label">Role:</label>
                                                        <form:select class="form-select" path="role.name">
                                                            <form:option value="USER">User</form:option>
                                                            <form:option value="ADMIN">Admin</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col">
                                                        <label for="formFile" class="form-label">Avatar</label>
                                                        <input class="form-control" type="file" id="avatarFile"
                                                            accept=" .png, .jsp, .jpeg" name="fileImage">
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col-12">
                                                        <img style="max-height: 250px; display: none"
                                                            alt="avatar preview" id="avatarPreview"
                                                            class="img-thumbnail">
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <button type="submit" class="btn btn-warning">Update</button>
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