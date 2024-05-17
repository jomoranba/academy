package com.mitocode.repository;

import com.mitocode.model.Course;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface CourseRepository extends GenericRepository<Course, String> {

  @Query("{'roles' :  { $in: ?0 }}")
  Flux<Course> getCourses(String[] roles);
}
