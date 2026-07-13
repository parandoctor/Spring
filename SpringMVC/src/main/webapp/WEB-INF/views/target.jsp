<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>转发/重定向目标页</title>
    <style>
        body { font-family: 'Microsoft YaHei', sans-serif; max-width: 800px; margin: 50px auto; }
        .card { border: 1px solid #ddd; border-radius: 8px; padding: 20px; margin: 10px 0; }
        .success { color: #2e7d32; background: #e8f5e9; }
        .info { color: #1565c0; background: #e3f2fd; white-space: pre-line; }
    </style>
</head>
<body>
    <h1>导航目标页 🎯</h1>
    <div class="card success">
        <strong>来源：${from}</strong>
    </div>
    <div class="card info">
        <p>${message}</p>
        <c:if test="${not empty flashMsg}">
            <p style="color:#e65100;">Flash 消息：${flashMsg}（重定向后仍可读取，刷新即消失！）</p>
        </c:if>
    </div>
    <hr>
    <p><a href="${pageContext.request.contextPath}/nav/index">← 返回导航页</a></p>
</body>
</html>
