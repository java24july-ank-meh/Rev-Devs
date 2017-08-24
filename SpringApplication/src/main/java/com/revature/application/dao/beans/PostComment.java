package com.revature.application.dao.beans;

import java.util.Date;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

@Entity
public class PostComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;

	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;

	@Column
	private Date commented;

	@Column
	private String content;

	public PostComment() {

	}

	public PostComment(Employee employee, Post post, Date commented, String content) {
		super();
		this.employee = employee;
		this.post = post;
		this.commented = commented;
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

	public Date getCommented() {
		return commented;
	}

	public void setCommented(Date commented) {
		this.commented = commented;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PostComment [commentId=" + commentId + ", employee=" + employee + ", post=" + post + ", commented="
				+ commented + ", content=" + content + "]";
	}

}
