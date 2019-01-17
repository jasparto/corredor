/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.corredor.gestor;

import com.oasys.corredor.modelo.Mensaje;
import com.oasys.util.UtilMSG;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.activation.*;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorMensaje {

    public Boolean enviar(Mensaje m, String mensaje, String asunto, String correo, String clave) {

        //tls
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
                return new PasswordAuthentication(correo, clave);
            }
        });

        try {

            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("oasys.scon@gmail.com"));
            message.setFrom(new InternetAddress(correo));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(m.getCorreo()));
            message.setSubject(asunto);
//            message.setContent(mensaje, "text/html; charset=utf-8");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setContent(mensaje, "text/html; charset=utf-8");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String filename = "c:\\adjunto\\" + m.getAdjunto();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(m.getAdjunto());
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);

//            message.setText(
//                     "Cordial Saludo\n\n"
//                    + "Contacto.\n\n"
//                    + "[Contacto] JULIAN DAVID OSORIO GONZALEZ\n"
//                    + "Cargo: INGENIERO DESARROLLO\n"
//                    + "Cédula: 1093215489\n"
//                    + "\n\n Cordialmente \n Julian David Osorio Gonzalez"
//            );
            Transport.send(message);
            System.out.println("Done");
            return Boolean.TRUE;
        } catch (MessagingException e) {

            UtilMSG.addErrorMsg("Error al enviar el correo " + m.getNombre() + " - " + e.getMessage());
            return Boolean.FALSE;
        }
    }

}
