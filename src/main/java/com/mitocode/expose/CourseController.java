package com.mitocode.expose;

import com.mitocode.business.CourseService;
import com.mitocode.model.Course;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @GetMapping("/findById")
  public Mono<ResponseEntity<Course>> getFindById(@RequestParam String id) {
    return courseService.findById(id)
      .map(course -> ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(course)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  @GetMapping
  public Mono<ResponseEntity<Flux<Course>>> findAll() {
    return ReactiveSecurityContextHolder.getContext()
      .map(SecurityContext::getAuthentication)
      .map(Authentication::getAuthorities)
      .map(roles -> {
        String rolesString = roles.stream().map(Objects::toString).collect(Collectors.joining(","));
        String[] stringArray = rolesString.split(",");
        return courseService.getCourses(stringArray);
      })
      .flatMap(fx -> Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fx)
      ))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Course>> save(@Valid @RequestBody Course course, final ServerHttpRequest req) {
    return courseService.save(course)
      .map(e -> ResponseEntity.created(
            URI.create(req.getURI().toString().concat("/").concat(e.getId()))
          )
          .contentType(MediaType.APPLICATION_JSON)
          .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Course>> update(@PathVariable("id") String id, @RequestBody Course course) {
    return Mono.just(course)
      .map(e -> {
        e.setId(id);
        return e;
      })
      .flatMap(e -> courseService.update(id, e))
      .map(e -> ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Boolean>> delete(@PathVariable("id") String id) {
    return courseService.deleteById(id)
      .map(e -> ResponseEntity
        .ok()
        .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
