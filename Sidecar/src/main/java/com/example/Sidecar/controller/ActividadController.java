package com.example.Sidecar.controller;

import com.example.Sidecar.model.Actividad;
import com.example.Sidecar.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/actividades")
public class ActividadController {
    @Autowired
    private ActividadRepository actividadRepository;

    @GetMapping
    public List<Actividad> getAllActividades(){
        return actividadRepository.findAll();
    }

    @PostMapping
    public void registrarActividad(@RequestBody Actividad actividad){
        actividad.setFechaAccion(LocalDateTime.now());
        actividadRepository.save(actividad);
    }
}
