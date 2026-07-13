<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导航演示（请求转发 vs 重定向）</title>
    <style>
        body { font-family: 'Microsoft YaHei', sans-serif; max-width: 800px; margin: 50px auto; }
        .card { border: 1px solid #ddd; border-radius: 8px; padding: 20px; margin: 10px 0; }
        .forward { color: #2e7d32; background: #e8f5e9; }
        .redirect { color: #e65100; background: #fff3e0; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #1565c0; color: white; }
    </style>
</head>
<body>
    <h1>🔀 请求转发 vs 重定向</h1>

    <table>
        <tr>
            <th>特征</th>
            <th>请求转发 (forward)</th>
            <th>重定向 (redirect)</th>
        </tr>
        <tr>
            <td>浏览器地址栏</td>
            <td>不变</td>
            <td>变为新 URL</td>
        </tr>
        <tr>
            <td>请求次数</td>
            <td>1 次</td>
            <td>2 次（302 + 新请求）</td>
        </tr>
        <tr>
            <td>request 域数据</td>
            <td>✅ 保留</td>
            <td>❌ 丢失（可用 Flash 属性）</td>
        </tr>
        <tr>
            <td>速度</td>
            <td>快（服务器内部）</td>
            <td>慢（客户端重新请求）</td>
        </tr>
        <tr>
            <td>适用场景</td>
            <td>同一应用内部跳转</td>
            <td>跨应用跳转 / 防重复提交</td>
        </tr>
    </table>

    <h3>演示入口</h3>
    <div class="card forward">
        <strong>➡ 请求转发</strong>
        <ul>
            <li><a href="${pageContext.request.contextPath}/nav/forward-demo">基础转发（观察地址栏不变）</a></li>
            <li><a href="${pageContext.request.contextPath}/nav/forward-with-data">携带数据转发（request 域共享）</a></li>
        </ul>
    </div>
    <div class="card redirect">
        <strong>↪ 重定向</strong>
        <ul>
            <li><a href="${pageContext.request.contextPath}/nav/redirect-demo">基础重定向（观察地址栏变化）</a></li>
            <li><a href="${pageContext.request.contextPath}/nav/redirect-view">重定向（RedirectView 方式）</a></li>
            <li><a href="${pageContext.request.contextPath}/nav/redirect-with-params">携带参数 + Flash 消息</a></li>
        </ul>
    </div>
    <hr>
    <p><a href="${pageContext.request.contextPath}/hello/say">← 返回首页</a></p>
</body>
</html>
