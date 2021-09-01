package com.felix.EmployeeDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmployeeDatabaseServlet
 */
@WebServlet("/EmployeeDatabaseServlet")
public class EmployeeDatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDbUtil empDbUtil;
	
	@Override
    public void init() throws ServletException {
    	empDbUtil = new EmployeeDbUtil();
    }
       
    public EmployeeDatabaseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		System.out.println("the command: " + theCommand);
		try {
			switch(theCommand) {
			case "load":
				loadEmployee(request, response);
				break;
			case "search":
				searchEmployee(request, response);
				break;
			case "sort":
				sortEmployees(request, response);
				break;
			case "delete":
				deleteEmployee(request, response);
				break;
			default:
				listEmployees(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("/index.jsp");
		}
	}

	private void searchEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String theSearchName = request.getParameter("theSearchName").trim().replaceAll("\\s+", "-").toUpperCase();
		if(theSearchName == null || theSearchName.equals(""))
			listEmployees(request, response);
		List<Employee> emps = empDbUtil.searchEmployee(theSearchName);
		request.setAttribute("employees", emps);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
		dispatcher.forward(request, response);
	}

	private void sortEmployees(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int sortField = Integer.parseInt(request.getParameter("sort"));
		List<Employee> emps = empDbUtil.sortEmployees(sortField);
		request.setAttribute("employees", emps);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		if(theCommand == null)
			theCommand = "list";
		try {
			switch(theCommand) {
			case "list":
				listEmployees(request, response);
				break;
			case "update":
				updateDatabase(request, response);
				break;
			case "load":
				loadEmployee(request, response);
				break;
			default:
				listEmployees(request, response);
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("/index.jsp");
		}
	}
	
	private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// get employees from database
		List<Employee> emps = empDbUtil.getEmployees();
				
		// add employee to the request
		request.setAttribute("employees", emps);
				
		// send to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int theId;
		String empId = request.getParameter("empId");
		String firstName = request.getParameter("firstName").trim().replaceAll("\\s+", "-").toUpperCase();
		String lastName = request.getParameter("lastName").trim().replaceAll("\\s+", "-").toUpperCase();
		String department = request.getParameter("deptRadio");
		String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
		String domain = "@" + department.toLowerCase() + ".company.com";
		if(department.equals("other")) {
			department = "-";
			domain = "@company.com";
		}
		String email = username + domain;
		
		int emailNum = 1;
		while(empDbUtil.isDuplicate(email)) {
			email = username + emailNum + domain;
			emailNum++;
		}
		
		Employee emp;
		if(empId == null || empId.equals("")) {
			// create new employee
			emp = new Employee(firstName, lastName, department, email);
			empDbUtil.addEmployee(emp);
		} else {
			// update existing employee
			theId = Integer.parseInt(empId);
			emp = new Employee(theId, firstName, lastName, department, email);
			empDbUtil.updateEmployee(emp);
		}
		
		listEmployees(request, response);
	}

	private void loadEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String empId = request.getParameter("empId");
		Employee theEmp = empDbUtil.getEmployee(empId);
		request.setAttribute("theEmployee", theEmp);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/employee-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read employee id
		String empId = request.getParameter("empId");
		
		// delete employee from database
		empDbUtil.deleteEmployee(empId);
		
		// send the updated list back
		listEmployees(request, response);
	}

}
