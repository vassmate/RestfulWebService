package dbhandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import employees.Employee;

public class DatabaseContent {
	
	//Requested data from the user and to the user
	private String requestedEmployeeFirstName;
	private String requestedEmployeeLastName;
	private Employee employee;
	private List<Employee> employees = new ArrayList<>();
	
	//Database credentials
	private Connection databaseConnection;
	private final String databaseAddress = "jdbc:mysql://127.0.0.1:3306/hr_db";
	private final String databaseUser = "root";
	private final String databasePassword = "vassmate1991";
	
	//Constructors to call the connectDatabase()
	//and to pass in some data at a new DatabaseContent() initialization for:
	// #1 making a new employee entry in the database
	// #2 searching employees in the database
	public DatabaseContent(Employee employee){
		this.employee = employee;
		connectToDatabase();
	}
	
	public DatabaseContent(String requestedUserFirstName, String requestedUserLastName) {
		this.requestedEmployeeFirstName = requestedUserFirstName;
		this.requestedEmployeeLastName = requestedUserLastName;
		connectToDatabase();
	}

	//Getters, setters
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public String getRequestedEmployeeFirstName() {
		return requestedEmployeeFirstName;
	}

	public void setRequestedEmployeeFirstName(String requestedUserFirstName) {
		this.requestedEmployeeFirstName = requestedUserFirstName;
	}

	public String getRequestedEmployeeLastName() {
		return requestedEmployeeLastName;
	}

	public void setRequestedEmployeeLastName(String requestedUserLastName) {
		this.requestedEmployeeLastName = requestedUserLastName;
	}

	//Establish connection with database
	public void connectToDatabase(){
		try {
			databaseConnection = DriverManager.getConnection(databaseAddress, databaseUser, databasePassword);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Get the specified employee's data from the database
	//Based on getRequestedEmployeeFirstName() and getRequestedEmployeeLastName()
	public void getEmployeeFromDatabase(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			ResultSet dbResultSet = dbStatement.executeQuery(
					"SELECT * FROM hr_db.employees WHERE FIRST_NAME = '" + getRequestedEmployeeFirstName() + 
					"' AND LAST_NAME = '" + getRequestedEmployeeLastName() + "'");
			
			while (dbResultSet.next()) {
				Employee user = new Employee(dbResultSet.getInt("EMPLOYEE_ID"), dbResultSet.getString("FIRST_NAME"), 
						dbResultSet.getString("LAST_NAME"), dbResultSet.getString("EMAIL"), dbResultSet.getInt("PHONE_NUMBER"), 
						dbResultSet.getString("HIRE_DATE"), dbResultSet.getString("JOB_ID"), dbResultSet.getInt("SALARY"), 
						dbResultSet.getFloat("COMMISSION_PCT"), dbResultSet.getInt("MANAGER_ID"), dbResultSet.getInt("DEPARTMENT_ID"));
				employees.add(user);
			}
			
			databaseConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Remove the given employee's data from the database
	//Based on getRequestedEmployeeFirstName() and getRequestedEmployeeLastName()
	public void removeEmployee(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			dbStatement.execute(
					"DELETE FROM hr_db.employees WHERE FIRST_NAME = '" + getRequestedEmployeeFirstName() + 
					"' AND LAST_NAME = '" + getRequestedEmployeeLastName() + "'");
			
			databaseConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Makes a new employee entry in the database
	//Currently work in progress, can't handle data from user
	public void makeNewEmployee(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			
			//Get the last employee id for generating new valid id
			ResultSet dbResultSet = dbStatement.executeQuery(
					"SELECT * FROM hr_db.employees ORDER BY EMPLOYEE_ID DESC LIMIT 1");
			
			int lastUserId = 0;
			while (dbResultSet.next()) {
				lastUserId += dbResultSet.getInt("EMPLOYEE_ID") + 1;
			}
			
			//Set the new valid id
			Employee newEmployee = getEmployee();
			newEmployee.setId(lastUserId);
			
			//Insert new employee into the table
			dbStatement.execute(
					"INSERT INTO hr_db.employees(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, " + 
					"HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) VALUES(" +
					 newEmployee.getId() + ", '" + newEmployee.getFirstName() + "', '" + newEmployee.getLastName() + "', '" + 
					 newEmployee.getEmail() + "', " + newEmployee.getPhone() + ", '" + newEmployee.getHire_date() + "', '" +
					 newEmployee.getJob_id() + "', " + newEmployee.getSalary() + ", " + newEmployee.getCommission_pct() + ", " +
					 newEmployee.getManager_id() + ", " + newEmployee.getDepartment_id() + ")");
			
			databaseConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Updates the selected employee's data in the specified column (paramToChange) with the specified value (newParam)
	public void updateEmployeeData(String paramToChange, String newParam) {
		try {
			Statement dbStatement = databaseConnection.createStatement();
			dbStatement.execute(
					"UPDATE hr_db.employees SET "+ paramToChange + " = '" + newParam +
					"' WHERE FIRST_NAME = '" + getRequestedEmployeeFirstName() + 
					"' AND LAST_NAME = '" + getRequestedEmployeeLastName() + "'");
			
			databaseConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
