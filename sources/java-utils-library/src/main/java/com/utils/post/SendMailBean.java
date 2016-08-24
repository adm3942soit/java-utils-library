package com.utils.post;




import com.utils.archiv.ZipArchiv;
import com.utils.date.Dater;
import com.utils.file.Filer;
import ua.edu.file.MyFiler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

//#############################################################################################################
public class SendMailBean
{

    public static final String SMTP_HOST_NAME = "smtp.gmail.com";//"gmail.google.com";
    public static final String MAIL_USER = "admin84it@gmail.com";
    public static final String MAIL_PWD ="administrator";
    private static final String emailSubjectTxt = "Error in application CM84";
    public static final double limitAttachments=25000000.0;//bytes
    //private List exceptions=new ArrayList();
    private String fromEmail="";

//#############################################################################################################
 public static String send(String username, String from, String to, String cc, String bcc,
                       String subject, String message, String smtpServer, Long idApplicationError)
    {

        String result = "<BR>";

        String host = smtpServer;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "gmail.google.com");
        props.put("mail.smtp.user", MAIL_USER);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.debug", "false");
//587 is the Outgoing server (SMTP) port for IMAP. It uses a TLS
//encryption connection.
//
//465 is the Outgoing server (SMTP) port for pop. It uses an SSL
//encryption connection.
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        javax.mail.Session session
         = javax.mail.Session.getDefaultInstance(props,
                new javax.mail.Authenticator() { protected PasswordAuthentication getPasswordAuthentication()
                {return new PasswordAuthentication(MAIL_USER, MAIL_PWD);}});
        session.setDebug(true);


        try
        {
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,  InternetAddress.parse(to, false));


            if(cc!=null)
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc, false));

            if(bcc!=null)
            msg.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(bcc, false));

            if(subject!=null)  msg.setSubject(subject);
            else msg.setSubject(emailSubjectTxt);

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(message);

            MimeBodyPart mbp1 = new MimeBodyPart();
            String textError="my text";//getExceptionMessage(idApplicationError);


            if(textError!=null)
            {
              mbp1.setText(textError);
              mbp1.setContent(textError, "text/plain");
            }

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp);

            if(textError!=null)mp.addBodyPart(mbp1);


            msg.saveChanges();

            msg.setContent(mp);
            msg.setSentDate(new Date());

          //  log.info("updateApplicationErrors");
/*
            updateApplicationErrorsTable(username,idApplicationError,
                    createRecordInLetterTable(username, from, to, message, subject, cc, bcc)
                    , false);
*/

            Transport trans = session.getTransport("smtp");
            trans.connect(host, MAIL_USER, MAIL_PWD);

            trans.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
            trans.close();

            result = result + "Success! " +
                    "<B>Mail was successfully sent to </B></FONT>: " + to + "<BR>";

            if (cc!=null && !cc.equals(""))
            {
                result = result + "<FONT color=green><B>CCed To </B></FONT>: " + cc + "<BR>";
            }

            if (bcc!=null && !bcc.equals(""))
            {
                result = result + "<FONT color=green><B>BCCed To </B></FONT>: " + bcc;
            }

            result = result + "<BR><HR>";

/*
            updateApplicationErrorsTable(username,idApplicationError,
                    createRecordInLetterTable(username, from, to, message, subject, cc, bcc), true
                    );
*/


        }
        catch (MessagingException mex)
        {

            result = result + "<FONT SIZE=4 COLOR=\"blue\"> <B>Error : </B><BR><HR> " +
                    "<FONT SIZE=3 COLOR=\"black\">" + mex.toString() + "<BR><HR>";
        }
        catch (Exception e)
        {
            result = result + "<FONT SIZE=4 COLOR=\"blue\"> <B>Error : </B><BR><HR> " +
                    "<FONT SIZE=3 COLOR=\"black\">" + e.toString() + "<BR><HR>";

            e.printStackTrace();
        }
        finally
        {
            return result;
        }
 }
    //#############################################################################################################
    public static String send(String username, String from, String to, String cc, String bcc,
                              String subject, String message, String smtpServer, Long idApplicationError, String pathToAttachment)
    {

        String result = "<BR>";

        String host = smtpServer;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "gmail.google.com");
        props.put("mail.smtp.user", MAIL_USER);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.debug", "false");
