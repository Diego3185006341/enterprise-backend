package com.enterprise_backend.Repository;

import com.enterprise_backend.Entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
