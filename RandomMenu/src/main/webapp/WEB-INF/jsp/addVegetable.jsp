<%-- 
    Document   : addMenu
    Created on : 7 juil. 2015, 18:22:21
    Author     : Black
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>    

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add vegetable</title>

    </head>

    <body>
        <div style="padding: 20px">
            <h1>Add vegetable</h1>
            <c:url var="viewMenusUrl" value="/menus.html" />
            <a href="${viewMenusUrl}">View Existing Menus</a>

            <br /><br />
            <c:url var="saveVegetableUrl" value="/vegetables/save.html" />
            <form:form modelAttribute="vegetable" method="POST" action="${saveVegetableUrl}" class="form-inline">
                <form:label path="name">Title:</form:label>
                <form:input path="name"/>
                <input type="submit" value="Save vegetable" class="btn btn-default"/>
            </form:form>
            <br /><br />
            <div style="width: 30%;">
                <c:if test="${!empty vegetables}">
                    <table class="table">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                        </tr>
                        <c:forEach items="${vegetables}" var="vegetable">
                            <tr>
                                <td><c:out value="${vegetable.id}" /></td>
                                <td><c:out value="${vegetable.name}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                <c:if test="${empty vegetables}">
                    There are currently no vegetable created.
                </c:if>
            </div>

        </div>
    </body>
</html>
