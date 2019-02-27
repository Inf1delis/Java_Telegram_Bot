package new_version.users;

import new_version.DataBase;
import new_version.Keyboard;
import new_version.Sender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public abstract class User {

    protected Update update;
    protected Keyboard keyboard;
    protected int premiumMessagesRequest;
    protected int simpleMessagesRequest;
    protected boolean commandBoolean = true;

    public static String[] freeBetMessages = {"Бесплатный 1",
            "Бесплатный 2",
            "Бесплатный 3"};

    public static String[] premiumBetMessages = {"Премиум 1",
            "Премиум 2",
            "Премиум 3"};

    protected static String START_TEXT = "Привет!";
    protected static String INFO_TEXT = "Информация о боте";
    protected static String STATISTIC_TEXT = "146%";

    protected static String HELP_TEXT = "/id - получить userID\n" +
            "/status - проверка статуса пользователя\n" +
            "@Infidelis - он мой создатель!";

    protected static String HOW_IS_WORKS_TEXT = "Пока текст для этого меню не написан";

    protected static String ORDINAR_PRICE_TEXT = "100.000 $";
    protected static String EXPRESS_PRICE_TEXT = "200.000 $";
    protected static String SUBSCRIPTION_PRICE_TEXT = "2.000 rub";

    protected static String USER_STATUS_TEXT;


    public static String SIMPLE_SEND_TEXT = "Всех бетатестеров прошу прислать мне отчет, где вам удобно!!!";
    public static String PREMIUM_SEND_TEXT = SIMPLE_SEND_TEXT;


    public User(Update update, int premiumMessagesRequest, int simpleMessagesRequest) {
        this.update = update;
        this.keyboard = new Keyboard();
        this.simpleMessagesRequest = simpleMessagesRequest;
        this.premiumMessagesRequest = premiumMessagesRequest;
    }

    public void execute() {
        commandBoolean = commandBoolean && !betButtons() && !mainKeyboardCommands() && !betKeyboardCommands();
        if ( checkNullMessage() ) {
            if (commandBoolean) {
                Sender.sendMsg(update.getMessage(), "Извините, неизвестная команда");
            }
        }
    }

    protected boolean betButtons () {
        Message message = update.getMessage();

        switch (message.getText().toLowerCase()) {
            case "кнопка 1":
                Sender.sendMsg(simpleBetSendMessage(message));

                return true;

            case "кнопка 2":
                Sender.sendMsg(premiumBetSendMessage(message));

                return true;
        }

        return false;
    }

    protected boolean mainKeyboardCommands() {
        Message message = update.getMessage();

        switch (message.getText().toLowerCase()) {
            case "доп. возможности":
                Sender.sendMsg("Текст", betKeyboard(message));
                return true;

            case "статистика":
                Sender.sendMsg(STATISTIC_TEXT, mainKeyboard(message));
                return true;

            case "поддержка":
                Sender.sendMsg(HELP_TEXT, mainKeyboard(message));
                return true;

            case "принцип работы":
                Sender.sendMsg(HOW_IS_WORKS_TEXT, mainKeyboard(message));
                return true;

            case "/start":
                Sender.sendMsg( START_TEXT, mainKeyboard(message));
                return  true;

            case "/id":
                String str = message.getFrom().getId().toString();
                Sender.sendMsg(str, mainKeyboard(message));
                return true;

            case "/stop":
                DataBase.deleteUser(message.getFrom().getId().toString());
                return true;

            case "/status":
                Sender.sendMsg(USER_STATUS_TEXT, mainKeyboard(message));
                return true;
        }

        return false;
    }

    protected SendMessage simpleBetSendMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        String text;
        if (simpleMessagesRequest < freeBetMessages.length) {
            text = freeBetMessages[simpleMessagesRequest];
            DataBase.updateUserMessageRequest(update, DataBase.SIMPLE, simpleMessagesRequest + 1);
        } else {
            text = "В данный момент все ваши бесплатные действия закончилсь";
            sendMessage = betKeyboard(message);
        }

        return sendMessage.setText(text);
    }

    protected SendMessage premiumBetSendMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        String text;
        if (premiumMessagesRequest < premiumBetMessages.length){
            text = premiumBetMessages[premiumMessagesRequest];
            DataBase.updateUserMessageRequest(update, DataBase.PREMIUM, premiumMessagesRequest + 1);
        } else {
            text = "В данный момент все ваши премиум действия закончилсь";
            sendMessage = betKeyboard(message);
        }

        return sendMessage.setText(text);
    }

    protected boolean betKeyboardCommands() {
        Message message = update.getMessage();

        switch (message.getText().toLowerCase()) {
            case "кнопка 1":
                Sender.sendMsg( ORDINAR_PRICE_TEXT, betKeyboard(message));
                return true;

            case "кнопка 2":
                Sender.sendMsg( EXPRESS_PRICE_TEXT, betKeyboard(message));
                return true;

            case "кнопка 3":
                Sender.sendMsg( SUBSCRIPTION_PRICE_TEXT, betKeyboard(message));
                return true;

            case "назад":
                Sender.sendMsg("Возврат к главному меню", mainKeyboard(message));
                return true;

            case "инфо":
                Sender.sendMsg( INFO_TEXT, keyboard.showBetKeyboard(message));
                return true;
        }
        return false;
    }

    protected SendMessage betKeyboard (Message message) {
        return keyboard.showBetKeyboard(message);
    }

    protected SendMessage mainKeyboard (Message message) {
        return keyboard.showMainKeyboard(message);
    }

    protected boolean checkNullMessage() {
        Message message = update.getMessage();
        return message != null && message.hasText();
    }
}
