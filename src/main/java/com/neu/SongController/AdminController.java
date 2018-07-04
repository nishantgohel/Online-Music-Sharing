package com.neu.SongController;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.DAO.UserDAO;
import com.neu.POJO.Song;
import com.neu.POJO.User;



@Controller
public class AdminController 
{
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	

	@Autowired
	ServletContext servletContext;
	
	
	@RequestMapping(value = "search-user.htm", method = RequestMethod.GET)
	public ModelAndView seacrhAllUser(HttpServletRequest request)
	{
		 List<User> userList = null;
		 
		 userList = userDao.getAllUser();
		 
		 return new ModelAndView("admin-browse-user","userList",userList);
	}
	
	
	@RequestMapping(value = "search-all-song.htm", method = RequestMethod.GET)
	public ModelAndView seacrhAllSong(HttpServletRequest request)
	{
		 List<Song> songList = null;
		 
		 songList = userDao.getAllSongs();
		 
		 return new ModelAndView("admin-browse-song","songList",songList);
	}
	
	
	
	@RequestMapping(value = "admin-search.htm", method = RequestMethod.GET)
	public ModelAndView adminSearch(HttpServletRequest request)
	{
		return new ModelAndView("admin-page");
	}
	

}
