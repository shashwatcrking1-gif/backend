package com.civicshield.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, unique=true, length=180) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false, length=120) private String name;
 @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role;
 @Column(nullable=false) private boolean enabled=true;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected AppUser() {}
 public AppUser(String email,String passwordHash,String name,Role role){this.email=email.toLowerCase();this.passwordHash=passwordHash;this.name=name;this.role=role;}
 public Long getId(){return id;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public String getName(){return name;} public Role getRole(){return role;} public boolean isEnabled(){return enabled;} public Instant getCreatedAt(){return createdAt;}
}
