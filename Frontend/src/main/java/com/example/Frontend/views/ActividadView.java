package com.example.Frontend.views;

import com.example.Frontend.model.Actividad;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("actividades")
public class ActividadView extends VerticalLayout {
    private final RestTemplate restTemplate;
    private final Grid<Actividad> grid = new Grid<>(Actividad.class);

    public ActividadView(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
        grid.setColumns("id", "idNota", "fechaAccion", "tipoAccion");
        Button obtenerActividadesButton = new Button("Obtener Actividades", event -> actualizarGrid());
        add(obtenerActividadesButton,grid);
    }

    private void actualizarGrid() {
        Actividad[] actividades = restTemplate.getForObject("http://localhost:8082/actividades", Actividad[].class);
        if (actividades != null && actividades.length > 0) {
            grid.setItems(actividades);
        } else {
            Notification.show("No hay actividades disponibles");
        }
    }
}
