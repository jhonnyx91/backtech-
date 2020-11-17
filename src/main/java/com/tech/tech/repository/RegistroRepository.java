package com.tech.tech.repository;

import com.tech.tech.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroRepository extends JpaRepository <Registro, Long>{


}
