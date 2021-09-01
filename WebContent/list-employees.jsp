<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.felix.EmployeeDatabase.SortUtils" %>

<!DOCTYPE html>

<html lang="en" dir="ltr">
	<head>
		<meta charset="utf-8">
		<title>Company Employee Database</title>
		<link rel="preconnect" href="https://fonts.gstatic.com">
		<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
	</head>
	<body>
		<header class="clearfix">
			<h1><span>Company</span> Employee Management</h1>
		</header>
		
		<c:url var="sortLinkFirstName" value="/EmployeeDatabaseServlet">
			<c:param name="command" value="sort"/>
			<c:param name="sort" value="<%= Integer.toString(SortUtils.FIRST_NAME) %>" />
		</c:url>
		<c:url var="sortLinkLastName" value="/EmployeeDatabaseServlet">
			<c:param name="command" value="sort"/>
			<c:param name="sort" value="<%= Integer.toString(SortUtils.LAST_NAME) %>" />
		</c:url>
		<c:url var="sortLinkDepartment" value="/EmployeeDatabaseServlet">
			<c:param name="command" value="sort"/>
			<c:param name="sort" value="<%= Integer.toString(SortUtils.DEPARTMENT) %>" />
		</c:url>
		<c:url var="sortLinkEmail" value="/EmployeeDatabaseServlet">
			<c:param name="command" value="sort"/>
			<c:param name="sort" value="<%= Integer.toString(SortUtils.EMAIL) %>" />
		</c:url>
		
		<section>
			<div class="table-container">
				<form class="search-container" action="${pageContext.request.contextPath}/EmployeeDatabaseServlet" method="get">
					<input type="hidden" name="command" value="search" />
					<div class="desc">Search by First or Last Name: </div>
					<input type="text" name="theSearchName" />
					<input type="submit" value="Search" class="search-btn" />
				</form>
				<a href="${pageContext.request.contextPath}/employee-form.jsp" class="add-btn">Add Employee</a>
				<table class="employee-table">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col"><a href="${sortLinkFirstName}">First Name</a></th>
							<th scope="col"><a href="${sortLinkLastName}">Last Name</a></th>
							<th scope="col"><a href="${sortLinkDepartment}">Department</a></th>
							<th scope="col"><a href="${sortLinkEmail}">Email</a></th>
							<th scope="col">Update</th>
							<th scope="col">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${employees}" varStatus="numOfEmps">
							<c:url var="updateEmpLink" value="/EmployeeDatabaseServlet">
								<c:param name="command" value="load"/>
								<c:param name="empId" value="${employee.id}"/>
							</c:url>
							<c:url var="deleteLink" value="/EmployeeDatabaseServlet">
								<c:param name="command" value="delete"/>
								<c:param name="empId" value="${employee.id}"/>
							</c:url>
							<tr>
								<td scope="row"><c:out value="${numOfEmps.count}"/></td>
								<td scope="row"><c:out value="${employee.firstName}"/></td>
								<td scope="row"><c:out value="${employee.lastName}"/></td>
								<td scope="row"><c:out value="${employee.department}"/></td>
								<td scope="row"><c:out value="${employee.email}"/></td>
								<td scope="row"><a class="action-link" href="${updateEmpLink}">Update</a></td>
								<td scope="row"><a class="action-link" href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to remove this employee?'))) return false">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</section>
	</body>
</html>