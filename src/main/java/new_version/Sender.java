package new_version;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;




public class Sender {
    private static Bot bot;

    public Sender(Bot bot) {
        Sender.bot = bot;
    }

    public static void sendMsg(Message message, String text) {
        sendMsg(message.getChatId().toString(), text);
    }

    static void sendMsg(String userID, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userID);
        sendMessage.setText(text);

        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(String text, SendMessage sendMessage) {
        sendMessage.setText(text);

        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
