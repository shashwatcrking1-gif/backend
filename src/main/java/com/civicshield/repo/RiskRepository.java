package com.civicshield.repo;
import com.civicshield.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface RiskRepository extends JpaRepository<RiskAnalysis,Long>{Optional<RiskAnalysis> findTopByIncidentOrderByAnalyzedAtDesc(Incident incident);}
