package com.microservicios.app.usuarios.services;

import org.springframework.stereotype.Service;

import com.microservicios.app.usuarios.models.repository.AlumnaRepository;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnaServiceImpl extends CommonServiceImpl<Alumna, AlumnaRepository> implements AlumnaService {

}


