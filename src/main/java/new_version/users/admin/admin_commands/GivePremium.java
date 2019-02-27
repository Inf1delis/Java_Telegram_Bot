package new_version.users.admin.admin_commands;

import new_version.DataBase;
import new_version.Sender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class GivePremium extends Command {
    private SendMessage sendMessage;

    public GivePremium(Message message, SendMessage sendMessage, StringBuilder rawTextMessage) {
        super(message);
        this.sendMessage = sendMessage;
        Sender.sendMsg(message, givePrem(rawTextMessage));
    }



    private String givePrem (StringBuilder rawTextMessage) {
        if (DataBase.updatePremium(rawTextMessage)) {
            sendMessage.setText("Вы повышены до премиум статуса.");
            sendMessage.setChatId(rawTextMessage.toString());
            Sender.sendMsg(sendMessage);
            return  "Пользователь повышен до премиум статуса.";
        } else {
            return "Неверный формат ввода/Неверные данные.";
        }
    }


}
