/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.puntosfidelidad.dtos;

import co.edu.uniandes.csw.puntosfidelidad.entities.EventoEntity;
import java.util.Calendar;

/**
 *
 * @author cass_
 */
public class EventoDTO {

    private String nombre;
    private String descripcion;
    private Calendar fechaInicio;
    private Calendar fechaFin; 
    
    
    public EventoDTO(){
        
    }
    public EventoDTO(EventoEntity entity) {
        this.descripcion = entity.getDescripcion();
        this.fechaFin = entity.getFechaFin();
        this.fechaInicio = entity.getFechaInicio();
        this.nombre = entity.getNombre();
    }
    
    public EventoEntity toEntity(){
        EventoEntity entity = new EventoEntity();
        entity.setDescripcion(this.getDescripcion());
        entity.setNombre(this.getNombre());
        entity.setFechaFin(this.getFechaFin());
        entity.setFechaInicio(this.getFechaInicio());
        return entity;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fechaInicio
     */
    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Calendar getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
}
