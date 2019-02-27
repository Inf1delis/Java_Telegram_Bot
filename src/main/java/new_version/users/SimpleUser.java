package new_version.users;

import new_version.Sender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SimpleUser extends User {


    public SimpleUser(Update update, int premiumMessagesRequest, int simpleMessagesRequest) {
        super(update, premiumMessagesRequest, simpleMessagesRequest);
        USER_STATUS_TEXT = "Обычный пользователь";
        SUBSCRIPTION_PRICE_TEXT = "100";
        HELP_TEXT = "/id - получить userID\n" +
                "/status - проверка статуса пользователя\n" +
                "@Infidelis - он мой создатель!";;
        execute();
    }

    @Override
    protected boolean betButtons () {
        Message message = update.getMessage();

        switch (message.getText().toLowerCase()) {
            case "кнопка 1":
                Sender.sendMsg(simpleBetSendMessage(message));

                return true;
        }
        return false;
    }
}
