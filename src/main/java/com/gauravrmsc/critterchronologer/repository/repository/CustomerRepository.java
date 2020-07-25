package com.gauravrmsc.critterchronologer.repository.repository;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
