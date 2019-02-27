package new_version.users.admin.admin_commands.messages;

import new_version.Sender;
import new_version.users.User;
import new_version.users.admin.TextProcessing;
import new_version.users.admin.admin_commands.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UpdateMessages extends Command {
    private StringBuilder rawTextMessage;

    public UpdateMessages(Message message, StringBuilder rawTextMessage) {
        super(message);
        this.rawTextMessage = rawTextMessage;
    }

    public void updatePremium () {
        String[] premiumBetMessages = TextProcessing.changeTextMessages(rawTextMessage);
        User.premiumBetMessages = concat(User.premiumBetMessages,premiumBetMessages);
    }

    public void updateFree () {
        String[] freeBetMessages = TextProcessing.changeTextMessages(rawTextMessage);
        User.freeBetMessages = concat(User.freeBetMessages,freeBetMessages);
    }

    public String[] concat(String[] a, String[] b) {
        int aLen = a.length;
        int bLen = b.length;
        String[] c= new String[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
