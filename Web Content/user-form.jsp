<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
    <h2>User Form</h2>
    <form action="UserServlet" method="post">
        <input type="hidden" name="id" value="${user.id}">
        Name: <input type="text" name="name" value="${user.name}"><br><br>
        Email: <input type="text" name="email" value="${user.email}"><br><br>
        <input type="submit" value="Save">
    </form>
    <br>
    <a href="UserServlet?action=list">Cancel</a>
</body>
</html>
