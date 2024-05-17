package com.mitocode.business.impl;

import com.mitocode.business.CourseService;
import com.mitocode.model.Course;
import com.mitocode.repository.CourseRepository;
import com.mitocode.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CrudServiceImpl<Course, String> implements CourseService {

  private final CourseRepository courseRepository;

  @Override
  protected GenericRepository<Course, String> getRepo() {
    return courseRepository;
  }

  @Override
  public Flux<Course> getCourses(String[] roles) {
    return courseRepository.getCourses(roles);
  }
}
