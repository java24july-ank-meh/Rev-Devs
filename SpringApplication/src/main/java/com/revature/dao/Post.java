package com.revature.dao;

import javax.persistence.*;

@Entity
public class Post {
	
	@Id
	private long post_id;
	
	private long location_id;
	
	private long user_id;
	
	private long type_id;
	
	private String content;
	
	public Post() {
		// TODO Auto-generated constructor stub
	}
	
	public Post(long post_id, long location_id, long user_id, long type_id, String content) {
		super();
		this.post_id = post_id;
		this.location_id = location_id;
		this.user_id = user_id;
		this.type_id = type_id;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Post [post_id=" + post_id + ", location_id=" + location_id + ", user_id=" + user_id + ", type_id="
				+ type_id + ", content=" + content + "]";
	}

	public long getPost_id() {
		return post_id;
	}

	public void setPost_id(long post_id) {
		this.post_id = post_id;
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getType_id() {
		return type_id;
	}

	public void setType_id(long type_id) {
		this.type_id = type_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
