package new_version;

import new_version.exceptions.DataBaseConnectionError;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

//  mvn clean heroku:deploy

public class Bot extends TelegramLongPollingBot {
    private static Logger log = Logger.getLogger(Bot.class.getName());

    private static String baseURL = "URL" ;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        try {
            TelegramBotsApi telegramBotsApi =
                    new TelegramBotsApi();
            Bot bot = new Bot();
            telegramBotsApi.registerBot(bot);
            new Sender(bot);
        } catch (TelegramApiException e) {
            log.log(Level.SEVERE, "TelegramApiException: ", e);
        }


    }


    public void onUpdateReceived(Update update) {
        Thread thread = new Thread(new MyRunnable(update));
        thread.start();

    }

    class MyRunnable implements Runnable {
        private Update update;

        MyRunnable(Update update) {
            this.update = update;
        }

        @Override
        public void run() {
            try {
                new DataBase().check(update);
            } catch (DataBaseConnectionError e) {
                log.log(Level.SEVERE, "DataBaseConnectionError: ", e);
            }
        }
    }


    public String getBotToken() {
        return "TOKEN";
    }


    public String getBotUsername() {
        return "BOT_NAME";
    }



}
