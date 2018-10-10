package business.mail;

import app.App;
import model.Thread;
import model.User;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import util.ExceptionUtil;

import java.util.List;

public class ThreadMail implements Runnable {

    private Thread thread;

    public ThreadMail(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        List<? extends User> to = thread.assignedUsers();
        if (to.isEmpty())
            return;

        try {
            Email email = Mailer.createEmail();

            for (User user : to)
                email.addTo(user.getEmail(), user.getName());

            email.setSubject("New thread assigned: " + thread.getTopic());
            email.setMsg(formatUserMsg(thread));
            email.send();
        } catch (EmailException e) {
            App.logger.warn("Cannot send mail an error occurred.");
            App.logger.debug(e.getMessage());
            App.logger.debug(ExceptionUtil.getStackTraceAsString(e));
        }

    }

    private String formatUserMsg(Thread thread) {
        // String msg = thread.getText();
        // msg = msg.replace("<br>", "\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append("A new thread has been created.");
        sb.append("\r\n");
        sb.append("----------");
        sb.append("\r\n");
        sb.append(thread.getName());
        sb.append(" - ");
        sb.append(thread.getEmail());
        sb.append("\r\n");
        sb.append(thread.getText());
        sb.append("\r\n\r\nDo Not Reply to This Email");
        return sb.toString();
    }

}
