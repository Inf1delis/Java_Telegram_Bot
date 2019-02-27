package new_version.users.admin.admin_commands.messages;

import new_version.DataBase;
import new_version.Sender;
import new_version.users.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.awt.dnd.DropTarget;

public class Messages {
    private String command;
    private Message message;
    private String[] freeBetMessages = User.freeBetMessages;
    private String[] premiumBetMessages = User.premiumBetMessages;

    public Messages(String command, Message message) {
        this.command = command;
        this.message = message;
    }

    public boolean commands(StringBuilder rawTextMessage) {


        switch (command.toLowerCase()) {
            case "/setfreemessages":
                (new SetMessages(message, rawTextMessage)).setFree();
                Sender.sendMsg(message, "Изменения приняты, на данный момент " +
                        Integer.toString(freeBetMessages.length) + " бесплатных Сообщение" );
                return true;

            case "/setpremiummessages":
                (new SetMessages(message, rawTextMessage)).setPremium();
                Sender.sendMsg(message, "Изменения приняты, на данный момент " +
                        Integer.toString(premiumBetMessages.length) + " премиум Сообщение");
                return true;

            case "/getpremiummessagesquantity":
                Sender.sendMsg(message, Integer.toString(premiumBetMessages.length));
                return true;

            case "/getfreemessagesquantity":
                Sender.sendMsg(message, Integer.toString(freeBetMessages.length));
                return true;

            case "/getfreemessages":
                Sender.sendMsg(message, arrayToString(freeBetMessages));
                return true;

            case "/getpremiummessages":
                Sender.sendMsg(message, arrayToString(premiumBetMessages));
                return true;

            case "/updatepremiummessages":
                (new UpdateMessages(message, rawTextMessage)).updatePremium();
                Sender.sendMsg(message, "Изменения приняты, до обновления было " +
                        Integer.toString(premiumBetMessages.length) + " премиум Сообщение");
                return true;

            case "/updatefreemessages":
                (new UpdateMessages(message, rawTextMessage)).updateFree();
                Sender.sendMsg(message, "Изменения приняты, до обновления было " +
                        Integer.toString(freeBetMessages.length) + " бесплатных Сообщение" );
                return true;

            case "/nullmessagesrequest":
                DataBase.nullMessageRequestAll();
                Sender.sendMsg(message, "Запрошенные сообщения обнулены у всех пользователей.");
                return true;

            case "/confirmbets":
                DataBase.confirmBets();
                return true;

            case "/sendtoall":
                DataBase.sendToAll(rawTextMessage.toString());
                return true;

        }

        return false;
    }


    private String arrayToString(String[] array) {
        StringBuilder str = new StringBuilder();
        for (String string : array) {
            str.append(string).append("\n\n");
        }
        return str.toString();
    }
}
