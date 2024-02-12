<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Pegas.service.CompanyService" %>
<%@ page import="Pegas.DTO.CompanyDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/fanctions">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt">

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>List of employee:</h1>
<ul>
    <%
    CompanyService companyService = CompanyService.getINSTANCE();
    Long companyId = Long.valueOf(request.getParameter("companyid"));
    for(CompanyDTO i : companyService.findAllByEmployeeId(companyId)){
        out.write(String.format("<li>%s</li>",i.userName()));
    }
    %>
    <c:forEach var="company" items="${requestScope.company}">
    <li>"${company.userName()}"</li>
    </c:forEach>

</ul>
</body>
</html>