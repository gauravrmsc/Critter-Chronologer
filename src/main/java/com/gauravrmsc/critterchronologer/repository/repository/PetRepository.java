package com.gauravrmsc.critterchronologer.repository.repository;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import java.security.acl.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {

  List<PetEntity> findAllByOwner(CustomerEntity owner);

}
