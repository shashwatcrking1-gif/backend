package com.civicshield.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="recommendations")
public class Recommendation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="incident_id",nullable=false) private Incident incident;
 @Column(nullable=false,length=1200) private String text;
 @Column(nullable=false,length=24) private String status="OPEN";
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected Recommendation(){}
 public Recommendation(Incident i,String text){this.incident=i;this.text=text;}
 public Long getId(){return id;} public Incident getIncident(){return incident;} public String getText(){return text;} public String getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
}
