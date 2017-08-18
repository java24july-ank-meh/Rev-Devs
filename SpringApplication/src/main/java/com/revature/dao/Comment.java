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

}
