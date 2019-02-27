package new_version.users.admin;

import new_version.Sender;
import new_version.users.User;
import new_version.users.admin.admin_commands.GivePremium;
import new_version.users.admin.admin_commands.messages.Messages;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class AdminUser extends User { // добавить команду добавления текста ставок, разные фичи админа


    public AdminUser(Update update, int premiumMessagesRequest, int simpleMessagesRequest) {
        super(update, premiumMessagesRequest, simpleMessagesRequest);
        USER_STATUS_TEXT = "Администратор";
        SUBSCRIPTION_PRICE_TEXT = "Ты адмен\n" +
                "Иди работай";

        HELP_TEXT = "Приветствую в кабинете админа!\n" +
                "/start - начало работы\n" +
                "/confirmBets - подтвержение ставок и рассылка всем уведомлений\n" +
                "/sendToAll [Сообщение] - рассылка всем сообщения\n\n" +
                "/setFreeMessages [Сообщение] | .. | [Сообщение] - задание бесплатных ставок\n" +
                "/updateFreeMessages [Сообщение] | .. | [Сообщение] - добавление бесплатных ставок сверху\n" +
                "/getFreeMessages - получение бесплатных Сообщение\n" +
                "/getFreeMessagesQuantity - количество бесплатных Сообщение\n\n" +
                "/setPremiumMessages [Сообщение] | .. | [Сообщение] - задание премиум Сообщение\n" +
                "/updatePremiumMessages [Сообщение] | .. | [Сообщение] - добавление премиум Сообщение сверху\n" +
                "/getPremiumMessages - получение премиум ставок\n" +
                "/getPremiumMessagesQuantity - количество премиум ставок\n" +
                "/givePremium [UserID] - дает пользователю премиум статус\n\n" +
                "/nullMessagesRequest - обнуляет все запрошенные сообщения у пользователей\n" +
                "/status - проверка статуса пользователя\n\n" +
                "/help - помощь\n";

        commandBoolean = commandBoolean && !adminCommands();
        execute();
    }

    @Override
    protected SendMessage mainKeyboard(Message message) {
        return keyboard.showPremiumMainKeyboard(message);
    }

    private boolean adminCommands () {
        Message message = update.getMessage();

        StringBuilder messageText = new StringBuilder(message.getText());
        int commandBorder = messageText.indexOf(" ");
        String command;
        StringBuilder rawTextMessage;
        if (commandBorder != -1) {
            command = messageText.substring(0, commandBorder);
            rawTextMessage = messageText.delete(0, commandBorder + 1);
        } else {
            command = messageText.substring(0);
            rawTextMessage = new StringBuilder("");
        }

        switch (command.toLowerCase()) {
            case "/help":
                Sender.sendMsg(message, HELP_TEXT);
                return true;

            case "/givepremium":
                new GivePremium(update.getMessage(), keyboard.showPremiumMainKeyboard(message), rawTextMessage);
                return  true;

            case "/stopthread":
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Sender.sendMsg(message, "Я подождал 10 секунд");
                return  true;
        }

        return new Messages(command, message).commands(rawTextMessage);
    }
}
