<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Pegas.service.CompanyService" %>
<%@ page import="Pegas.DTO.CompanyDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    	String[] numList = {"one","two","three"};
    	request.setAttribute("numList", numList);
    %>
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
    };
    %>

     <c:if test="${not empty company}">
        <c:forEach var="i" items="${company}">
        <c:out value="${i}"/>
        </c:forEach>
    </c:if>
    	<c:out value='${requestScope["numList"][0]}' /><br/>
    	<c:out value='${requestScope["numList"][2]}'/>
</ul>
</body>
</html>