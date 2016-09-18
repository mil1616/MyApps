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
        <title>New Menu</title>
    </head>
    <body>        
        <div style="padding: 20px">
            <div class="row">
                <div class="col-md-6">
                    <h1>Create Menu</h1>
                    <br/>
                    <g:form method="post">
                        <label for="name">Menu Name</label>
                        <g:textField name="name" value="${menu.name}"/>
                        <g:link action="save" id="${menu.id}" params="[name: menu.name]" >Save</g:link>
                        <br/>
                    </g:form>
                </div>
            </div>
        </div>
    </body>
</html>
