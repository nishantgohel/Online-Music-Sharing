package com.neu.SongController;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.neu.DAO.UserDAO;
import com.neu.POJO.User;
import com.neu.PdfView.PdfView;


@Controller
public class PdfViewController extends PdfView
{
	

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "search-user-pdf.htm", method = RequestMethod.GET)
	public ModelAndView showPdfReport(ModelMap model,HttpServletRequest request) throws Exception
	{

		 List<User> userList = null;
		 userList = userDao.getAllUser();
		 
		  model.addAttribute("userList", userList);
		  
		  View view = new PdfView();
		  return new ModelAndView(view);
	}
	

}
