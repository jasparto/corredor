/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author juliano
 */
public class UtilMSG {

    public static void addErrorMsg(final String mensaje) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addWarningMsg(final String mensaje) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMsg(final String mensaje) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addWarningMsg(final String titulo, final String detalle) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, detalle);
        context.showMessageInDialog(message);
    }

    public static void addWarningMsg(Exception e) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getCause().getMessage(), e.getMessage());
        context.showMessageInDialog(message);
    }

    public static void addErrorMsg(final String titulo, final String detalle) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalle);
        context.showMessageInDialog(message);
    }

    public static void addSuccessMsg(final String titulo, final String detalle) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle);
        context.showMessageInDialog(message);
    }

    public static void addSupportMsg() {
        UtilMSG.addErrorMsg("Error no controlado, intente nuevamente", "Si el problema persiste contactenos para asistirle.");
    }

    public static String getSupportMsg() {
        return "Error no controlado intente nuevamente. Si el problema persiste contactenos para asistirle.";
    }

    public static void addSuccesMsg() {
        UtilMSG.addSuccessMsg("Registro satisfactorio");
    }

}
