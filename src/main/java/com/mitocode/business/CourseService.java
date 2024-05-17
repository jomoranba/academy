package com.mitocode.business;

import com.mitocode.model.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService extends CrudService<Course, String> {

  Flux<Course> getCourses(String[] roles);

}
