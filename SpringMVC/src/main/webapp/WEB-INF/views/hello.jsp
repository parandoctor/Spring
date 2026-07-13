<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring MVC 演示</title>
    <style>
        body { font-family: 'Microsoft YaHei', sans-serif; max-width: 800px; margin: 50px auto; }
        .card { border: 1px solid #ddd; border-radius: 8px; padding: 20px; margin: 10px 0; }
        .success { color: #2e7d32; background: #e8f5e9; }
        .info { color: #1565c0; background: #e3f2fd; }
    </style>
</head>
<body>
    <h1>Spring MVC 🚀</h1>
    <div class="card success">
        <strong>来源：${from}</strong>
    </div>
    <div class="card info">
        <p>${message}</p>
    </div>
    <hr>
    <p><a href="${pageContext.request.contextPath}/param/form">→ 去表单页面</a></p>
    <p><a href="${pageContext.request.contextPath}/nav/index">→ 重定向与转发演示</a></p>
</body>
</html>
