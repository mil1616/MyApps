<%-- 
    Document   : addMenu
    Created on : 7 juil. 2015, 18:22:21
    Author     : Black
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="stylesheet" type="text/css" href="css/styles.css"></link>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <!--        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.min.css"></link>
                    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.min.js"></script>
                    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-zh-CN.min.js"></script>-->
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

            <title>Add Menu</title>

    </head>

    <body>
        <div style="padding: 20px">
            <div>
                <h1>Add Menu</h1>
                <c:url var="viewMenusUrl" value="/menus.html" />
                <a href="${viewMenusUrl}" class="btn btn-link">View Existing Menus</a>
            </div>

            <br /><br />
            <div>
                <c:url var="saveMenuUrl" value="/menus/save.html" />
                <form:form modelAttribute="menu" method="POST" action="${saveMenuUrl}" class="form-inline">
                    <div class="col-md-6">
                        <div class="form-group">
                            <%--<c:out value="Menu name :"></c:out>--%>

                            <form:label path="name">Menu name</form:label>
                            <c:if test="${empty menu.id}">
                                <form:input type="text" path="name"/>
                                <form:button type="submit" value="Save Menu" class="btn btn-default">Save</form:button>
                            </c:if>
                            <c:if test="${!empty menu.id}">
                                <c:out value="${menu.name}" />
                                <!--<input type="submit" value="Save" class="btn btn-default"/>-->
                                <%--<form:button type="submit" value="Update Menu" class="btn btn-default">Update</form:button>--%>
                            </c:if>
                        </div>  

                        <%--</form:form>--%>
                    </div>  
                </form:form>
            </div>
            <!--</div>-->
            <br /><br />
            <div class="row">
                <div class="col-md-6">
                    <c:if test="${!empty menuItems}">
                        <table class="table">
                            <!--<thead>-->
                            <tr>
                                <th class="col-md-4" align="center" >Name</th>
                                <th class="col-md-6">Quantity</th>
                                <th class="col-md-2"></th>
                            </tr>
                            <!--</thead>-->
                            <c:url var="removeFromMenuUrl" value="/menus/removeFromMenu.html" />
                            <c:url var="saveQuantityUrl" value="/menus/saveQuantity.html" />
                            <c:forEach items="${menuItems}" var="menuItems">
                                <tr>
                                    <td>
                                        <c:out value="${menuItems.vegetable.name}" />
                                    </td>
                                    <td>
                                        <form:form id="${fruitFormIdii}" method="POST" action="${saveQuantityUrl}" class="form-inline">
                                            <input id="menuItemId" name="menuItemId" type="hidden" value="${menuItems.id}" />
                                            <input type="text" class="form-control" name="menuQuantity" value="${menuItems.quantity}" />
                                            <input type="submit" class="btn btn-default" value="Save Quantity"/>
                                        </form:form>
                                    </td>
                                    <td>
                                        <form id="${fruitFormId}" action="${removeFromMenuUrl}" method="POST">
                                            <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                            <input id="menuItemId" name="menuItemId" type="hidden" value="${menuItems.id}"/>
                                            <input type="submit" class="btn btn-default" value="Remove from menu"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <c:if test="${empty menuItems}">
                        There are currently no vegetable added to this menu.
                    </c:if>
                </div>


            </div>

            <br /><br />  
            <div>
                <%--<c:url var="addVegetableUrl" value="/vegetables/add.html" />--%>
                <!--<a href="${addVegetableUrl}" class="btn btn-link">Add Vegetable</a>-->
                <!--<br /><br />-->
                <%--<c:url var="saveVegetableUrl" value="/vegetables/save.html" />--%>
                <%--<form:form modelAttribute="vegetable" method="POST" action="${saveVegetableUrl}" class="form-inline">--%>
                <%--<c:out value="New Vegetable :"></c:out>--%>
                <!--<div class="form-group">-->
                <%--<form:label path="name">New Vegetable</form:label>--%>
                <%--<form:input path="name"/>--%>
                <!--                        <input type="submit" value="Save Vegetable" class="btn btn-default"/>
                                    </div>-->
                <%--</form:form>--%>
            </div>

            <br /><br />
            <div class="row">
                <div class="col-md-6">
                    <c:if test="${!empty vegetables}">
                        <table class="table">
                            <tr>
                                <th class="col-md-4">Name</th>
                                <th class="col-md-6"></th>
                                <th class="col-md-2"></th>
                            </tr>
                            <c:url var="addToMenuUrl" value="/menus/addToMenu.html" />
                            <c:forEach items="${vegetables}" var="vegetable">
                                <tr>
                                    <td>
                                        <c:out value="${vegetable.name}" />
                                    </td>
                                    <td>
                                        <div style='width: 10px;'></div>
                                    </td>
                                    <td>
                                        <form id="${fruitForm}" action="${addToMenuUrl}" method="POST">
                                            <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                            <input id="vegetableId" name="vegetableId" type="hidden" value="${vegetable.id}"/>

                                            <c:if test="${isSaved}">
                                                <input type="submit" class="btn btn-default" value="Add to menu" />
                                            </c:if>
                                            <c:if test="${!isSaved}">
                                                <input type="submit" disabled class="btn btn-default" value="Add to menu" />
                                            </c:if>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <c:if test="${empty vegetables}">
                        There are currently no vegetable created.
                    </c:if>
                </div>
            </div>
        </div>
        </div>
    </body>
</html>
