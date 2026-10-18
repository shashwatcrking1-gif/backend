package com.civicshield.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="incidents")
public class Incident {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=180) private String title;
 @Column(nullable=false,length=40) private String type;
 @Column(nullable=false,length=200) private String location;
 @Column(length=2000) private String description;
 @Column(nullable=false) private double seedRisk;
 @Column(nullable=false,length=24) private String status="OPEN";
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="created_by",nullable=false) private AppUser createdBy;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 @Column(name="updated_at",nullable=false) private Instant updatedAt=Instant.now();
 protected Incident(){}
 public Incident(String title,String type,String location,String description,double seedRisk,AppUser user){this.title=title;this.type=type;this.location=location;this.description=description;this.seedRisk=seedRisk;this.createdBy=user;}
 public void update(String title,String type,String location,String description,double seedRisk){this.title=title;this.type=type;this.location=location;this.description=description;this.seedRisk=seedRisk;this.updatedAt=Instant.now();}
 public void setStatus(String status){this.status=status;this.updatedAt=Instant.now();}
 public Long getId(){return id;} public String getTitle(){return title;} public String getType(){return type;} public String getLocation(){return location;} public String getDescription(){return description;} public double getSeedRisk(){return seedRisk;} public String getStatus(){return status;} public AppUser getCreatedBy(){return createdBy;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
