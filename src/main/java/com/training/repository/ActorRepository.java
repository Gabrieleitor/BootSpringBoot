package com.training.repository;

import com.training.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The actor repository
 */
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
