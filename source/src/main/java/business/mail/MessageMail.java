package business.mail;

import app.App;
import model.Message;
import model.Thread;
import model.User;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import util.ExceptionUtil;

public class MessageMail implements Runnable {

    private Message message;

    public MessageMail(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Thread original = new Thread().setId(message.getThreadId()).one();

        try {
            Email email = Mailer.createEmail();

            email.addTo(original.getEmail(), original.getName());

            email.setSubject("Your message has a new reply: " + original.getTopic());
            email.setMsg(formatReplyMsg(original, message));
            email.send();
        } catch (EmailException e) {
            App.logger.warn("Cannot send mail an error occurred.");
            App.logger.debug(e.getMessage());
            App.logger.debug(ExceptionUtil.getStackTraceAsString(e));
        }

    }

    private String formatReplyMsg(Thread original, Message message) {
        User user = new User().setId(message.getUserId()).one();
        // String msg = message.getText();
        // msg = msg.replace("<br>", "\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append("New reply from ");
        sb.append(user.getName());
        sb.append(":\r\n");
        sb.append(message.getText());
        sb.append(":\r\n");
        sb.append("\r\n");
        sb.append("\r\n\r\nDo Not Reply to This Email");
        return sb.toString();
    }

}
