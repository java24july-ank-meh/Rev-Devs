package com.revature.application.dao.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HotSpot {
    
    @Id
    @SequenceGenerator(name = "locationSequence", sequenceName = "location_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSequence")
    private Long hotSpotId;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    private Double lattitude;
    
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
    
    @OneToOne
    @JoinColumn(name = "postId")
    private Post post;
    
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public HotSpot() {
        super();
    }
    
    public HotSpot(Double longitude, Double lattitude, Location location) {
        super();
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.location = location;
    }
    
    public Long getHotSpotId() {
        return hotSpotId;
    }
    
    public void setHotSpotId(Long hotSpotId) {
        this.hotSpotId = hotSpotId;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getLattitude() {
        return lattitude;
    }
    
    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "HotSpot [hotSpotId=" + hotSpotId + ", longitude=" + longitude + ", lattitude="
                + lattitude + ", location=" + location + ", post=" + post + "]";
    }
    
}
