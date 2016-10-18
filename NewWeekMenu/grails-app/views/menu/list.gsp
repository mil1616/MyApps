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
        <title>Sample title</title>
    </head>
    <body style="padding: 20px">

        <div>
            <h1>Menus of the Week</h1>
            <div class="row">
                <div class="col-md-6">
                    <table class="table">
                        <g:each in="${weeklist}" var="menu">
                            <tr><td><g:link action="edit" id="${menu.id}">${menu.name}</g:link></td></tr>
                        </g:each>        
                    </table>
                </div>
            </div>
            <h1>Existing Menus</h1>
            <div class="row">
                <div class="col-md-6">
                    <table class="table">
                        <g:each in="${menulist}" var="menu">
                            <tr>
                                <td>
                                    <g:link action="edit" id="${menu.id}">${menu.name}</g:link>
                                    <g:link action="add" id="${menu.id}">Add To week</g:link>
                                </td>
                            </tr>
                        </g:each>        
                    </table>
                </div>
            </div>
        </div>
        <div>
            <a href="${createLink(action: 'create')}">Create new menu</a>
        </div>
    </body>
</html>
