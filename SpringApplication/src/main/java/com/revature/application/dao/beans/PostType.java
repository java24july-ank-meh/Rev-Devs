package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

@Entity
public class PostType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long typeId;

	@Column
	@NotNull
	private String type;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "typeId")
	private Set<Post> posts = new HashSet<>();

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
