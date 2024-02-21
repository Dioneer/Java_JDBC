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
    <form action="/registration" method="post" accept-charset="UTF-8">
        <p><label for="name">Name:
            <input type="text" name="name" id="name">
        </label></p>
        <p><label for="birthday">Birthday:
            <input type="date" name="birthday" id="birthday">
        </label></p>
        <p><label for="email">Email:
            <input type="text" name="email" id="email">
        </label></p>
        <p><label for="pwd">Password:
            <input type="password" name="pwd" id="pwd">
        </label></p>
        <p><select name="role" id="role">
           <c:forEach var="role" items="${requestScope.roles}">
           <option label="${role}">${role}</option>
           </c:forEach>
        </select>
        </p>
        <p>
           <c:forEach var="gender" items="${requestScope.genders}">
              <input type="radio" name="gender" value="${gender}">${gender}
           </c:forEach>
        </p>
        <button type="submit" value="submit">Submit</button>
    </form>
    <c:if test="${requestScope.errors !=null}">
        <div style="color:red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span></br>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>