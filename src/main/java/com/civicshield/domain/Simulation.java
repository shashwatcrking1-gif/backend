package com.civicshield.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="simulations")
public class Simulation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="incident_id",nullable=false) private Incident incident;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="created_by",nullable=false) private AppUser createdBy;
 @Column(nullable=false,columnDefinition="text") private String inputs;
 @Column(nullable=false) private double score;
 @Column(nullable=false,length=20) private String severity;
 @Column(nullable=false,columnDefinition="text") private String nodeProbabilities;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected Simulation(){}
 public Simulation(Incident i,AppUser u,String inputs,double score,String severity,String nodes){this.incident=i;this.createdBy=u;this.inputs=inputs;this.score=score;this.severity=severity;this.nodeProbabilities=nodes;}
 public Long getId(){return id;} public Incident getIncident(){return incident;} public String getInputs(){return inputs;} public double getScore(){return score;} public String getSeverity(){return severity;} public String getNodeProbabilities(){return nodeProbabilities;} public Instant getCreatedAt(){return createdAt;}
}
