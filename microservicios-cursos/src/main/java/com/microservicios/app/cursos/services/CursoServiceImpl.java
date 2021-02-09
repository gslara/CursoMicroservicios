package com.microservicios.app.cursos.services;

import org.springframework.stereotype.Service;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.models.repository.CursoRepository;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

}
