/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oasys.corredor.gestor;

import com.oasys.corredor.modelo.Mensaje;
import com.oasys.util.UtilMSG;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
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

    public Boolean enviar(Mensaje m, String mensaje, String asunto, String correo, String clave, String host) {

        //tls
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.host", "smtp.office365.com");

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

            String correos = "";
            String[] d = m.getCorreo().split(":");
            for (String s : d) {
                correos += s + ",";
            }

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correos));
            message.setSubject(asunto);
//            message.setContent(mensaje, "text/html; charset=utf-8");

            String[] a = m.getAdjunto().split(":");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Create the message part
            // Now set the actual message
//            messageBodyPart.setContent(mensaje, "text/html; charset=utf-8");
//            MimeBodyPart textPart = new MimeBodyPart();
//            textPart.setText(mensaje, "utf-8", "text/html; charset=utf-8");
//
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(mensaje, "text/html; charset=utf-8");

//            multipart.addBodyPart(textPart); // <-- first
            multipart.addBodyPart(htmlPart); // <-- second

            // Set text message part
//            multipart.addBodyPart(messageBodyPart);
            BodyPart messageBodyPart = null;
            for (String s : a) {
                messageBodyPart = new MimeBodyPart();
//                String filename = "c:\\adjunto\\" + m.getAdjunto();
                String filename = "c:\\adjunto\\" + s;
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
//                messageBodyPart.setFileName(m.getAdjunto());
                messageBodyPart.setFileName(s);
                multipart.addBodyPart(messageBodyPart);
                // Send the complete message parts
            }

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Done");
            return Boolean.TRUE;
        } catch (MessagingException e) {

            UtilMSG.addErrorMsg("Error al enviar el correo " + m.getNombre() + " - " + e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
