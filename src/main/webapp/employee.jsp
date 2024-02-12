<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Pegas.DTO.EmployeeDto" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
    <span>Content Russian</span>
    <%List<EmployeeDto>employee = (List)request.getAttribute("employee");
    EmployeeDto employeeDto = employee.get(0);%>
    <b><%=employeeDto.id()%></b>
    <b><%=employeeDto.description()%></b>
    <b><%=employeeDto.id_company()%></b>
    </div>
</body>
</html>