package com.gauravrmsc.critterchronologer.repository.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserEntity {
  @Id
  @GeneratedValue
  private Long id;
  private String name;

}
