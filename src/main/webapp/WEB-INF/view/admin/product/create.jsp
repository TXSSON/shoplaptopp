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
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
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
                                <h1 class="mt-4">Create product</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin/product">Products</a>
                                    </li>
                                    <li class="breadcrumb-item active">Create</li>
                                </ol>
                                <div class=" mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a product</h3>
                                            <hr />
                                            <form:form method="post" action="/admin/product/create"
                                                modelAttribute="newProduct" enctype="multipart/form-data">
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <c:set var="errorName">
                                                            <form:errors path="name" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Name:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                            path="name" />
                                                        ${errorName}
                                                    </div>
                                                    <div class="col">
                                                        <c:set var="errorPrice">
                                                            <form:errors path="price" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Price:</label>
                                                        <form:input type="number"
                                                            class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                                                            path="price" placeholder="0.0" />
                                                        ${errorPrice}
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <c:set var="errorDetailDesc">
                                                            <form:errors path="detailDesc"
                                                                cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Detail description:</label>
                                                        <form:textarea
                                                            class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}"
                                                            path="detailDesc" />
                                                        ${errorDetailDesc}
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <c:set var="errorShortDetail">
                                                            <form:errors path="shortDetail"
                                                                cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Short description:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorShortDetail ? 'is-invalid' : ''}"
                                                            path="shortDetail" />
                                                        ${errorShortDetail}
                                                    </div>
                                                    <div class="col">
                                                        <c:set var="errorQuantity">
                                                            <form:errors path="quantity" cssClass="invalid-feedback" />
                                                        </c:set>

                                                        <label class="form-label">Quantity:</label>
                                                        <form:input type="number"
                                                            class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}"
                                                            path="quantity" placeholder="0" />
                                                        ${errorQuantity}
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col">
                                                        <label class="form-label">Factory:</label>
                                                        <form:select class="form-select" path="factory">
                                                            <form:option value="Apple">Apple(Macbook)</form:option>
                                                            <form:option value="Asus">Asus</form:option>
                                                            <form:option value="Lenovo">Lenovo</form:option>
                                                            <form:option value="Dell">Dell</form:option>
                                                            <form:option value="LG">LG</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col">
                                                        <label class="form-label">Target:</label>
                                                        <form:select class="form-select" path="target">
                                                            <form:option value="Gaming">Gaming</form:option>
                                                            <form:option value="Sinh viên - Văn Phòng">Sinh viên - Văn
                                                                Phòng</form:option>
                                                            <form:option value="Thiết kế đồ họa">Thiết kế đồ họa
                                                            </form:option>
                                                        </form:select>
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col-6">
                                                        <label for="formFile" class="form-label">Avatar</label>
                                                        <input class="form-control" type="file" id="avatarFile"
                                                            accept=" .png, .jsp, .jpeg" name="fileImage">
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col-12">
                                                        <img style="max-height: 250px; display: none"
                                                            alt="avatar preview" id="avatarPreview">
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                    <a href="/admin/product" class="btn btn-primary"
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