<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>表单演示</title>
    <style>
        body { font-family: 'Microsoft YaHei', sans-serif; max-width: 600px; margin: 50px auto; }
        .form-group { margin: 15px 0; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="email"] { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 10px 20px; background: #1976d2; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #1565c0; }
    </style>
</head>
<body>
    <h1>📝 用户表单（演示 @RequestParam + Session + Cookie）</h1>
    <form action="${pageContext.request.contextPath}/param/submit-form" method="post">
        <div class="form-group">
            <label>用户名：</label>
            <input type="text" name="username" placeholder="请输入用户名" required>
        </div>
        <div class="form-group">
            <label>邮箱：</label>
            <input type="email" name="email" placeholder="请输入邮箱" required>
        </div>
        <div class="form-group">
            <label>
                <input type="checkbox" name="remember"> 记住我（7天免登录）
            </label>
        </div>
        <button type="submit">提交</button>
    </form>
    <hr>
    <h3>其他演示入口</h3>
    <ul>
        <li><a href="${pageContext.request.contextPath}/param/basic?name=张三&age=25">@RequestParam 基础绑定</a></li>
        <li><a href="${pageContext.request.contextPath}/param/simple?name=李四">@RequestParam 省略注解</a></li>
        <li><a href="${pageContext.request.contextPath}/param/default-value">required + defaultValue</a></li>
        <li><a href="${pageContext.request.contextPath}/param/hobbies?hobby=篮球&hobby=游泳&hobby=编程">数组绑定（爱好多选）</a></li>
        <li><a href="${pageContext.request.contextPath}/param/all?name=张三&age=25&city=北京">Map 接收全部参数</a></li>
        <li><a href="${pageContext.request.contextPath}/param/header">@RequestHeader 获取请求头</a></li>
        <li><a href="${pageContext.request.contextPath}/param/set-cookie">@CookieValue 演示</a></li>
        <li><a href="${pageContext.request.contextPath}/param/login?username=张三">@SessionAttribute 演示（先登录）</a></li>
    </ul>
</body>
</html>
