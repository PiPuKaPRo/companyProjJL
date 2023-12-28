<%@ page import="edu.vsu.sakovea.model.Employee" %>
<%@ page import="edu.vsu.sakovea.service.EmployeeService" %>
<%@ page import="edu.vsu.sakovea.model.Department" %>
<%@ page import="edu.vsu.sakovea.repository.DbDepartmentRepository" %>
<%@ page import="edu.vsu.sakovea.service.CompanyService" %>
<%@ page import="edu.vsu.sakovea.infra.context.ApplicationContext" %><%--
  Created by IntelliJ IDEA.
  User: Evgeniy
  Date: 27.12.2023
  Time: 21:58
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
<%
    if (request.getMethod().equals("POST")) {
        String fullName = request.getParameter("fullname");
        int age = Integer.parseInt(request.getParameter("age"));
        int salary = Integer.parseInt(request.getParameter("salary"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));

        Employee employee = new Employee(fullName, age, salary);
        Department employeeDepartment = dbDepartmentRepository.getDepartmentById(departmentId);
        if (employeeDepartment != null) {
            companyService.addEmployee(employee, employeeDepartment);
            response.sendRedirect("main.jsp");
        } else {
            out.println("Отдел не найден.");
        }
    }
%>
<html>
<head>
    <title>Добавление сотрудника</title>
</head>
<body>
<h1>Добавьте сотрудника</h1>
<form action="addEmployee.jsp" method="post">
    <div>
        <label for="fullname">Введите ФИО сотрудника:</label>
        <input type="text" name="fullname" id="fullname" required
               value="<%= request.getParameter("fullname") != null ? request.getParameter("fullname") : "" %>">
    </div>
    <div>
        <label for="age">Введите возраст сотрудника:</label>
        <input type="age" name="age" id="age" required
               value="<%= request.getParameter("age") != null ? request.getParameter("age") : "" %>">
    </div>
    <div>
        <label for="salary">Введите ЗП сотрудника:</label>
        <input type="text" name="salary" id="salary" required
               value="<%= request.getParameter("salary") != null ? request.getParameter("salary") : "" %>">
    </div>
    <div>
        <label for="departmentId">Введите id отдела, в котором работает сотрудник:</label>
        <input type="departmentId" name="departmentId" id="departmentId" required
               value="<%= request.getParameter("departmentId") != null ? request.getParameter("departmentId") : "" %>">
    </div>
    <input type="submit" value="Добавить">
</form>
</body>
</html>
