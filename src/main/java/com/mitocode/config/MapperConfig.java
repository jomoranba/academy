package com.mitocode.config;

import com.mitocode.dto.MatriculaDTO;
import com.mitocode.model.Matricula;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @Bean("matriculaMapper")
    public ModelMapper matriculaMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Escritura
        TypeMap<MatriculaDTO, Matricula> typeMap1 = mapper.createTypeMap(MatriculaDTO.class, Matricula.class);
        typeMap1.addMapping(e -> e.getStudent().getId(), (dest, v) -> dest.getStudent().setId((String) v));
        typeMap1.addMapping(e -> e.getStudent().getName(), (dest, v) -> dest.getStudent().setName((String) v));
        typeMap1.addMapping(e -> e.getStudent().getLastName(), (dest, v) -> dest.getStudent().setLastName((String) v));
        typeMap1.addMapping(e -> e.getStudent().getDni(), (dest, v) -> dest.getStudent().setDni((String) v));
        typeMap1.addMapping(e -> e.getStudent().getAge(), (dest, v) -> dest.getStudent().setAge((Integer) v));


        //Lectura
        TypeMap<Matricula, MatriculaDTO> typeMap2 = mapper.createTypeMap(Matricula.class, MatriculaDTO.class);
        typeMap2.addMapping(e -> e.getStudent().getId(), (dest, v) -> dest.getStudent().setId((String) v));
        typeMap2.addMapping(e -> e.getStudent().getName(), (dest, v) -> dest.getStudent().setName((String) v));
        typeMap2.addMapping(e -> e.getStudent().getLastName(), (dest, v) -> dest.getStudent().setLastName((String) v));
        typeMap2.addMapping(e -> e.getStudent().getDni(), (dest, v) -> dest.getStudent().setDni((String) v));
        typeMap2.addMapping(e -> e.getStudent().getAge(), (dest, v) -> dest.getStudent().setAge((Integer) v));

        return mapper;
    }
}
