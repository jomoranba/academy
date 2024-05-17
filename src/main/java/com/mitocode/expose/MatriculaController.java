package com.mitocode.expose;

import com.mitocode.business.MatriculaService;
import com.mitocode.dto.MatriculaDTO;
import com.mitocode.model.Matricula;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

  private final MatriculaService matriculaService;
  @Qualifier("matriculaMapper")
  private final ModelMapper modelMapper;


  @GetMapping("/findById")
  public Mono<ResponseEntity<Matricula>> getFindById(@RequestParam String id) {
    return matriculaService.findById(id)
      .map(course -> ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(course)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Mono<ResponseEntity<Flux<MatriculaDTO>>> findAll() {

    Flux<MatriculaDTO> fxCourse = matriculaService.findAll().map(this::convertToDto);
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fxCourse)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<MatriculaDTO>> save(@Valid @RequestBody MatriculaDTO dto, final ServerHttpRequest req) {
    return matriculaService.save(convertToDocument(dto))
      .map(this::convertToDto)
      .map(response -> ResponseEntity.created(
            //localhost:8080/invoicees/1293123
            URI.create(req.getURI().toString().concat("/").concat(response.getId()))
          )
          .contentType(MediaType.APPLICATION_JSON)
          .body(response)
      )
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  private MatriculaDTO convertToDto(Matricula model){
    return modelMapper.map(model, MatriculaDTO.class);
  }

  private Matricula convertToDocument(MatriculaDTO dto){
    return modelMapper.map(dto, Matricula.class);
  }
}
