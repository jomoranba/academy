package com.mitocode.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "students")
public class Student {

  @Id
  @EqualsAndHashCode.Include
  private String id;

  @NotEmpty
  @Field
  private String name;

  @NotEmpty
  @Field
  private String lastName;

  @NotEmpty
  @Field
  private String dni;

  @NotNull
  @Field
  private Integer age;
}
