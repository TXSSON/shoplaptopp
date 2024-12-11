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
                                <h1 class="mt-4">Detail product</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                            href="/admin/product">product</a>
                                    </li>
                                    <li class="breadcrumb-item active">View</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <h3>Table Product with id = ${product.id}</h3>
                                            <hr />

                                            <div class="card  mb-3" style="width: 60%;">
                                                <div class="card-header ">
                                                    Product information
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">
                                                        <img src="/images/product/${product.image}" alt="Card image cap"
                                                            style="height: 100px; width: auto;" class="img-thumbnail" />
                                                    </li>
                                                    <li class="list-group-item">Name: ${product.name}</li>
                                                    <li class="list-group-item">Price: ${product.price}</li>
                                                    <li class="list-group-item">Detail description:
                                                        ${product.detailDesc}</li>
                                                    <li class="list-group-item">Short detail: ${product.shortDetail}
                                                    </li>
                                                    <li class="list-group-item">Quantity: ${product.quantity}</li>
                                                    <li class="list-group-item">Sold: ${product.sold}</li>
                                                    <li class="list-group-item">Factory: ${product.factory}</li>
                                                    <li class="list-group-item">Target: ${product.target}</li>
                                                </ul>
                                            </div>
                                            <div class="d-flex justify-content-start" style="width: 100%">
                                                <a href="/admin/product" class="btn btn-primary">Back</a>
                                            </div>
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