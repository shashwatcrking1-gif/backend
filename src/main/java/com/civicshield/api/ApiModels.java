package com.civicshield.api;
import jakarta.validation.constraints.*; import java.time.Instant; import java.util.Map;
public final class ApiModels {private ApiModels(){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 public record LoginResponse(String accessToken,String tokenType,long expiresInSeconds,UserView user){}
 public record UserView(Long id,String email,String name,String role,boolean enabled,Instant createdAt){}
 public record IncidentRequest(@NotBlank @Size(max=180) String title,@NotBlank @Size(max=40) String type,@NotBlank @Size(max=200) String location,@Size(max=2000) String description,@DecimalMin("0") @DecimalMax("100") double seedRisk){}
 public record StatusRequest(@NotBlank @Pattern(regexp="OPEN|IN_PROGRESS|RESOLVED|CLOSED") String status){}
 public record AlertStatusRequest(@NotBlank @Pattern(regexp="ACTIVE|ACKNOWLEDGED|RESOLVED") String status){}
 public record IncidentView(Long id,String title,String type,String location,String description,double seedRisk,String status,Long createdBy,Instant createdAt,Instant updatedAt){}
 public record RiskView(Long id,Long incidentId,double score,String severity,Map<String,Double> nodes,Map<String,Double> localFactors,Instant analyzedAt){}
 public record SimulationRequest(@NotNull Long incidentId,@DecimalMin("0") @DecimalMax("100") Double seed,Map<String,@DecimalMin("0") @DecimalMax("100") Double> localFactors){}
 public record SimulationView(Long id,Long incidentId,double score,String severity,Map<String,Double> nodes,Map<String,Double> localFactors,Instant createdAt){}
 public record AlertView(Long id,Long incidentId,String severity,String message,String status,Instant createdAt){}
 public record RecommendationView(Long id,Long incidentId,String text,String status,Instant createdAt){}
 public record Summary(long totalIncidents,long openIncidents,long activeAlerts,long simulations){}
 public record ErrorResponse(Instant timestamp,int status,String error,String message,String path){}
}
