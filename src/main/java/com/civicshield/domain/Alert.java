package com.civicshield.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="alerts")
public class Alert {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="incident_id",nullable=false) private Incident incident;
 @Column(nullable=false,length=24) private String severity;
 @Column(nullable=false,length=500) private String message;
 @Column(nullable=false,length=24) private String status="ACTIVE";
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected Alert(){}
 public Alert(Incident i,String severity,String message){this.incident=i;this.severity=severity;this.message=message;}
 public void setStatus(String status){this.status=status;}
 public Long getId(){return id;} public Incident getIncident(){return incident;} public String getSeverity(){return severity;} public String getMessage(){return message;} public String getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
}
