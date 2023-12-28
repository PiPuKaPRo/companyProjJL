<%--
  Created by IntelliJ IDEA.
  User: Evgeniy
  Date: 27.12.2023
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private CompanyService companyService;
    private DbDepartmentRepository dbDepartmentRepository;
    public void jspInit() {
        companyService = (CompanyService) ((ApplicationContext) getServletContext().getAttribute("ComponentContext"))
                .getBeanFactory().getBean("companyService");
        dbDepartmentRepository = (DbDepartmentRepository) ((ApplicationContext) getServletContext().getAttribute("ComponentContext"))
                .getBeanFactory().getBean("dbDepartmentRepository");

    }
%>
<html>
<head>
    <title>Список всех сотрудников в отделе</title>
</head>
<body>
<h2>Список всех сотрудников в отделе</h2>
<p>
    <%@ page import="edu.vsu.sakovea.repository.DbDepartmentRepository" %>
    <%@ page import="edu.vsu.sakovea.model.Department" %>
    <%@ page import="java.util.List" %>
    <%@ page import="edu.vsu.sakovea.service.CompanyService" %>
    <%@ page import="edu.vsu.sakovea.infra.context.ApplicationContext" %>
    <%@ page import="edu.vsu.sakovea.CompanyApplication" %>
    <%@ page import="edu.vsu.sakovea.controllers.MainController" %>
    <%@ page import="edu.vsu.sakovea.model.Employee" %>

    <%
        if (request.getMethod().equals("POST")) {
            int id = Integer.parseInt(request.getParameter("depname"));
            Department departmentToDisplay = dbDepartmentRepository.getDepartmentById(id);

            if (departmentToDisplay != null) {
                List<Employee> departmentEmployees = companyService.getEmployeesInDepartment(departmentToDisplay);
                for (Employee emp : departmentEmployees) {
                    out.println("ФИО: " + emp.getFull_name() + ", Возраст: " + emp.getAge() + ", ЗП: " + emp.getSalary() + "</p>");
                }
            } else {
                out.println("Отдел не найден.");
            }
        }
    %>
</p>
<form action="getAllEmployeesInDepartment.jsp" method="post">
    <div>
        <label for="depname">Введите id отдела, в котором хотите увидеть сотрудников:</label>
        <input type="text" name="depname" id="depname" required
               value="<%= request.getParameter("depname") != null ? request.getParameter("depname") : "" %>">
    </div>
    <input type="submit" value="Показать">
</form>
</body>
</html>
