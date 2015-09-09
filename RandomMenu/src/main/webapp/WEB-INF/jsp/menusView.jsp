<%-- 
    Document   : addMenu
    Created on : 7 juil. 2015, 18:22:21
    Author     : Black
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>    

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="stylesheet" type="text/css" href="css/styles.css"></link>
        <link rel="stylesheet" type="text/css" href="newcss.css"></link>
        <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Existing Menus</title>
                </head>

                <body style="padding: 20px">
                    <div >
                        <h1>Existing Menus</h1>
                        <div class="row">
                            <div class="col-md-6">
                                <c:url var="saveMenuUrl" value="/menus/add" />
                                <form:form modelAttribute="menu" method="POST" action="${saveMenuUrl}" class="form-inline">
                                    <!--<div class="col-md-6">-->
                                    <div class="form-group">
                                        <form:label path="name">New menu name</form:label>
                                        <form:input type="text"  cssClass="form-control" path="name" size="25"/>
                                    </div>
                                    <!--<div class="form-group">-->
                                    <form:button type="submit" value="Save Menu" class="btn btn-default">Add</form:button>
                                        <!--</div>-->
                                        <!--</div>-->
                                </form:form>
                            </div>  
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <h3>Available menus</h3>
                            <c:if test="${!empty menus}">
                                <table class="table">
                                    <tr>
                                        <th>Name</th>
                                        <!--<th></th>-->
                                        <th></th>
                                        <th></th>
                                    </tr>
                                    <c:url var="editMenuUrl" value="/menus/edit.html" />
                                    <c:url var="deleteMenuUrl" value="/menus/delete.html" />
                                    <c:url var="addMenuToWeekUrl" value="/menus/addMenuToWeek.html" />
                                    <c:forEach items="${menus}" var="menu">
                                        <tr>
                                            <td><c:out value="${menu.name}" /></td>
                                            <td>
                                                <form id="${fruitFormId}" action="${deleteMenuUrl}" method="POST">
                                                    <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                                    <input type="submit" class="btn btn-default" value="Delete"/>
                                                </form>
                                            </td>
                                            <td>
                                                <form id="${fruitFormId}" action="${addMenuToWeekUrl}" method="POST">
                                                    <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                                    <input type="submit" class="btn btn-default" value="Add to Week"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <c:if test="${empty menus}">
                                There are currently no menus created.
                            </c:if>
                        </div>

                        <div class="col-md-3">
                            <h3>Week's menus</h3>
                            <c:if test="${!empty weekMenus}">
                                <table class="table">
                                    <tr>
                                        <th>Name</th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                    <c:url var="editMenuUrl" value="/menus/edit.html" />
                                    <c:url var="removeFromWeekUrl" value="/menus/removeMenuFromWeek.html" />
                                    <c:forEach items="${weekMenus}" var="menu">
                                        <tr>
                                            <td><c:out value="${menu.name}" /></td>
                                            <td>
                                            <!--                                        <form id="${fruitFormId}" action="${editMenuUrl}" method="POST">
                                            <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                            <input type="submit" class="btn btn-default" value="Edit"/>
                                            </form>-->
                                                <spring:url value="/menus/${menu.id}" var="updateUrl" />
                                                <button class="btn btn-primary" onclick="location.href = '${updateUrl}'">Edit</button>
                                            </td>
                                            <td>
                                                <form id="${fruitFormId}" action="${removeFromWeekUrl}" method="POST">
                                                    <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                                                    <input type="submit" class="btn btn-default" value="Remove from Week"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <c:if test="${empty weekMenus}">
                                There are currently no menus for the week.
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <h3>Week's shopping list</h3>
                            <c:url var="saveQuantityInFridgeUrl" value="/menus/saveQuantityInFridge.html" />
                            <c:if test="${!empty weekItems}">
                                <table class="table">
                                    <tr>
                                        <th>Name</th>
                                        <th>Quantity To Shop</th>
                                        <th>Fridge</th>
                                    </tr>
                                    <c:forEach items="${weekItems}" var="weekItem">
                                        <tr>
                                            <td><c:out value="${weekItem.vegetable.name}" /></td>
                                            <td><c:out value="${weekItem.getQuantityToShop()}" /></td>
                                            <td>
                                                <form:form id="${fruitFormIdii}" method="POST" action="${saveQuantityInFridgeUrl}" class="form-inline">
                                                    <input id="weekItem" name="weekItemName" type="hidden" value="${weekItem.id}" />
                                                    <input type="text" class="form-control" name="quantity" value="${weekItem.fridgeQuantity}" size="3"/>
                                                    <input type="submit" class="btn btn-default" value="Update"/>
                                                </form:form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                    <c:url var="sendMailUrl" value="/menus/sendMail" />
                                <button class="btn btn-danger" onclick="location.href = '${sendMailUrl}'">Edit</button>
                            </c:if>
                            <c:if test="${empty weekItems}">
                                There are currently no menus for the week.
                            </c:if>
                        </div>
                    </div>
                    </div>
                </body>
                </html>
