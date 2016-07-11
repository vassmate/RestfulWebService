package dbhandling;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import users.User;

@RestController
public class DatabaseController {
	
	//Get the client's URL for debugging or CORS configuration
	public static String getUrl(HttpServletRequest request){
		return request.getHeader("referer");
	}
	
	//Get a specified user from the database
	//Returns with a User() object List in JSON form
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public List<User> getData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, 
			HttpServletRequest request){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.getUser();
		System.out.println(getUrl(request));
		return dbContent.getContent();
	}
	
	//Deletes a user based on the given parameters
	@RequestMapping(value="/delete_user")
	public void removeData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.removeUser();
	}
	
	//Adds a new user entry in the database
	@RequestMapping(value="/add_user")
	public void addData(){
		DatabaseContent dbContent = new DatabaseContent();
		dbContent.makeNewUser();
	}
	
	//Updates a specified users data at the given column (paramToChange) with the given value (newParam)
	@RequestMapping(value="/update_user")
	public void updateUser(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, 
			@RequestParam(value="paramToChange") String paramToChange, @RequestParam(value="newParam") String newParam) {
		
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.updateUserData(paramToChange, newParam);
	}
}
