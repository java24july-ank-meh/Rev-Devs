package com.revature.application.dao;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long commentId;
	
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="postId")
	private Post post;
	
	@Column 
	private Date date;
	
	@Column
	private String content;

	public Comment(Employee employee, Post post, Date date, String content) {
		super();
		this.employee = employee;
		this.post = post;
		this.date = date;
		this.content = content;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
