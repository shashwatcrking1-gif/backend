package com.civicshield.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="audit_logs")
public class AuditLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="actor_id",nullable=false) private AppUser actor;
 @Column(nullable=false,length=80) private String action;
 @Column(nullable=false,length=80) private String entityType;
 @Column(length=80) private String entityId;
 @Column(nullable=false,columnDefinition="text") private String details;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected AuditLog(){}
 public AuditLog(AppUser actor,String action,String type,String id,String details){this.actor=actor;this.action=action;this.entityType=type;this.entityId=id;this.details=details;}
}
