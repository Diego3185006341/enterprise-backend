package com.enterprise_backend.Repository;

import com.enterprise_backend.Entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {}
