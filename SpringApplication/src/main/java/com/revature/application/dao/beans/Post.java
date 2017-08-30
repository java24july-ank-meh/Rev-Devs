package com.revature.application.dao.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
    
    @Id
    @SequenceGenerator(name = "postSequence", sequenceName = "post_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSequence")
    private Long postId;
    
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
    
    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "typeId")
    private PostType type;
    

    @ManyToOne
    @JoinColumn(name = "hotSpotId")

    @OneToOne(cascade = CascadeType.ALL)

    private HotSpot hotSpot;
    
    @Column
    private Date posted;
    
    @Column
    private String content;
    
    @OneToMany(fetch=FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Set<PostComment> comments = new HashSet<>();
    
    public Post() {
    }
    
    public Post(Location location, Employee employee, PostType type, Date posted, String content, HotSpot hotSpot) {
        super();
        this.location = location;
        this.employee = employee;
        this.type = type;
        this.posted = posted;
        this.content = content;
        this.hotSpot = hotSpot;
    }
    
    public HotSpot getHotSpot() {
        return hotSpot;
    }

    public void setHotSpot(HotSpot hotSpot) {
        this.hotSpot = hotSpot;
    }

    public Long getPostId() {
        return postId;
    }
    
    public void setPostId(Long postId) {
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
        return "Post [postId=" + postId + ", location=" + location + ", employee=" + employee
                + ", type=" + type + ", posted=" + posted + ", content=" + content + "]";
    }
}
