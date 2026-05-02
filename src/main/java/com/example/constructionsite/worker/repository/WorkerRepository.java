package com.example.constructionsite.worker.repository;

import com.example.constructionsite.worker.entity.WorkerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer> {
  boolean existsByFirstNameAndLastName(String firstName, String lastName);

  @Query("SELECT w FROM WorkerEntity w WHERE w.isActive = true")
  Page<WorkerEntity> findAllActive(Pageable pageable);
}
