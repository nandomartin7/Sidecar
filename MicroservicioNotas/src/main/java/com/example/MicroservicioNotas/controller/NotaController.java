package com.example.MicroservicioNotas.controller;

import com.example.MicroservicioNotas.model.Nota;
import com.example.MicroservicioNotas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {
    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String Sidecar_URL = "http://localhost:8082/actividades";
    @GetMapping
    public List<Nota> getAllNotas(){
        return notaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Nota getNotaById(@PathVariable Long id){
        Nota savedNota = notaRepository.findById(id).orElse(null);
        enviarActividad("Consulta", savedNota.getId());
        return savedNota;
    }

    @PostMapping
    public Nota createNota(@RequestBody Nota nota){
        nota.setFechaPosteo(LocalDateTime.now());
        nota.setFechaModificacion(LocalDateTime.now());
        Nota savedNota = notaRepository.save(nota);
        enviarActividad("Creacion", savedNota.getId());
        return savedNota;
    }

    @PutMapping("/{id}")
    public Nota updateNota(@PathVariable Long id, @RequestBody Nota nota){
        Nota existeNota = notaRepository.findById(id).orElse(null);
        if (existeNota != null){
            existeNota.setTitulo(nota.getTitulo());
            existeNota.setDetalle(nota.getDetalle());
            existeNota.setFechaModificacion(LocalDateTime.now());
            Nota updateNota = notaRepository.save(existeNota);
            enviarActividad("Edicion", updateNota.getId());
            return updateNota;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteNota(@PathVariable Long id){
        notaRepository.deleteById(id);
        enviarActividad("Eliminacion",id);
    }

    private void enviarActividad(String tipoAccion, Long idNota){
        HttpHeaders headers = new HttpHeaders();
        Actividad actividad = new Actividad();
        actividad.setIdNota(idNota);
        actividad.setTipoAccion(tipoAccion);
        HttpEntity<Actividad> requestEntity = new HttpEntity<>(actividad,headers);
        restTemplate.exchange(Sidecar_URL,HttpMethod.POST,requestEntity, Void.class);
    }
}
