package com.neu.SongController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.neu.DAO.UserDAO;
import com.neu.POJO.Album;
import com.neu.POJO.Song;
import com.neu.POJO.User;
import com.neu.Validator.SongValidator;

@Controller
public class AddSongController 
{
	
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	
	@Autowired
	@Qualifier("songValidator")
	SongValidator songvalidator;
	
	@Autowired
	ServletContext servletContext;
	
	
	@InitBinder
	private void initBinder(WebDataBinder binder)
	{
		binder.setValidator(songvalidator);
				
	}

	@RequestMapping(value = "choice.htm", method = RequestMethod.POST)
	public ModelAndView choice(HttpServletRequest request)
	{
		
		HttpSession session = request.getSession(true);
        String choice = request.getParameter("choice");
        
        if(choice.equals("browse"))
        {
            return new ModelAndView("search-song");
        }
        else if(choice.equals("insert"))
        {
            return new ModelAndView("add-song", "songs", new Song());
        }
        
        return null;
	}
	
	
	@RequestMapping(value = "add-song.htm", method = RequestMethod.GET)
	public ModelAndView addSong(HttpServletRequest request,@ModelAttribute("songs") Song songs,BindingResult result)
	{
		return new ModelAndView("add-song", "songs", new Song());
	}
	
	
	@RequestMapping(value = "user-page.htm", method = RequestMethod.GET)
	public ModelAndView userPage(HttpServletRequest request)
	{
		return new ModelAndView("user-page");
	}
	
	
	@RequestMapping(value = "search-song-again.htm", method = RequestMethod.GET)
	public ModelAndView searchSongAgain(HttpServletRequest request)
	{
		return new ModelAndView("search-song");
	}
	
	
	
	@RequestMapping(value = "add-song.htm", method = RequestMethod.POST)
	public ModelAndView addSongPartTwo(HttpServletRequest request,@ModelAttribute("songs") Song songs,BindingResult result) 
	{
		
		HttpSession session = (HttpSession) request.getSession();
		
		songvalidator.validate(songs, result);

		if (result.hasErrors()) 
		{
			return new ModelAndView("add-song", "songs", songs);
		}
				
		try 
		{
			if (songs.getFileName().trim() != "" || songs.getFileName() != null)
			{
				File directory;
				String check = File.separator; 
												
												
				String path = null;
				if (check.equalsIgnoreCase("\\"))
				{
					path = servletContext.getRealPath("").replace("build\\", ""); 
																				  
				}
				
				String albumName = songs.getFileName();
				albumName = albumName.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
				
				
				

				
				directory = new File(path + "\\" + albumName);
				boolean temp = directory.exists();
				if (!temp)
				{
					temp = directory.mkdir();
				}
				if (temp)
				{
					
					CommonsMultipartFile songInMemory = songs.getSong();

					String fileName = songInMemory.getOriginalFilename();
					

					File localFile = new File(directory.getPath(), fileName);

					
					songInMemory.transferTo(localFile);
					songs.setFileName(localFile.getPath());
					
					System.out.println("File is stored at" + localFile.getPath());
					
					
					
					
					
					
					
					Song s = userDao.registerSong(songs);
					long songidentity = s.getSongID();
					userDao.registerAlbum(albumName,songidentity);
					
					

				}
				else
				{
					System.out.println("Failed to create directory!");
				}
			}

		}
		catch (IllegalStateException e) 
		{
			System.out.println("*** IllegalStateException: " + e.getMessage());
		} 
		catch (IOException e) 
		{
			
			System.out.println("*** IOException: " + e.getMessage());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ModelAndView("song-success");
		
	}
	
	
	
	
	@RequestMapping(value = "search.htm", method = RequestMethod.POST)
	public ModelAndView seacrhSong(HttpServletRequest request)
	{
		
		HttpSession session = request.getSession();
		
		
		
		String key=request.getParameter("inputtext");
		key = key.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
		String searchKey=request.getParameter("searchkey");
		
		int index= 0;
		int pageno=1;
		session.setAttribute("index", index);
        session.setAttribute("key", key);
        session.setAttribute("searchKey", searchKey);
        session.setAttribute("pageno", pageno);
        
        List<Song> songList = null;
		/*try
		{
			songList = userDao.getSong(key,searchKey);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        if (searchKey.equals("moviename")) 
        {
        	songList = userDao.getSongByMovieName(key,searchKey,index);
        }
        
        if (searchKey.equals("songname")) 
        {
        	songList = userDao.getSongBySongName(key,searchKey,index);
        }
        
        if (searchKey.equals("actor")) 
        {
        	songList = userDao.getSongByActor(key,searchKey,index);
        }
        
        if (searchKey.equals("actress")) 
        {
        	songList = userDao.getSongByActress(key,searchKey,index);
        }
        
        session.setAttribute("key", key);
        return new ModelAndView("song-result", "songList", songList);
        
		
	}
	
	
	@RequestMapping(value = "search.htm", method = RequestMethod.POST,params="next")
	public ModelAndView searchNextPageSong(HttpServletRequest request)
	{
		
		HttpSession session = request.getSession();
		
		
		
		String key= (String) session.getAttribute("key");
		String searchKey= (String) session.getAttribute("searchKey");
		int index = (Integer) session.getAttribute("index");
		int pageno = (Integer) session.getAttribute("pageno");
		
		index=index+5;
		pageno=pageno+1;
		
		session.setAttribute("pageno",pageno);
		session.setAttribute("index",index);
        
        List<Song> songList = null;
		/*try
		{
			songList = userDao.getSong(key,searchKey);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        if (searchKey.equals("moviename")) 
        {
        	songList = userDao.getSongByMovieName(key,searchKey,index);
        }
        
        if (searchKey.equals("songname")) 
        {
        	songList = userDao.getSongBySongName(key,searchKey,index);
        }
        
        if (searchKey.equals("actor")) 
        {
        	songList = userDao.getSongByActor(key,searchKey,index);
        }
        
        if (searchKey.equals("actress")) 
        {
        	songList = userDao.getSongByActress(key,searchKey,index);
        }
        
        session.setAttribute("key", key);
        return new ModelAndView("song-result", "songList", songList);
		
	}
	
	
	
	@RequestMapping(value = "search.htm", method = RequestMethod.POST,params="prev")
	public ModelAndView searchPrevPageSong(HttpServletRequest request)
	{
		
		HttpSession session = request.getSession();
		
		
		
		String key= (String) session.getAttribute("key");
		String searchKey= (String) session.getAttribute("searchKey");
		int index = (Integer) session.getAttribute("index");
		int pageno = (Integer) session.getAttribute("pageno");
			
		index=index-5;
		pageno=pageno-1;
		
		session.setAttribute("pageno",pageno);
		session.setAttribute("index",index);
        
        List<Song> songList = null;
		/*try
		{
			songList = userDao.getSong(key,searchKey);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        if (searchKey.equals("moviename")) 
        {
        	songList = userDao.getSongByMovieName(key,searchKey,index);
        }
        
        if (searchKey.equals("songname")) 
        {
        	songList = userDao.getSongBySongName(key,searchKey,index);
        }
        
        if (searchKey.equals("actor")) 
        {
        	songList = userDao.getSongByActor(key,searchKey,index);
        }
        
        if (searchKey.equals("actress")) 
        {
        	songList = userDao.getSongByActress(key,searchKey,index);
        }
        
        session.setAttribute("key", key);
        return new ModelAndView("song-result", "songList", songList);
		
	}
	
	
}

