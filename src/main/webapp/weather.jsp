<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>weather</title>
</head>
<body>
<h1>Погода</h1>
<form method="get" action="${pageContext.request.contextPath}/weather">
    <p>введите город</p>
    <input type="text" name="city">
    <input type="submit" value="search">
</form>

<%
    String weather = (String) request.getAttribute("weather");
    if (weather != null) {

%>
<pre><%= weather %></pre>
<%
    }
%>
</body>
</html>