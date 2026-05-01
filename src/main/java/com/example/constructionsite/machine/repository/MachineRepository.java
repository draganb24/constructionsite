package com.example.constructionsite.machine.repository;

import com.example.constructionsite.machine.entity.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
  boolean existsByName(String name);
}
