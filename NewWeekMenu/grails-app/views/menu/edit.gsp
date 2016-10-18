<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
        <title>Edit Menu</title>
    </head>
    <body>        
        <div style="padding: 20px">
            <div class="row">
                <div class="col-md-6">
                    <h1>Edit Menu</h1>
                    <br/>
                    <g:form method="post">
                        <label for="name">Menu Name</label>
                        <g:textField name="name" value="${menu.name}"/>
                        <g:field name="id" value="${menu.id}"/>
                        <g:actionSubmit action="update" value="Save" />
                        <br/>
                    </g:form>
                    <g:link action="delete" id="${menu.id}">Delete</g:link>
                </div>
            </div>
        </div>
    </body>
</html>
