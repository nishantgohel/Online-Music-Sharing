package com.neu.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.neu.POJO.User;

public class AdminInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		System.out.println("interceptor called");
		try
		{
			User user =	(User) session.getAttribute("user");
			if(user==null)
			{
				response.sendRedirect("login.htm");
			}
			else
			{
				String role= user.getUserRole();
				if(role.equals("User"))
				{
					
					response.sendRedirect("logout.htm");
					
					
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
			return true;
			
	}

		/**
		 * This implementation is empty.
		 */
	public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception
	{
		
	}
	

}
