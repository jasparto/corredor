/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.corredor.modelo;

/**
 *
 * @author Julian D Osorio G
 */
public class Mensaje {

    private String nombre;
    private String correo;
    private String adjunto;
    private Boolean notificado = Boolean.FALSE;

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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the adjunto
     */
    public String getAdjunto() {
        return adjunto;
    }

    /**
     * @param adjunto the adjunto to set
     */
    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    /**
     * @return the notificado
     */
    public Boolean getNotificado() {
        return notificado;
    }

    /**
     * @param notificado the notificado to set
     */
    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

}
