package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

  private String id;
  private LocalDateTime dateMatricula;
  private StudentDTO student;
  private List<CourseDTO> lstCourse;
  private Boolean status;
}
