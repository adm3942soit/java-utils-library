package com.utils.post;

import com.utils.date.Dater;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Created by PROGRAMMER II on 04.08.2014.
 */
public class SendMail
{
    private Session session;
    private String hostStr="smtp.gmail.com";
    private String passStr="administrator";
    private String userStr="admin84it@gmail.com";
    private static final String SMTP_SERVER_NAME = "smtp.gmail.com";
    private static final int SMTP_PORT = 465;//587;//995;

    private static final String MAIL_USER = "admin84it@gmail.com";
    private static final String MAIL_PWD ="administrator";

    public SendMail(String host, String username, String password)
    {
        if(!host.isEmpty())hostStr = host;
        if(!username.isEmpty())userStr = username;
        if(!password.isEmpty())passStr = password;


        Properties properties = System.getProperties();
/*        properties.put("mail.smtp.host", SMTP_SERVER_NAME);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", Integer.toString(SMTP_PORT));
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback","false ");
*/


        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "gmail.google.com");
        properties.put("mail.smtp.user", MAIL_USER);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", Integer.toString(SMTP_PORT));
        properties.put("mail.smtp.debug", "false");
//587 is the Outgoing server (SMTP) port for IMAP. It uses a TLS
//encryption connection.
//
//465 is the Outgoing server (SMTP) port for pop. It uses an SSL
//encryption connection.
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        session
                = javax.mail.Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() { protected PasswordAuthentication getPasswordAuthentication()
                {return new PasswordAuthentication(MAIL_USER, MAIL_PWD);}});
        session.setDebug(false);


/*
        session = Session.getDefaultInstance(properties, null);
        session.setDebug(false);
*/
    }

    public void sendMessage(String fromAddr, String toAddr, String subject, String mainBody, String attach) throws Exception
    {
        Transport transport = null;
        if(fromAddr==null || fromAddr.isEmpty())fromAddr="terminal@mail.com";
        if(toAddr==null || toAddr.isEmpty()) toAddr="sgsoft-pay@yandex.ru";
        try {
            InternetAddress internetaddress    = new InternetAddress(fromAddr);
            InternetAddress internetaddress1   = new InternetAddress(toAddr);
            InternetAddress ainternetaddress[] = { internetaddress1 };

            MimeMessage mimemessage = new MimeMessage(session);
            mimemessage.setFrom(internetaddress);

            mimemessage.setRecipients(javax.mail.Message.RecipientType.TO, ainternetaddress);
            mimemessage.setSubject(subject );
            mimemessage.setSentDate(new Date());

            MimeMultipart mimemultipart = new MimeMultipart();
            MimeBodyPart mimebodypart = new MimeBodyPart();

            mimebodypart.setText(mainBody);

            MimeBodyPart mimebodypart1 = new MimeBodyPart();
            mimemultipart.addBodyPart(mimebodypart);
            if(attach != null)
            {
                File file = new File(attach);
                FileDataSource filedatasource = new FileDataSource(attach);
                mimebodypart1.setDataHandler(new DataHandler(filedatasource));
                mimebodypart1.setFileName(file.getName());
                mimemultipart.addBodyPart(mimebodypart1);
            }

            mimemessage.setContent(mimemultipart);
            transport = session.getTransport("smtp");
            transport.connect(hostStr, userStr, passStr);
            mimemessage.saveChanges();
            transport.sendMessage(mimemessage, mimemessage.getAllRecipients());
        }
        catch(Exception exception)
        {
            throw exception;
        }
        finally
        {
            try
            {
                transport.close();
            }
            finally {     }
        }
    }
 public static void main(String[]args){
     SendMail bean=new SendMail("", "", "");
     try {
 /*        bean.sendMessage("terminal@email", "asyaDudnik@hotmail.com", "Reload Payment System",
                 "Error in Payment System", "screen.png");
 */        bean.sendMessage("", "", "Reload Payment System",
                 "Error in Payment System", "logPaySystem_"+ Dater.getDateForLog1()+".txt");

     }catch(Exception ex){ex.printStackTrace();}
 }
}