package com.revature.dao;

import javax.persistence.*;

@Entity
public class Comment {

	@Id
	private long comment_id;
	
	private long user_id;
	
	private long post_id;
	
	private String content;
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(long comment_id, long user_id, long post_id, String content) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.post_id = post_id;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", user_id=" + user_id + ", post_id=" + post_id + ", content="
				+ content + "]";
	}

	public long getComment_id() {
		return comment_id;
	}

	public void setComment_id(long comment_id) {
		this.comment_id = comment_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getPost_id() {
		return post_id;
	}

	public void setPost_id(long post_id) {
		this.post_id = post_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
