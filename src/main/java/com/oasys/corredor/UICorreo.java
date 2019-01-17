/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.corredor;

import com.oasys.corredor.gestor.GestorArchivo;
import com.oasys.corredor.gestor.GestorMensaje;
import com.oasys.corredor.modelo.Mensaje;
import com.oasys.util.UtilLog;
import com.oasys.util.UtilMSG;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Julian D Osorio G
 */
@ManagedBean(name = "uiCorreo")
@SessionScoped
public class UICorreo {

    private UploadedFile file;
    private List<Mensaje> mensajeList = new ArrayList<>();
    private String correo;
    private String usuario;
    private String clave;
    private String texto;
    private String asunto;

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            System.out.println(file.getFileName());
        } else {
            UtilMSG.addWarningMsg("Selecciona el archivo de clientes.");
        }
    }

    public void cargarAdjunto(FileUploadEvent event) {
        try {
            GestorArchivo gestorArchivo = new GestorArchivo();
            this.file = event.getFile();
            getMensajeList().addAll(gestorArchivo.fileToMensajeList(file));

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);

        }

    }

    public void enviar() {
        try {
            if (mensajeList == null || mensajeList.isEmpty()) {
                throw new Exception("Carga la lista de clientes", UtilLog.TW_VALIDACION);
            }

            if (texto == null || texto.equalsIgnoreCase("")
                    || asunto == null || asunto.equalsIgnoreCase("")
                    || correo == null || correo.equalsIgnoreCase("")
                    || clave == null || clave.equalsIgnoreCase("")) {
                throw new Exception("Ingresa los datos del correo origen", UtilLog.TW_VALIDACION);
            }

            GestorMensaje gestorMensaje = new GestorMensaje();

            for (Mensaje m : mensajeList) {
                String textoUsuario = texto;
                if (!m.getNotificado()) {
                    textoUsuario = textoUsuario.replace("$USER", m.getNombre());
                    m.setNotificado(gestorMensaje.enviar(m, textoUsuario, asunto, correo, clave));
                }
            }

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
            }
        }
    }

    public void limpiar() {
        this.file = null;
        mensajeList = new ArrayList<>();
    }

    /**
     * @return the mensajeList
     */
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    /**
     * @param mensajeList the mensajeList to set
     */
    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

}
