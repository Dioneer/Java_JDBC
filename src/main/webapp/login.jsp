<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post" accept-charset="UTF-8">
         <p><label for="email">Email:
             <input type="text" name="email" id="email" value="${param.email}" required>
         </label></p>
        <p><label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label></p>
       <button type="submit">Login</button>
       <a href="${pageContext.request.contextPath}/registration">
            <button type="submit">Registration</button>
       </a>
        <c:if test="${param.error !=null}">
            <div style="color:red">
                <span>Email or password is not correct</span></br>
            </div>
        </c:if>
    </form>
</body>
</html>