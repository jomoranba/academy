package com.mitocode.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "matriculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Matricula {

  @Id
  @EqualsAndHashCode.Include
  private String id;

  @Field
  @NotNull
  private LocalDateTime dateMatricula;

  @Field
  @NotNull
  private Student student;

  @Field
  @NotNull
  private List<Course> lstCourse;

  @Field
  @NotNull
  private Boolean status;
}
