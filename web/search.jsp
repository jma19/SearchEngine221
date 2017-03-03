<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="com.uci.api.MiyaApi" %>
<%@ page import="com.uci.mode.Abstract" %>
<%@ page import="com.uci.mode.Response" %>
<%@ page import="com.uci.*"%>
<%--@ page import="com.google.common.collect.Lists"--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Miya Search Results</title>
    <link rel="shortcut icon" href="<%=basePath %>/favicon.ico" />
    <style>
        #search{
            width:120px;
            height:50px;
            font_size:30px;
            background-image:image("images.png");
        }
        #textArea{
            width:400px;
            height:32px;
            font_size:14px;
        }
        #displayText{
            font-size: 14px;
            ;
        }
    </style>
</head>
<body>
<%
  String keyword = new String(request.getParameter("keyword").getBytes());
%>
<form action="search.jsp" name="Miya Submit" method="get">
    <table border="0">
        <tr>
            <td align="left"></td>
            <td align="justify" width="600px">
            <table border="0" height="50px" width="650px" align="left">
                <tr>
                    <td align="left"><img src="Miya.jpg" height="10%"/></td>
                                            <%
                        if(keyword!=null){
                                            %>
                    <td align="left"><input name="keyword" value='<%=keyword%>' type=maxlength="100"
                                            id="textArea"></td>
                                                <%
                                                }else{
                                                %>
                    <td align="left"><input name="keyword" type="text" maxlength="100"
                                            ></td>
                    <%
                        }
                    %>
                    <td align="right"><input type="image" src="c.png" alt="Miya Submit" width="65px" height="65px"> </td>
                </tr>
            </table>
        </td>
        </tr>
        <tr>
            <td align="left" width="50"></td>
            <%
                MiyaApi api = new MiyaApi();
                List<Abstract> results = api.getter();
            %>
            <td align="justify" style="font-size: 14px;font-face:'Arial' ;color:#808080 ">The best 10 results are given by Miya<br></td>
            <%
                System.out.println("About "+results.size()+ "results are found");
                for(Abstract result:results){
            %>
        </tr>
        <tr>
            <td align="left" width="50"></td>
            <td align="justify" width="800px">
                <h3><a href=<%=result.getUrl()%>><%=result.getTitle()%></a></h3>
                <p><font size: 14px><%=result.getDesc()+"......"%></font>
                    <br><font color="#006400"; size: 12px> <%=result.getUrl()%></font>
                <p>
                    <%
    }
%>
            </td>

        </tr>


    </table>

</form>

</body>
</html>