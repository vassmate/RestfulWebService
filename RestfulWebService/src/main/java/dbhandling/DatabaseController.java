package dbhandling;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import employees.Employee;

@RestController
public class DatabaseController {
	
	//Get the client's URL for debugging or CORS configuration
	public static String getUrl(HttpServletRequest request){
		return request.getHeader("referer");
	}
	
	//Get a specified employee from the database
	//Returns with a Employee() object List in JSON form
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public List<Employee> getData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, 
			HttpServletRequest request){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.getEmployeeFromDatabase();
		System.out.println(getUrl(request));
		return dbContent.getEmployees();
	}
	
	//Deletes a employee based on the given parameters
	@RequestMapping(value="/delete_user")
	public void removeData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.removeEmployee();
	}
	
	//Adds a new employee entry in the database
	@RequestMapping(value="/add_user")
	public void addData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, 
			@RequestParam(value="email") String email, @RequestParam(value="phone") int phone, 
			@RequestParam(value="hire_date") String hire_date, @RequestParam(value="job_id") String job_id, 
			@RequestParam(value="salary") int salary, @RequestParam(value="commission_pct") float commission_pct, 
			@RequestParam(value="manager_id") int manager_id, @RequestParam(value="department_id") int department_id){
		
		Employee newEmployee = new Employee(0, firstName, lastName, email, phone, 
				hire_date, job_id, salary, commission_pct, manager_id, department_id);
		
		DatabaseContent dbContent = new DatabaseContent(newEmployee);
		dbContent.makeNewEmployee();
	}
	
	//Updates a specified employee's data at the given column (paramToChange) with the given value (newParam)
	@RequestMapping(value="/update_user")
	public void updateUser(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, 
			@RequestParam(value="paramToChange") String paramToChange, @RequestParam(value="newParam") String newParam) {
		
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.updateEmployeeData(paramToChange, newParam);
	}
}
