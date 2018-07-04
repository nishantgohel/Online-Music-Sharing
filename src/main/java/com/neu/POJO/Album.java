package com.neu.POJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="album")
public class Album
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "generalID", unique=true, nullable = false)
	private long generalID;
	
	
	




	@Column(name = "albumName")
	private String albumName;
	
	@Column(name = "songid")
	private long songid;


	

	public long getGeneralID() {
		return generalID;
	}



	public void setGeneralID(long generalID) {
		this.generalID = generalID;
	}
	
	public long getSongid() {
		return songid;
	}



	public void setSongid(long songid) {
		this.songid = songid;
	}



	



	public String getAlbumName() {
		return albumName;
	}



	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	
	
	
	

}
