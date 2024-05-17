package com.mitocode.business.impl;

import com.mitocode.business.MatriculaService;
import com.mitocode.model.Matricula;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CrudServiceImpl<Matricula, String> implements MatriculaService {

  private final MatriculaRepository matriculaRepository;

  @Override
  protected GenericRepository<Matricula, String> getRepo() {
    return matriculaRepository;
  }

}
