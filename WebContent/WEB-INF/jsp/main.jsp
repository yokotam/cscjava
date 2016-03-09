<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Hatsugen,model.GetListLogic" %>
<%
//アプリケーションスコープに保存された発言内容を取得
GetListLogic hatsugenList = new GetListLogic();
//エラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSC</title>
</head>
<body>
<h1>掲示板</h1>
<% for(Hatsugen hhh : hatsugenList.execute()){%>
<p><%= hhh.getComment() %></p>
<% } %>
</body>
</html>

