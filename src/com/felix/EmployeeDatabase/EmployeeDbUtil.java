package com.felix.EmployeeDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDbUtil {
	
	public EmployeeDbUtil() {
	}

	public List<Employee> getEmployees() throws SQLException {
		List<Employee> emps = new ArrayList<>();
		
		//JDBC objects
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			//get database connection
			myConn = DBconnection.createNewDbConnection();
			
			//create a SQL statement
			String sql = "select * from employee";
			myStmt = myConn.createStatement();
			
			//execute query
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				//process result set
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String dept = myRs.getString("department");
				String email = myRs.getString("email");
				
				Employee emp = new Employee(id, firstName, lastName, dept, email);
				
				emps.add(emp);
			} //end while
			
			return emps;
		}
		finally {
			//close JDBC objects
			close(myConn, myStmt, myRs);
		}
	} //end getEmployees
	
	public void addEmployee(Employee newEmp) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			//get database connection
			myConn = DBconnection.createNewDbConnection();
			
			//create SQL statement for insert
			String sql = "insert into employee (first_name, last_name, department, email) values (?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			
			//set the parameters for the prepared statement
			myStmt.setString(1, newEmp.getFirstName());
			myStmt.setString(2, newEmp.getLastName());
			myStmt.setString(3, newEmp.getDepartment());
			myStmt.setString(4, newEmp.getEmail());
			
			//execute prepared statement
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	} //end addEmployee
	
	public Employee getEmployee(String theEmpId) throws SQLException {
		Employee emp = null;
		int empId;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			empId = Integer.parseInt(theEmpId);
			
			myConn = DBconnection.createNewDbConnection();
			
			String sql = "select * from employee where id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, empId);
			
			myRs = myStmt.executeQuery();
			
			//retrieve data from result set row
			if(myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String department = myRs.getString("department");
				String email = myRs.getString("email");
				
				emp = new Employee(id, firstName, lastName, department, email);
			} else {
				//throw new Exception("Could not find employee id: " + theEmpId);
				System.out.println("employee not found: " + theEmpId);
			}
			
			return emp;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public void updateEmployee(Employee theEmp) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = DBconnection.createNewDbConnection();
			
			String sql = "update employee set first_name=?, last_name=?, department=?, email=? where id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theEmp.getFirstName());
			myStmt.setString(2, theEmp.getLastName());
			myStmt.setString(3, theEmp.getDepartment());
			myStmt.setString(4, theEmp.getEmail());
			myStmt.setInt(5, theEmp.getId());
			
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	} //end updateEmployee
	
	public void deleteEmployee(String theEmpId) throws SQLException {
		int empId;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			empId = Integer.parseInt(theEmpId);
			
			myConn = DBconnection.createNewDbConnection();
			
			String sql = "delete from employee where id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, empId);
			
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	} //end deleteEmployee
	
	public List<Employee> searchEmployee(String theSearchName) throws SQLException {
		List<Employee> emps = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DBconnection.createNewDbConnection();
			String sql = "select * from employee where first_name like '%" + theSearchName + "%' or last_name like '%" + theSearchName + "%'";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String dept = myRs.getString("department");
				String email = myRs.getString("email");
				
				Employee emp = new Employee(id, firstName, lastName, dept, email);
				
				emps.add(emp);
			}
			return emps;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<Employee> sortEmployees(int theSortField) throws SQLException {
		List<Employee> emps = new ArrayList<>();
		String theFieldName;
		
		switch(theSortField) {
		case SortUtils.FIRST_NAME:
			theFieldName = "first_name";
			break;
		case SortUtils.LAST_NAME:
			theFieldName = "last_name";
			break;
		case SortUtils.DEPARTMENT:
			theFieldName = "department";
			break;
		case SortUtils.EMAIL:
			theFieldName = "email";
			break;
		default:
			theFieldName = "last_name";
		}
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = DBconnection.createNewDbConnection();
			String sql = "select * from employee order by " + theFieldName;
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String dept = myRs.getString("department");
				String email = myRs.getString("email");
				Employee emp = new Employee(id, firstName, lastName, dept, email);
				emps.add(emp);
			}
			return emps;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public boolean isDuplicate(String email) {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = DBconnection.createNewDbConnection();
			String sql = "select * from employee where email = '" + email + "'";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			return myRs.next();
		} catch(SQLException e) {
			return false;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs != null) {
				myRs.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myConn != null) {
				myConn.close();		//doesn't close the database connection, just makes it an available connection in the connection pool
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} //end close

}
