package new_version.users.admin.admin_commands.messages;

import new_version.Sender;
import new_version.users.User;
import new_version.users.admin.TextProcessing;
import new_version.users.admin.admin_commands.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public class SetMessages extends Command {
    private StringBuilder rawTextMessage;

    public SetMessages(Message message, StringBuilder rawTextMessage) {
        super(message);
        this.rawTextMessage = rawTextMessage;
    }

    public void setPremium () {
        User.premiumBetMessages = TextProcessing.changeTextMessages(rawTextMessage);
    }

    public void setFree () {
        User.freeBetMessages = TextProcessing.changeTextMessages(rawTextMessage);
    }

}
