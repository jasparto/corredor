/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.corredor.gestor;

import com.oasys.corredor.modelo.Mensaje;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import static org.primefaces.component.contextmenu.ContextMenu.PropertyKeys.event;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorArchivo {

    public Collection<? extends Mensaje> fileToMensajeList(UploadedFile file) throws ParseException {
        Collection<Mensaje> mList = new ArrayList();

        if (file != null && file.getSize() > 0) {
            BufferedReader buffer = null;
            try {
                String linea;
                Integer contador = 1;
                Reader targetReader = new InputStreamReader(file.getInputstream());
                buffer = new BufferedReader(targetReader);
                while ((linea = buffer.readLine()) != null) {
                    if (!linea.equals("NOMBRE,DESTINO,ADJUNTO")) {
                        mList.add(readLineToMensaje(linea));
                    }
                    contador++;
                }

                targetReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null) {
                        buffer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
        return mList;
    }

    public Mensaje readLineToMensaje(String data) throws ParseException {
        Mensaje m = new Mensaje();

        if (data != null) {
            String[] splitData = data.split("\\s*,\\s*");
            if (splitData.length == 3) {
                m.setNombre(splitData[0].trim().replace("\"", ""));
                m.setCorreo(splitData[1].trim().replace("\"", ""));
                m.setAdjunto(splitData[2].trim().replace("\"", ""));
            }
        }
        return m;
    }
}
