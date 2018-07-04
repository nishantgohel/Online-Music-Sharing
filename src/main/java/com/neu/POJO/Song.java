package com.neu.POJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;



@Entity
@Table(name="song")
public class Song 
{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "songID", unique=true, nullable = false)
	private long songID;
	
	
	
	@Transient
	private CommonsMultipartFile song;
	
	
	
	
	
	@Column(name = "songName")
	private String songName;
	
	@Column(name = "movieName")
	private String movieName;
	
	@Column(name = "Actor")
	private String actor;
	
	@Column(name = "Actress")
	private String actress;
		
	@Column(name = "fileName")
	private String fileName;
	
	
	

	public long getSongID() {
		return songID;
	}

	public void setSongID(long songID) {
		this.songID = songID;
	}

	public CommonsMultipartFile getSong() {
		return song;
	}

	public void setSong(CommonsMultipartFile song) {
		this.song = song;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getActress() {
		return actress;
	}

	public void setActress(String actress) {
		this.actress = actress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

		
	

}
