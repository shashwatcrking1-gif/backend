package com.civicshield.repo;
import com.civicshield.domain.Recommendation; import org.springframework.data.jpa.repository.JpaRepository;
public interface RecommendationRepository extends JpaRepository<Recommendation,Long>{}
