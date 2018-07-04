package com.neu.SongController;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.neu.DAO.UserDAO;
import com.neu.POJO.Song;
import com.neu.POJO.User;
import com.neu.Validator.SongValidator;
import com.neu.Validator.UserValidator;




/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{
	
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	
	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;
	
	
	
	
	

	@InitBinder
	private void initBinder(WebDataBinder binder)
	{
		binder.setValidator(validator);
				
	}
	
	@RequestMapping(value = "login.htm", method = RequestMethod.GET)
	protected ModelAndView login() throws Exception 
	{
		return new ModelAndView("login");
	
	}
	
	
	@RequestMapping(value = "register.htm", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");
		
		 Map<String,String> userRole = new LinkedHashMap<String,String>();
		 userRole.put("User", "User");
		 userRole.put("Admin", "Admin");
		
		 ModelAndView mav = new ModelAndView("register");
		 mav.addObject("userRole",userRole);
		 mav.addObject( "user", new User());
		
		 return mav;
		 
		//return new ModelAndView("register", "user", new User());

	}
	
	
	
	@RequestMapping(value = "register.htm", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult result) throws Exception
	{

		validator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		try {

			System.out.print("registerNewUser");

			
			String emailid = request.getParameter("email");
			
			
			
			User u = userDao.register(user);
			
			request.getSession().setAttribute("user", u);
			
			
			Email email = new SimpleEmail();
	        email.setHostName("smtp.googlemail.com");
	        email.setSmtpPort(465);
	        email.setAuthenticator(new DefaultAuthenticator("nishantgohel1819@gmail.com", "wandrers1819"));
	        email.setSSLOnConnect(true);
	        email.setFrom("nishantgohel1819@gmail.com");
	        email.setSubject("TestMail");
	        email.setMsg("You Registered Successfully....");
	        email.addTo(emailid);
	        email.send();
			
			
			
			
			
			
				return new ModelAndView("index", "user", u);
			
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}
	
	
	@RequestMapping(value = "login.htm", method = RequestMethod.POST)
	public ModelAndView loginUser(HttpServletRequest request) throws Exception 
	{
		
		HttpSession session = (HttpSession) request.getSession();
		
		try
		{

			System.out.print("loginUser");

			User u = userDao.get(request.getParameter("username"), request.getParameter("password"));
			
			if(u == null)
			{
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return new ModelAndView("error");
			}
			
			
			
			session.setAttribute("user", u);
			
			
			
			String role = u.getUserRole();
			
			if(role.equals("Admin"))
			{
				return new ModelAndView("admin-page");
			}
			
			
			return new ModelAndView("user-page");

		} 
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView("error");
		}
		
		
	}
	
	
	/*@RequestMapping(value = "browseuser.htm", method = RequestMethod.POST)
	public ModelAndView seacrhSong(HttpServletRequest request)
	{
		 List<User> userList = null;
		 
		 userList = userDao.getAllUser();
		 
		 return new ModelAndView("admin-browse-user","userList",userList);
	}*/
	
	
	@RequestMapping(value = "logout.htm", method = RequestMethod.GET)
	public ModelAndView gotoHomePage(HttpServletRequest request)
	{	
		HttpSession session = request.getSession();
		if(session != null)
		    session.invalidate();
		System.out.println("You Logged Out Successfully");
		 return new ModelAndView("index");
	}
	
	
	
}
