package com.mitocode.business;

import com.mitocode.model.Student;
import reactor.core.publisher.Flux;

public interface StudentService extends CrudService<Student, String> {

  Flux<Student> order(String type);
}
