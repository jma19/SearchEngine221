<%--
  Created by IntelliJ IDEA.
  User: carolchen
  Date: 2/27/17
  Time: 4:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title>Miya Search</title>
    <link rel="shortcut icon" href="<%=basePath %>/favicon.ico" />
    <style>
      #search{
        width:10em;
        height:20em;
        font_size:10em;
        background-image:image("images.png");
      }
      #textArea{
        width:400px;
        height:32px;
        font_size:40px
      }
    </style>
  </head>
  <body>
  <p></p>
  <p align="center"> <img src="heads.gif" width="300px" /></p>
  <form action="search.jsp" name="Miya Submit" method="get" enctype="application/x-www-form-urlencoded">
    <table border="0" height="100px" width="500px" align="center">
      <tr height="100%">
        <td width="90%"><input name="keyword" type="text" maxlength="100" id="textArea">
        </td>
        <td align="right"><input type="image" src="c.png" alt="Miya Submit" width="65px" height="65px"> </td>
      </tr>
    </table>
  </form>
  </body>
</html>
