package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class PostType {
    
    @Id
    @SequenceGenerator(name = "postTypeSequence", sequenceName = "posttype_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postTypeSequence")
    private Long typeId;
    
    @Column
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
    
    public Long getTypeId() {
        return typeId;
    }
    
    public void setTypeId(Long typeId) {
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
