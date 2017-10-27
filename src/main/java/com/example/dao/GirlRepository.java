package com.example.dao;

import com.example.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface GirlRepository extends JpaRepository<Girl, Integer>, JpaSpecificationExecutor<Girl> {
}