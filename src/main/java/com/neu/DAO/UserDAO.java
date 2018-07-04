package com.neu.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.neu.POJO.Album;
import com.neu.POJO.Song;
import com.neu.POJO.User;

import junit.framework.Test;




public class UserDAO extends DAO
{
	
	public UserDAO()
	{
		
	}
	
	Session session = (Session) DAO.getSession();
	
	public User register(User u)
	{
		
		try
		{
			begin();
			System.out.println("inside DAO");

			
			User user = new User();

			String firstname = u.getFirstName();
			firstname = firstname.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			String lastname = u.getLastName();
			lastname = lastname.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			String username = u.getUserName();
			username = username.replaceAll("[^a-zA-Z0-9_.\\s]+", "");


			String password = u.getPassword();
			password = password.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			
			
			
			user.setFirstName(firstname);
			user.setLastName(lastname);
			user.setUserName(username);
			user.setPassword(password);
			user.setEmail(u.getEmail());
			user.setUserRole(u.getUserRole());
			
			
			
			/*user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setUserName(u.getUserName());
			user.setPassword(u.getPassword());
			user.setEmail(u.getEmail());*/
			
			getSession().save(user);
			commit();
			return u;

		}
		
		 catch (Exception e) 
		{
				rollback();
				return null;
				
		}
		
	}
	
	
	public User get(String username, String password) 
	{
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} 
		catch (HibernateException e) 
		{
			rollback();
			return null;
		}
	}
	
	
	public Song registerSong(Song s)
	{
		try {
			begin();
			System.out.println("inside DAO");	
			
			
			Song song = new Song();
			
			String songname = s.getSongName();
			songname = songname.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			String moviename = s.getMovieName();
			moviename = moviename.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			String actor = s.getActor();
			actor = actor.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			String actress = s.getActress();
			actress = actress.replaceAll("[^a-zA-Z0-9_.\\s]+", "");
			
			
			song.setSongName(songname);
			song.setMovieName(moviename);
			song.setActor(actor);
			song.setActress(actress);
			song.setFileName(s.getFileName());
			
			
			
			getSession().save(song);
			commit();
			return song;

		} catch (HibernateException e) {
			rollback();
			return null;
		}
		
	}
	
	
	
	
	public List<Song>getSongByMovieName(String key,String flag, int index)
	{
		/*
		try 
		{
			begin();
			String q="";
			if (flag.equalsIgnoreCase("moviename"))
			{
                q = "from Song where movieName= :movieName";
            } 
			else if (flag.equalsIgnoreCase("songname"))
			{
                q = "from Song where songName= :movieName";
            } 
			else if (flag.equalsIgnoreCase("actor")) 
			{
                q = "from Song where actor= :movieName";
            }
			else if (flag.equalsIgnoreCase("actress")) 
			{
                q = "from Song where actress= :movieName";
            }
			
			
			Query q1 = getSession().createQuery(q);
			q1.setString("movieName",key);		
			List<Song> song = q1.list();
			commit();
			return song;
		}
		catch (HibernateException e) 
		{
			rollback();
			return null;
				
		}
		
	}*/
		
		
		
		
		Criteria criteria = session.createCriteria(Song.class);
		criteria.add(Restrictions.ilike("movieName", "%"+key+"%"));
		criteria.setFirstResult(index);
		criteria.setMaxResults(5);
		
		List<Song> songList =  criteria.list();
		return songList;
		
		
	}
	
	
	
	
	
	public List<Song>getSongBySongName(String key,String flag,int index)
	{
		
		
		Criteria criteria = session.createCriteria(Song.class);
		criteria.add(Restrictions.ilike("songName", "%"+key+"%"));
		criteria.setFirstResult(index);
		criteria.setMaxResults(5);
		List<Song> songList =  criteria.list();
		return songList;
		
		
	}
	
	
	public List<Song>getSongByActor(String key,String flag,int index)
	{
		
		
		Criteria criteria = session.createCriteria(Song.class);
		criteria.add(Restrictions.ilike("actor", "%"+key+"%"));
		criteria.setFirstResult(index);
		criteria.setMaxResults(5);
		List<Song> songList =  criteria.list();
		return songList;
		
		
	}
	
	
	public List<Song>getSongByActress(String key,String flag,int index)
	{
		
		
		Criteria criteria = session.createCriteria(Song.class);
		criteria.add(Restrictions.ilike("actress", "%"+key+"%"));
		criteria.setFirstResult(index);
		criteria.setMaxResults(5);
		List<Song> songList =  criteria.list();
		
		return songList;
		
		
	}
	
	
	public List<User>getAllUser()
	{
		begin();
		String q="";

		 q = "from User where userRole = :userRole";
		 Query q1 = getSession().createQuery(q);
		 q1.setString("userRole", "User");
		 List<User> user = q1.list();
		 commit();
		 return user;
	}
	
	
	public List<Song>getAllSongs()
	{
		begin();
		String q="";

		 q = "from Song";
		 Query q1 = getSession().createQuery(q);
		 List<Song> song = q1.list();
		 commit();
		 return song;
	}
	
	
	public void registerAlbum(String albumname, long songidentity)
	{
		begin();
		
		
		Query q = session.createQuery("from Album where albumname = :albumname ");
		q.setString("albumname", albumname);
		List res = q.list();
		
		Album a1 = null;
		Album alb = null;
		
		if(res.size()!=0)
		{
			
			 alb = (Album)res.get(0);
			 
			 a1 = new Album();
			 a1.setAlbumName(alb.getAlbumName());
			 a1.setSongid(songidentity);
			
		}
		else
		{
			a1 = new Album();
			a1.setAlbumName(albumname);
			a1.setSongid(songidentity);
		}
	
		
			
		getSession().save(a1);
		commit();
		
		
	}
	
}
