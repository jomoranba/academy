package com.mitocode.expose;

import com.mitocode.business.StudentService;
import com.mitocode.model.Student;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/findById")
  public Mono<ResponseEntity<Student>> getFindById(@RequestParam String id) {
    return studentService.findById(id)
      .map(student -> ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(student)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/order")
  public Mono<ResponseEntity<Flux<Student>>> gerOrder(@RequestParam String type) {
    Flux<Student> fxStudent = studentService.order(type);

    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fxStudent)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Mono<ResponseEntity<Flux<Student>>> findAll() {
    Flux<Student> fxStudent = studentService.findAll();

    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fxStudent)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Student>> saveStudent(@Valid @RequestBody Student student, final ServerHttpRequest req) {
    return studentService.save(student)
      .map(e -> ResponseEntity.created(
            //localhost:8080/clientes/1293123
            URI.create(req.getURI().toString().concat("/").concat(e.getId()))
          )
          .contentType(MediaType.APPLICATION_JSON)
          .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Student>> update(@PathVariable("id") String id, @RequestBody Student student) {
    return Mono.just(student)
      .map(e -> {
        e.setId(id);
        return e;
      })
      .flatMap(e -> studentService.update(id, e))
      .map(e -> ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Boolean>> delete(@PathVariable("id") String id) {
    return studentService.deleteById(id)
      .map(e -> ResponseEntity
        .ok()
        .body(e)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
