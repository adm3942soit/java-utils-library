package com.utils.post;

import com.utils.date.Dater;
import ua.edu.file.MyFiler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * Created by PROGRAMMER II on 24.10.2014.
 */
public class SendPost {
    String userName="admin84it@gmail.com";
    String host="gmail.google.com";
    String port="465";
    String auth="true";
    String passWord="administrator";

public boolean send(boolean debug, String subject, String to, String[] cc, String[] bcc, String text){
    Properties props = new Properties();
    props.put("mail.smtp.user", userName);
    props.put("mail.smtp.host", host);
    if(!"".equals(port))
            props.put("mail.smtp.port", port);

//if(!"".equals(starttls))
//props.put("mail.smtp.starttls.enable",starttls);
    props.put("mail.smtp.auth", auth);

    if(debug)
    {
        props.put("mail.smtp.debug", "true");
    }
    else
    {
        props.put("mail.smtp.debug", "false");
    }
    if(!"".equals(port))
            props.put("mail.smtp.socketFactory.port", port);
    //if(!"".equals(socketFactoryClass))
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    //if(!"".equals(fallback))
//props.put("mail.smtp.socketFactory.fallback", fallback);

    try

    {
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        MimeMessage msg = new MimeMessage(session);
//msg.setText(text);
        msg.setSubject(subject);
        msg.setFrom(new InternetAddress("johnny@gmail.com"));


        //for (int i = 0; i < to.length(); i++) {
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress("asyaDudnik@hotmail.com"));
       // }

        for (int i = 0;cc!=null &&  i < cc.length; i++) {
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
        }
        for (int i = 0; bcc!=null && i < bcc.length; i++) {
            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
        }

///


// create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(text);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(MyFiler.getCurrentDirectory()+ File.separator+"logPaySystem_"+ Dater.getDateForLog1()+".txt");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("logPaySystem_"+ Dater.getDateForLog1()+".txt");
        messageBodyPart.setFileName(source.getName());
//messageBodyPart.attachFile("f:/__BOOKS__/J2EE JSF Tutorial.pdf");
        multipart.addBodyPart(messageBodyPart);


        msg.setContent(multipart);

        msg.saveChanges();

///


        Transport transport = session.getTransport("smtp");
        transport.connect(host, userName, passWord);
        transport.sendMessage(msg, msg.getAllRecipients());

        transport.close();
        return true;
    }catch(Exception ex){ex.printStackTrace();}
    return false;
    }
    public static void main(String[] args){
        new SendPost().send(true,"asyaDudnik@hotmail.com", null, null, null, "hello World!");
    }
    }
