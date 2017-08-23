package com.revature.application.dao.beans;

import java.util.Set;

import javax.persistence.*;

@Entity
public class PostType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long typeId;

	@Column
	private String type;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "typeId")
	private Set<Post> posts;

	public PostType() {
	}

	public PostType(String type) {
		super();
		this.type = type;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "PostType [typeId=" + typeId + ", type=" + type + "]";
	}

}
