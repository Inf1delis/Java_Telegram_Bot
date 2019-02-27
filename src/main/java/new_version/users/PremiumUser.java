package new_version.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PremiumUser extends User {



    public PremiumUser(Update update, int premiumMessagesRequest, int simpleMessagesRequest) {
        super(update, premiumMessagesRequest, simpleMessagesRequest);
        USER_STATUS_TEXT = "Премиум пользователь";
        HELP_TEXT = "/id - получить userID\n" +
                "/status - проверка статуса пользователя\n" +
                "@Infidelis - он мой создатель!";
        SUBSCRIPTION_PRICE_TEXT = "У вас уже есть подписка на месяц!\n" +
                "На данный момент у вас есть доступ ко всему";
        execute();
    }

    @Override
    protected SendMessage mainKeyboard(Message message) {
        return keyboard.showPremiumMainKeyboard(message);
    }
}
