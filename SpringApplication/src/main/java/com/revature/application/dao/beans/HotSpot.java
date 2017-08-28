package com.revature.application.dao.beans;

import javax.persistence.*;

@Entity
public class HotSpot {
    
    @Id
    @SequenceGenerator(name = "locationSequence", sequenceName = "location_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSequence")
    private long hotSpotId;
    
    @Column(nullable = false)
    private double longitude;
    
    @Column(nullable = false)
    private double lattitude;
    
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
    
    public HotSpot() {
        super();
    }
    
    public HotSpot(double longitude, double lattitude, Location location) {
        super();
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.location = location;
    }
    
    public long getHotSpotId() {
        return hotSpotId;
    }
    
    public void setHotSpotId(long hotSpotId) {
        this.hotSpotId = hotSpotId;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLattitude() {
        return lattitude;
    }
    
    public void setLattitude(double lattitude) {
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
                + lattitude + ", location=" + location + "]";
    }
    
}
