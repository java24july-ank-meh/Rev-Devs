package com.revature.application.dao.beans;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long postId;
	
	@ManyToOne
	@JoinColumn(name="locationId")
	private Location location;
	
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="typeId")
	private PostType type;
	
	@Column 
	private Date posted;
	
	@Column
	private String content;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="postId")
	private Set<PostComment> comments;
	
	public Post() {
	}

	public Post(Location location, Employee employee, PostType type, Date posted, String content) {
		super();
		this.location = location;
		this.employee = employee;
		this.type = type;
		this.posted = posted;
		this.content = content;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public PostType getType() {
		return type;
	}

	public void setType(PostType type) {
		this.type = type;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<PostComment> getComments() {
		return comments;
	}

	public void setComments(Set<PostComment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", location=" + location + ", employee=" + employee + ", type=" + type
				+ ", posted=" + posted + ", content=" + content + "]";
	}
}
