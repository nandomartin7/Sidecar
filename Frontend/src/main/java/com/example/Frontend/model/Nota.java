package com.example.Frontend.model;
import java.time.LocalDateTime;
public class Nota {
    private Long id;
    private String titulo;
    private String detalle;
    private LocalDateTime fechaPosteo;
    private LocalDateTime fechaModificacion;

    public Nota() {

    }
    //Metodos Getter y Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDateTime getFechaPosteo() {
        return fechaPosteo;
    }

    public void setFechaPosteo(LocalDateTime fechaPosteo) {
        this.fechaPosteo = fechaPosteo;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
