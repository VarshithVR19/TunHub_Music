package com.example.demo.entites;

import java.util.List;

import org.hibernate.annotations.ManyToAny;
import jakarta.persistence.*;


@Entity
public class Playlist {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		int id;
		String name;
		@ManyToAny
		List<Song> songs;
		
		public Playlist()
		{
			super();
		}
		public Playlist(int id, String name, List<Song> songs) {
			super();
			this.id = id;
			this.name = name;
			this.songs = songs;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Song> getSongs() {
			return songs;
		}
		public void setSongs(List<Song> songs) {
			this.songs = songs;
		}
		@Override
		public String toString() {
			return "Playlist [id=" + id + ", name=" + name + "]";
		}
		
//		public String toString() {
//			return "Playlist [id=" + id + ", name=" + name + ", songs=" + songs + "]";
//		}
		
		
}
