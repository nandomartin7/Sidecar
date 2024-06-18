package com.example.Frontend.views;

import com.example.Frontend.model.Nota;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("notas")
public class NotaView extends VerticalLayout {
    private final RestTemplate restTemplate;
    private final Grid<Nota> grid = new Grid<>(Nota.class);
    private final TextField tituloFieldCrear = new TextField("Título");
    private final TextField detalleFieldCrear = new TextField("Detalle");
    private final TextField idFieldActualizar = new TextField("Id");
    private final TextField tituloFieldActualizar = new TextField("Título");
    private final TextField detalleFieldActualizar = new TextField("Detalle");
    private final TextField idFieldBorrar = new TextField("Id");
    private final TextField idFieldBuscar = new TextField("Id");

    public NotaView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        grid.setColumns("id", "titulo", "detalle");

        Button addButton = new Button("Agregar", event -> agregarNota());
        Button updateButton = new Button("Actualizar", event -> actualizarNota());
        Button deleteButton = new Button("Eliminar", event -> eliminarNota());
        Button findByIdButton = new Button("Buscar por ID", event -> buscarPorId());
        Button findAllButton = new Button("Buscar Todos", event -> buscarTodos());


        VerticalLayout agregarLayout = new VerticalLayout(tituloFieldCrear,detalleFieldCrear, addButton);
        VerticalLayout actualizarLayout = new VerticalLayout(idFieldActualizar,tituloFieldActualizar,detalleFieldActualizar,updateButton);
        VerticalLayout borrarLayout = new VerticalLayout(idFieldBorrar,deleteButton);
        VerticalLayout buscarPorIdLayout = new VerticalLayout(idFieldBuscar,findByIdButton);

        HorizontalLayout Barra = new HorizontalLayout(agregarLayout,actualizarLayout,borrarLayout,buscarPorIdLayout,findAllButton);

        add(Barra, grid);
    }

    private void agregarNota() {
        Nota nota = new Nota();
        nota.setTitulo(tituloFieldCrear.getValue());
        nota.setDetalle(detalleFieldCrear.getValue());

        restTemplate.postForObject("http://localhost:8081/notas", nota, Nota.class);
        Notification.show("Nota agregada");
        limpiarCampos();
        actualizarGrid();
    }

    private void actualizarNota() {
        Nota nota = new Nota();
        nota.setId(Long.parseLong(idFieldActualizar.getValue()));
        nota.setTitulo(tituloFieldActualizar.getValue());
        nota.setDetalle(detalleFieldActualizar.getValue());

        restTemplate.put("http://localhost:8081/notas/" + nota.getId(), nota);
        Notification.show("Nota actualizada");
        limpiarCampos();
        actualizarGrid();
    }

    private void eliminarNota() {
        try {
            String id = idFieldBorrar.getValue();
            Nota nota = restTemplate.getForObject("http://localhost:8081/notas/"+id,Nota.class);
            if (nota != null) {
                restTemplate.delete("http://localhost:8081/notas/" + id);
                grid.setItems(nota);
            } else {
                Notification.show("Nota no encontrada");
            }
        }catch (NumberFormatException e){
            Notification.show("Ingrese un ID válido");
        }
        Notification.show("Nota eliminada");
        limpiarCampos();
        actualizarGrid();
    }

    private void buscarPorId() {
        try {
            Long id = Long.parseLong(idFieldBuscar.getValue());
            Nota nota = restTemplate.getForObject("http://localhost:8081/notas/" + id, Nota.class);
            if (nota != null) {
                grid.setItems(nota);
            } else {
                Notification.show("Nota no encontrada");
            }
        } catch (NumberFormatException e) {
            Notification.show("Ingrese un ID válido");
        }
    }

    private void buscarTodos() {
        actualizarGrid();
    }

    private void limpiarCampos() {
        tituloFieldCrear.clear();
        detalleFieldCrear.clear();
        idFieldActualizar.clear();
        tituloFieldActualizar.clear();
        detalleFieldActualizar.clear();
        idFieldBorrar.clear();
        idFieldBuscar.clear();
    }

    private void actualizarGrid() {
        Nota[] notas = restTemplate.getForObject("http://localhost:8081/notas", Nota[].class);
        grid.setItems(notas);
    }
}
