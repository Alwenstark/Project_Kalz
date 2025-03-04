package com.kalz.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "truck_details")
public class TruckDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truck_id") // Explicitly defining column name
    private Long truckId;

    @Column(name = "truck_no", nullable = false, unique = true) // Truck number should be unique
    private String truckNo;

    @Column(name = "truck_owner", nullable = false)
    private String truckOwner;

    // Getters and Setters
    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getTruckOwner() {
        return truckOwner;
    }

    public void setTruckOwner(String truckOwner) {
        this.truckOwner = truckOwner;
    }
}