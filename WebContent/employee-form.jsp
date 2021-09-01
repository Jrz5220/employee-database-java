<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en" dir="ltr">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Employee Form</title>
		<link rel="preconnect" href="https://fonts.gstatic.com">
		<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
	</head>
	<body>
		<header class="clearfix">
			<div class="container">
				<h1><span>Company</span> Employee Management</h1>
				<nav>
					<ul>
						<li>
							<form class="link-wrap" action="${pageContext.request.contextPath}/EmployeeDatabaseServlet" method="post">
								<input type="hidden" name="command" value="list">
								<input type="submit" value="Back to List" class="back-to-list">
							</form>
						</li>
					</ul>
				</nav>
			</div>
		</header>
		<section>
			<div class="form-container">
				<div class="form-header-wrapper">
					<h3 class="form-header">Generate Employee Email</h3>
				</div>
				<form class="form-employee" action="${pageContext.request.contextPath}/EmployeeDatabaseServlet" method="post">
					<input type="hidden" name="command" value="update" />
					<input type="hidden" name="empId" value="${theEmployee.id}" />
					<label>
						First Name:
						<input type="text" name="firstName" value="${theEmployee.firstName}" required/>
					</label>
					<label>
						Last Name: 
						<input type="text" name="lastName" value="${theEmployee.lastName}" required />
					</label>
					<div class="radio-buttons">
						Department:
						<div class="department">
							<input class="department-btn" type="radio" name="deptRadio" value="Sales" checked>
							<label>Sales</label>
						</div>
						<div class="department">
							<input class="department-btn" type="radio" name="deptRadio" value="Development">
							<label>Development</label>
						</div>
						<div class="department">
							<input class="department-btn" type="radio" name="deptRadio" value="Accounting">
							<label>Accounting</label>
						</div>
						<div class="department">
							<input class="department-btn" type="radio" name="deptRadio" value="other">
							<label>Other</label>
						</div>
					</div>
					<div class="save-btn-wrapper">
						<input type="submit" value="Save" class="save-btn" />
					</div>
				</form>
			</div>
		</section>
	</body>
</html>