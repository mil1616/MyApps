<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Vegetable</title>
    </head>
    <body>
        <g:form action="save" >
            <label for="name">Vegetable Name</label>
            <g:textField name="name" value="${veg.name}"/>
            <br/>
            <g:submitButton name="create" value="Save" />
        </g:form>
    </body>
</html>
