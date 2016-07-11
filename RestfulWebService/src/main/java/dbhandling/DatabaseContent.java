package dbhandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import users.User;

public class DatabaseContent {
	
	private String requestedUserFirstName;
	private String requestedUserLastName;
	private Connection databaseConnection;
	private List<User> users = new ArrayList<>();
	
	//Constructors to call the connectDatabase()
	//and to pass in some data at a new DatabaseContent() initialization for search and stuff
	//They will be either reworked or completely removed
	public DatabaseContent(){
		connectToDatabase();
	}
	
	public DatabaseContent(String requestedUserFirstName, String requestedUserLastName) {
		this.requestedUserFirstName = requestedUserFirstName;
		this.requestedUserLastName = requestedUserLastName;
		connectToDatabase();
	}

	//Getters, setters
	public List<User> getContent() {
		return users;
	}
	
	public String getRequestedUserFirstName() {
		return requestedUserFirstName;
	}

	public void setRequestedUserFirstName(String requestedUserFirstName) {
		this.requestedUserFirstName = requestedUserFirstName;
	}

	public String getRequestedUserLastName() {
		return requestedUserLastName;
	}

	public void setRequestedUserLastName(String requestedUserLastName) {
		this.requestedUserLastName = requestedUserLastName;
	}
	
	//Establish connection with database
	public void connectToDatabase(){
		try {
			databaseConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hr_db", "root", "vassmate1991");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Get the specified users data from the database
	//Based on getRequestedUserFirstName() and getRequestedUserLastName()
	public void getUser(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			ResultSet dbResultSet = dbStatement.executeQuery(
					"SELECT * FROM hr_db.employees WHERE FIRST_NAME = '" + getRequestedUserFirstName() + 
					"' AND LAST_NAME = '" + getRequestedUserLastName() + "'");
			
			while (dbResultSet.next()) {
				User user = new User(dbResultSet.getInt("EMPLOYEE_ID"), dbResultSet.getString("FIRST_NAME"), 
						dbResultSet.getString("LAST_NAME"), dbResultSet.getString("EMAIL"), dbResultSet.getString("PHONE_NUMBER"));
				users.add(user);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Remove the given users data from the database
	//Based on getRequestedUserFirstName() and getRequestedUserLastName()
	public void removeUser(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			dbStatement.execute(
					"DELETE FROM hr_db.employees WHERE FIRST_NAME = '" + getRequestedUserFirstName() + 
					"' AND LAST_NAME = '" + getRequestedUserLastName() + "'");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Makes a new user entry in the database
	//Currently work in progress, can't handle data from user
	public void makeNewUser(){
		try {
			Statement dbStatement = databaseConnection.createStatement();
			
			//Get the last user id
			ResultSet dbResultSet = dbStatement.executeQuery("SELECT * FROM hr_db.employees ORDER BY EMPLOYEE_ID DESC LIMIT 1");
			int lastUserId = 0;
			while (dbResultSet.next()) {
				lastUserId += dbResultSet.getInt("EMPLOYEE_ID") + 1;
			}
			
			//Insert new user into the table
			dbStatement.execute(
					"INSERT INTO hr_db.employees(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, "
					+ "HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID)"
					+ " VALUES(" + lastUserId + ", 'firstName', 'lastName', 'email', 123456789, '1999-01-01', 'JOB_ID', 2500, 0.40, 100, 30)");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	//Updates the selected users data in the specified column (paramToChange) with the specified value (newParam)
	public void updateUserData(String paramToChange, String newParam) {
		try {
			Statement dbStatement = databaseConnection.createStatement();
			dbStatement.execute("UPDATE hr_db.employees SET "+ paramToChange + " = '" + newParam +
					"' WHERE FIRST_NAME = '" + getRequestedUserFirstName() + "' AND LAST_NAME = '" + getRequestedUserLastName() + "'");
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
