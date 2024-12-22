<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content="Trần Xuân Sơn - Dự án laptopshop"/>
    <meta name="author" content="Trần Xuân Sơn"/>
    <title>Dashboard - Trần Xuân Sơn</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Dashboard</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item "><a class="text-decoration-none"
                                                    href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item active"><a class="text-decoration-none"
                                                          href="/admin/order">Orders</a></li>
                    <li class="breadcrumb-item active"> View Detail</li>
                </ol>

                <div class="mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Table order detail with Order id = ${orderId}</h3>
                            </div>
                            <hr/>
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Sản phẩm</th>
                                    <th scope="col">Tên</th>
                                    <th scope="col">Giá cả</th>
                                    <th scope="col">Số lượng</th>
                                    <th scope="col">Thành tiền</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="orderDetail" items="${orderDetails}">
                                    <tr>
                                        <td>${orderDetail.id}</td>
                                        <td>
                                            <div >
                                                <img src="/images/product/${orderDetail.product.image}"
                                                     style="width: 75px; height: 75px; border-radius: 50%; overflow: hidden;" alt="">
                                            </div>
                                        </td>
                                        <td>${orderDetail.product.name}</td>
                                        <td>
                                            <fmt:formatNumber type="number"
                                                              value="${orderDetail.price}"/> VNĐ
                                        </td><td>
                                            <fmt:formatNumber type="number"
                                                              value="${orderDetail.quantity}"/>
                                        </td>
                                        <td><fmt:formatNumber type="number"
                                                              value="${orderDetail.price * orderDetail.quantity}"/> VNĐ
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>

                </div>

            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
</body>

</html>