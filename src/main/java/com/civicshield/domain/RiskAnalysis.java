package com.civicshield.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="risk_analyses")
public class RiskAnalysis {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="incident_id",nullable=false) private Incident incident;
 @Column(nullable=false) private double score;
 @Column(nullable=false,length=20) private String severity;
 @Column(nullable=false,columnDefinition="text") private String nodeProbabilities;
 @Column(nullable=false,columnDefinition="text") private String localFactors;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="analyzed_by",nullable=false) private AppUser analyzedBy;
 @Column(name="analyzed_at",nullable=false) private Instant analyzedAt=Instant.now();
 protected RiskAnalysis(){}
 public RiskAnalysis(Incident incident,double score,String severity,String nodes,String factors,AppUser user){this.incident=incident;this.score=score;this.severity=severity;this.nodeProbabilities=nodes;this.localFactors=factors;this.analyzedBy=user;}
 public Long getId(){return id;} public Incident getIncident(){return incident;} public double getScore(){return score;} public String getSeverity(){return severity;} public String getNodeProbabilities(){return nodeProbabilities;} public String getLocalFactors(){return localFactors;} public Instant getAnalyzedAt(){return analyzedAt;}
}
