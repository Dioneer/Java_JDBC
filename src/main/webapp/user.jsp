<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Pegas.service.CompanyService" %>
<%@ page import="Pegas.DTO.CompanyDTO" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>List of employee:</h1>
<ul>
    <%
    CompanyService companyService = CompanyService.getInstance();
    Long companyId = Long.valueOf(req.getParameter("companyid"));
    for(CompanyDTO i : companyService.findAllByEmployeeId(companyId)){
        out.write(String.format("<li>%s</li>",i.userName()))
    }
    %>
    <li>I-go-go</li>
</ul>
</body>
</html>