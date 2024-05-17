package com.mitocode.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
@Document(collection = "courses")
public class Course {

  @Id
  @EqualsAndHashCode.Include
  private String id;

  @Field
  @NotNull
  @NotEmpty
  private String name;

  @Field
  @NotNull
  @NotEmpty
  private String acronym;

  @Field
  @NotNull
  private Boolean status;

  @Field
  @NotNull
  private List<String> roles;
}
