package com.civicshield.repo;
import com.civicshield.domain.Alert; import org.springframework.data.jpa.repository.JpaRepository;
public interface AlertRepository extends JpaRepository<Alert,Long>{}
