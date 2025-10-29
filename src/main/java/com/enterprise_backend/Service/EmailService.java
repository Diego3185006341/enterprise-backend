package com.enterprise_backend.Service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import jakarta.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Service;


import java.util.Properties;


@Service
public class EmailService {

    private final String remitente = "yeogalindo@gmail.com";
    private final String clave = "sduk atac ernk kxyf";
    private final InventarioService inventarioService;

    public EmailService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public void enviarPDF(String destinatario) throws Exception {
        byte[] pdfBytes = inventarioService.generarPDFOpen(); // ✅ PDF en memoria

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensaje.setSubject("Inventario PDF");

        MimeBodyPart cuerpo = new MimeBodyPart();
        cuerpo.setText("Adjunto el inventario en PDF.");

        MimeBodyPart adjunto = new MimeBodyPart();
        DataSource fuente = new ByteArrayDataSource(pdfBytes, "application/pdf");
        adjunto.setDataHandler(new DataHandler(fuente));
        adjunto.setFileName("inventario.pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(cuerpo);
        multipart.addBodyPart(adjunto);

        mensaje.setContent(multipart);

        Transport.send(mensaje);
        System.out.println("Correo enviado con PDF.");
    }
}


