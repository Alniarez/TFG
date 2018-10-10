package business.mail;

import config.MailConfig;
import model.Message;
import model.Thread;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Mailer {

    public static void sendNewThreadMail(Thread thread) {
        thread.setText(thread.getText().replace("<br>", "\r\n"));
        new java.lang.Thread(new ThreadMail(thread)).run();
    }

    public static void sendNewReplyMail(Message message) {
        message.setText(message.getText().replace("<br>", "\r\n"));
        new java.lang.Thread(new MessageMail(message)).run();

    }

    static Email createEmail() throws EmailException {
        Email email = new SimpleEmail();

        email.setHostName(MailConfig.get("hostName"));
        int smtpPort = Integer.parseInt(MailConfig.get("SmtpPort"));
        email.setSmtpPort(smtpPort);

        email.setAuthenticator(new DefaultAuthenticator(MailConfig.get("authenticator.user"),
                MailConfig.get("authenticator.password")));
        email.setSSLOnConnect(true);
        email.setFrom(MailConfig.get("authenticator.user"));
        return email;
    }

}
