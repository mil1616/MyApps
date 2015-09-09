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
            <div class="row">
                <div class="col-md-6">
                    <h1>Add Menu</h1>
                    <%--<c:url var="viewMenusUrl" value="/menus.html" />--%>
                    <spring:url var="viewMenusUrl" value="/menus" />
                    <!--<a href="${viewMenusUrl}" class="btn btn-link">View Existing Menus</a>-->
                    <button class="btn btn-link pull-right" onclick="location.href = '${viewMenusUrl}'">View Existing Menus</button>
                </div>
            </div>

            <br /><br />
            <div class="row">
                <c:url var="saveMenuUrl" value="/menus/${menu.id}/update" />
                <form:form modelAttribute="menu" method="POST" action="${saveMenuUrl}" >

                    <form:hidden path="id" />
                    <form:hidden path="name" />
                    <form:hidden path="isWeekMenu" />

                    <div class="col-md-6">
                        <div class="form-group">
                            <form:label path="name">Menu name</form:label>
                            <c:out value="${menu.name}" />
                            <button class="btn btn-success pull-right" onclick="location.href = '${updateUrl}'">Save</button>
                        </div>
                        <div class="form-group">
                            <form:label path="comment">Comment</form:label>
                            <form:textarea path="comment" cssClass="form-control" ></form:textarea>
                            <spring:url value="/menus/${menu.id}/update" var="updateUrl" />
                        </div>
                        <form class="form-inline">
                            <div class="form-group">
                                <form:label path="comment">Url (Not Working)</form:label>
                                <form:input type="text"  cssClass="form-control" path=""/>
                            </div>
                            <button class="btn btn-info pull-right" onclick="window.open('http://www.marmiton.org/recettes/recette_gratin-de-courgettes-rapide_17071.aspx', '_blank');">Open URL</button>
                        </form>
<!--                        <div class="form-group pull-right">
                            <button class="btn btn-primary" onclick="location.href = '${updateUrl}'">Save</button>
                            <button class="btn btn-primary" onclick="window.open('http://www.marmiton.org/recettes/recette_gratin-de-courgettes-rapide_17071.aspx', '_blank');">Open URL</button>
                        </div>-->
                    </div>
                    <!--                    <div class="col-md-6">
                                            <br /><br />
                                            <div class="form-group">
                    <form:label path="comment">Url</form:label>
                    <form:input type="text"  cssClass="form-control" path=""/>
                </div>
                <button class="btn btn-primary pull-right" onclick="window.open('http://www.marmiton.org/recettes/recette_gratin-de-courgettes-rapide_17071.aspx', '_blank');">Open</button>
            </div>-->
                </form:form>
            </div>
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
                <c:url var="addVegetableUrl" value="/vegetables/save" />
                <form:form modelAttribute="vegetable" method="POST" action="${addVegetableUrl}" class="form-inline">
                    <div class="form-group">
                        <form:label path="name">New Vegetable</form:label>
                        <form:input path="name"/>
                        <input id="menuItemId" name="menuId" type="hidden" value="${menu.id}" />
                        <input type="submit" value="Save Vegetable" class="btn btn-default"/>
                    </div>
                </form:form>
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

                                            <%--<c:if test="${isSaved}">--%>
                                            <input type="submit" class="btn btn-default" value="Add to menu" />
                                            <%--</c:if>--%>
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

                <!--                <div class="col-md-6">
                                    <iframe class="col-lg-12 col-md-12 col-sm-12" src="http://www.marmiton.org/recettes/recette_gratin-de-courgettes-rapide_17071.aspx" >
                                    </iframe>
                                </div>-->
            </div>
        </div>
        </div>
    </body>
</html>
