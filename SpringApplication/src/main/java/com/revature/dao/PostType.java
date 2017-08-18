package com.revature.dao;

import javax.persistence.*;

@Entity
public class PostType {

	@Id
	private long type_id;
	
	private String type;
	
	public PostType() {
		// TODO Auto-generated constructor stub
	}

	public PostType(long type_id, String type) {
		super();
		this.type_id = type_id;
		this.type = type;
	}

	@Override
	public String toString() {
		return "PostType [type_id=" + type_id + ", type=" + type + "]";
	}

	public long getType_id() {
		return type_id;
	}

	public void setType_id(long type_id) {
		this.type_id = type_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