//587 is the Outgoing server (SMTP) port for IMAP. It uses a TLS
//encryption connection.
//
//465 is the Outgoing server (SMTP) port for pop. It uses an SSL
//encryption connection.
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        props.setProperty("mail.smtp.starttls.enable", "екгу");
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        javax.mail.Session session
                = javax.mail.Session.getDefaultInstance(props,
                new javax.mail.Authenticator() { protected PasswordAuthentication getPasswordAuthentication()
                {return new PasswordAuthentication(MAIL_USER, MAIL_PWD);}});
        session.setDebug(false);


        try
        {
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,  InternetAddress.parse(to, false));


            if(cc!=null)
                msg.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(cc, false));

            if(bcc!=null)
                msg.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bcc, false));

            if(subject!=null)  msg.setSubject(subject);
            else msg.setSubject(emailSubjectTxt);

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(message);

            MimeBodyPart mbp1 = new MimeBodyPart();
            String textError="my text";//getExceptionMessage(idApplicationError);


            if(textError!=null)
            {
                mbp1.setText(textError);
                mbp1.setContent(textError, "text/plain");
            }

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp);

            if(textError!=null)mp.addBodyPart(mbp1);


            MimeBodyPart messageBodyPart = new MimeBodyPart();


            messageBodyPart = new MimeBodyPart();
     //       if(pathToAttachment!=null && !pathToAttachment.isEmpty()) {


                File attachment = pathToAttachment!=null?new File(pathToAttachment):null;
                double size = attachment!=null?attachment.length():0;
                System.out.println("!!!!!!!!!!!!!!size" + size);
                if (size>0 && size <= limitAttachments) {
                    DataSource source = new FileDataSource(pathToAttachment);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    //messageBodyPart.setFileName(attachment.getName());
                    messageBodyPart.setFileName(source.getName());
                    mp.addBodyPart(messageBodyPart);

                } else {
                   if(size>0) {
                       ZipArchiv.archiv("file.zip", attachment.getName().substring(0, attachment.getName().length() - 4));
                       File archiv = new File("file.zip");
                       size = archiv.length();
                       System.out.println("!!!!!!!!!!!!!!sizeArchiv" + size);
                       if (size <= limitAttachments) {
                           DataSource source = new FileDataSource(archiv.getAbsolutePath());
                           messageBodyPart.setDataHandler(new DataHandler(source));
                           //messageBodyPart.setFileName(attachment.getName());
                           messageBodyPart.setFileName(source.getName());
                           mp.addBodyPart(messageBodyPart);
                       }
                   }
                }

            msg.setContent(mp);
       //     }
            msg.saveChanges();
            msg.setSentDate(new Date());
            Transport trans = session.getTransport("smtp");
            trans.connect(host, MAIL_USER, MAIL_PWD);

            trans.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
            trans.close();

            result = result + "Success! " +
                    "<B>Mail was successfully sent to </B></FONT>: " + to + "<BR>";

            if (cc!=null && !cc.equals(""))
            {
                result = result + "<FONT color=green><B>CCed To </B></FONT>: " + cc + "<BR>";
            }

            if (bcc!=null && !bcc.equals(""))
            {
                result = result + "<FONT color=green><B>BCCed To </B></FONT>: " + bcc;
            }

            result = result + "<BR><HR>";

/*
            updateApplicationErrorsTable(username,idApplicationError,
                    createRecordInLetterTable(username, from, to, message, subject, cc, bcc), true
                    );
*/


        }
        catch (MessagingException mex)
        {

            result = result + "<FONT SIZE=4 COLOR=\"blue\"> <B>Error : </B><BR><HR> " +
                    "<FONT SIZE=3 COLOR=\"black\">" + mex.toString() + "<BR><HR>";
        }
        catch (Exception e)
        {
            result = result + "<FONT SIZE=4 COLOR=\"blue\"> <B>Error : </B><BR><HR> " +
                    "<FONT SIZE=3 COLOR=\"black\">" + e.toString() + "<BR><HR>";

            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }

//####################################################################
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }


    public static void main(String[] args){
     //  String message= Filer.readFile(new File(MyFiler.getCurrentDirectory()+File.separator+"logPaySystem_24_10_2014.txt"), true, false);
       // System.out.println(message);
        send(MAIL_USER, "asyaDudnik@hotmail.com","asyaDudnik@hotmail.com", "", "",// "adm3942soit@gmail.com",
                "subject", "message", SMTP_HOST_NAME, 0L, MyFiler.getCurrentDirectory()+ File.separator+"logPaySystem_24_10_2014.txt");
/*
        send(MAIL_USER, "asyaDudnik@hotmail.com",
                "asyaDudnik@hotmail.com",
                "",
                "",// "adm3942soit@gmail.com",
                "subject",
                "message",
                SMTP_HOST_NAME,
                0L,
                null//MyFiler.getCurrentDirectory()+ File.separator+"logPaySystem_24_10_2014.txt"
                 );
*/

    }
}
