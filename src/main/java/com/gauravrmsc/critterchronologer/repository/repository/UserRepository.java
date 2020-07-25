package com.gauravrmsc.critterchronologer.repository.repository;

import com.gauravrmsc.critterchronologer.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
