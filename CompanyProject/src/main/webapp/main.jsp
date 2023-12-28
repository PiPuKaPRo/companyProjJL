<%--
  Created by IntelliJ IDEA.
  User: Evgeniy
  Date: 26.12.2023
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private CompanyService companyService;
    public void jspInit() {
        companyService = (CompanyService) ((ApplicationContext) getServletContext().getAttribute("ComponentContext"))
                .getBeanFactory().getBean("companyService");
    }
%>
<html>
<head>
    <title>Company</title>
</head>
<body>
<h1>Список всех отделов</h1>
<p>
    <%@ page import="edu.vsu.sakovea.repository.DbDepartmentRepository" %>
    <%@ page import="edu.vsu.sakovea.model.Department" %>
    <%@ page import="java.util.List" %>
    <%@ page import="edu.vsu.sakovea.service.CompanyService" %>
    <%@ page import="edu.vsu.sakovea.infra.context.ApplicationContext" %>
    <%@ page import="edu.vsu.sakovea.CompanyApplication" %>
    <%@ page import="edu.vsu.sakovea.controllers.MainController" %>
    <%
        DbDepartmentRepository departmentController = new DbDepartmentRepository();
        List<Department> allDepartments = departmentController.getAllDepartments();
        for (Department dep : allDepartments) {
            out.println("Отдел: " + dep.getName() + "</p>");
        }
    %>

    <%
        if (request.getMethod().equals("POST")) {
            String departmentName = request.getParameter("name");
            Department department = new Department(departmentName);
            companyService.addDepartment(department);
        }
    %>

    <%
        if (request.getMethod().equals("POST")) {
            int id = Integer.parseInt(request.getParameter("delname"));
            Department departmentForDel = new Department(id);
            companyService.deleteDepartment(departmentForDel);
        }
    %>
</p>
<h2>Выберите операцию, которую хотите совершить</h2>
<div>
    <ul>
        <li><a href="addEmployee.jsp">Добавить сотрудника</a></li>
        <li><a href="getAllEmployeesInDepartment.jsp">Посмотреть сотрудников в отделе</a></li>
    </ul>
    <form action="main.jsp" method="post">
        <div>
            <label for="name">Введите название отдела, который хотите добавить:</label>
            <input type="text" name="name" id="name" required
                   value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">
        </div>
        <input type="submit" value="Добавить">
    </form>
    <form action="main.jsp" method="post">
        <div>
            <label for="delname">Введите id отдела, который хотите удалить:</label>
            <input type="text" name="delname" id="delname" required
                   value="<%= request.getParameter("delname") != null ? request.getParameter("delname") : "" %>">
        </div>
        <input type="submit" value="Удалить">
    </form>
</div>
</body>
</html>
