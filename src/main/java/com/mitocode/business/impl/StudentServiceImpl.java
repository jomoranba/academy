package com.mitocode.business.impl;

import com.mitocode.business.StudentService;
import com.mitocode.model.Student;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CrudServiceImpl<Student, String> implements StudentService {

  private final StudentRepository studentRepository;

  @Override
  protected GenericRepository<Student, String> getRepo() {
    return studentRepository;
  }

  @Override
  public Flux<Student> order(String type) {
    Sort sort;
    if (type != null && !type.isEmpty()) {
      switch (type) {
        case "ASC" -> sort = Sort.by(Sort.Direction.ASC, "age");
        case "DESC" -> sort = Sort.by(Sort.Direction.DESC, "age");
        default ->  throw new IllegalArgumentException("El valor ingresado del campo type es incorrecto");
      }
    } else {
      throw new IllegalArgumentException("El campo type es vacio o nulo");
    }
    return getRepo().findAll(sort);
  }
}
