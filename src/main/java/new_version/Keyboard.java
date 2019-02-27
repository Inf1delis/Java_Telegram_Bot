package new_version;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keyboard {

    private ReplyKeyboardMarkup usingKeyboard;
    private List<KeyboardRow> mainKeyboard;
    private List<KeyboardRow> premiumMainKeyboard;
    private List<KeyboardRow> betKeyboard;
    private SendMessage sendMessage;


    public Keyboard() {
        initKeyboard();
        setMainKeyboard();
        setBetKeyboard();
        setPremiumMainKeyboard();
        sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(usingKeyboard);
        sendMessage.setText("Ознакомьтесь со следующим");
    }

    private void setMainKeyboard() {

        mainKeyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Кнопка 1"));
        mainKeyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Кнопка 2"));
        keyboardSecondRow.add(new KeyboardButton("Кнопка 3"));
        mainKeyboard.add(keyboardSecondRow);

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Кнопка 4"));
        keyboardThirdRow.add(new KeyboardButton("Кнопка 5"));
        mainKeyboard.add(keyboardThirdRow);

    }

    private void setPremiumMainKeyboard() {
        premiumMainKeyboard = new ArrayList<>(mainKeyboard);

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Кнопка 1"));
        keyboardFirstRow.add(new KeyboardButton("Кнопка 2"));

        premiumMainKeyboard.remove(0);
        premiumMainKeyboard.add(0, keyboardFirstRow);
    }

    private void setBetKeyboard () {
        betKeyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Кнопка 3"));
        keyboardFirstRow.add(new KeyboardButton("Кнопка 4"));
        betKeyboard.add(keyboardFirstRow);


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Кнопка 1"));
        betKeyboard.add(keyboardSecondRow);


        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Назад"));

        keyboardThirdRow.add(new KeyboardButton("Инфо"));
        betKeyboard.add(keyboardThirdRow);
    }

    private void initKeyboard() {
        usingKeyboard = new ReplyKeyboardMarkup();
        usingKeyboard.setSelective(true);
        usingKeyboard.setResizeKeyboard(true);
        usingKeyboard.setOneTimeKeyboard(false);
    }

    public SendMessage showMainKeyboard (Message message) {
        sendMessage.setChatId(message.getChatId().toString());
        usingKeyboard.setKeyboard(mainKeyboard);
        return sendMessage;
    }

    public SendMessage showBetKeyboard (Message message) {
        sendMessage.setChatId(message.getChatId().toString());
        usingKeyboard.setKeyboard(betKeyboard);
        return sendMessage;
    }


    public SendMessage showPremiumMainKeyboard (Message message) {
        sendMessage.setChatId(message.getChatId().toString());
        usingKeyboard.setKeyboard(premiumMainKeyboard);
        return sendMessage;
    }
}

