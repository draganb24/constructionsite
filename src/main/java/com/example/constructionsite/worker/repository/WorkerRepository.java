package com.example.constructionsite.worker.repository;

import com.example.constructionsite.worker.entity.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
  boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
