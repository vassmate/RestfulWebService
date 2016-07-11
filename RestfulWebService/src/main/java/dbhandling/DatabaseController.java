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
	
	public static String getUrl(HttpServletRequest request){
		return request.getHeader("referer");
	}
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public List<User> getData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, HttpServletRequest request){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.getUser();
		System.out.println(getUrl(request));
		return dbContent.getContent();
	}
	
	@RequestMapping(value="/delete")
	public void removeData(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName){
		DatabaseContent dbContent = new DatabaseContent(firstName, lastName);
		dbContent.removeUser();
	}
	
	@RequestMapping(value="/add_user")
	public void addData(){
		DatabaseContent dbContent = new DatabaseContent();
		dbContent.makeNewUser();
	}
}
