<%@page import="java.util.Date"%>
<%@page import="br.com.foxinline.util.DateUtils"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html xmlns="http://www.w3.org/1999/xhtml" style="margin-top:-60px">
    <head>
        <title>Garçom Login</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <link rel="stylesheet" href="media/bootstrap/css/bootstrap.css"/>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="media/css/font-awesome.min.css"/>
        <!-- Ionicons -->
        <link rel="stylesheet" href="media/css/ionicons.css"/>
        <!-- Theme style -->
        <link rel="stylesheet" href="media/dists/css/AdminLTE.css"/>
        <!-- iCheck -->
        <link rel="stylesheet" href="media/dists/css/skins/skin-blue.css"/>

    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="https://adminlte.io/themes/AdminLTE/index2.html"><b>Gar</b>çom</a>
            </div>
            <!-- /.login-logo -->
            <div class="login-box-body">
                <p class="login-box-msg">Login</p>

                <form action="j_security_check" method="POST">
                    <div class="form-group has-feedback">
                        <input name="j_username" id="id_username" class="form-control" placeholder="Login">
                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" name="j_password"  id="id_password" class="form-control" placeholder="Password">
                            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="checkbox icheck">
                                <label>
                                </label>
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-xs-4">
                            <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                        </div>
                        <!-- /.col -->
                    </div>
                </form>

            </div>
            <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->


    </body>
</html>