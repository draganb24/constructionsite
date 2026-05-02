package com.example.constructionsite.machine.repository;

import com.example.constructionsite.machine.entity.MachineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, Integer> {
  boolean existsByName(String name);

  @Query("SELECT m FROM MachineEntity m WHERE m.isActive = true")
  Page<MachineEntity> findAllActive(Pageable pageable);
}
