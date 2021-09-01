<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en" dir="ltr">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<title>Company Employee Database</title>
		<link rel="preconnect" href="https://fonts.gstatic.com">
		<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
	</head>
	<body>
		<div class="home-page flex-center">
			<div class="welcome-box">
				<h1>Welcome</h1>
				<form class="link-wrap" action="${pageContext.request.contextPath}/EmployeeDatabaseServlet" method="post">
					<input type="hidden" name="command" value="list" />
					<input type="submit" value="View Employees" />
				</form>
				<div class="link-wrap">
					<a href="${pageContext.request.contextPath}/employee-form.jsp">Add Employee</a>
				</div>
			</div>
		</div>
	</body>
</html>