package new_version;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import new_version.exceptions.DataBaseConnectionError;
import new_version.users.PremiumUser;
import new_version.users.SimpleUser;
import new_version.users.User;
import new_version.users.admin.AdminUser;
import new_version.users.admin.TextProcessing;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DataBase {
    public static String PREMIUM = "premium";
    public static String SIMPLE = "simple";
    private static Statement statement;
    private static Logger log = Logger.getLogger(DataBase.class.getName());
    private static String URL = "jdbc:mysql://localhost:3306/telegram";
    private static String USER = "root";
    private static String PASSWORD = "";
    private static String[] adminList = new String[]{"197079657", "153290293", "531928074", "332110806", "441305929"};

    public DataBase() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = conn.createStatement();
        } catch (SQLException var2) {
            log.log(Level.SEVERE, "SQLExeption: ", var2);
        }

    }

    public static void updateUserMessageRequest(Update update, String userStatus, int messageRest) {
        String userID = update.getMessage().getFrom().getId().toString();

        try {
            statement.execute(updateMessageRequestSql(userID, userStatus, messageRest));
        } catch (SQLException var5) {
            log.log(Level.SEVERE, "SqlException: ", var5);
        }

    }

    public static boolean updatePremium(StringBuilder rawTextMessage) {
        String userID = TextProcessing.getUserID(rawTextMessage);

        try {
            Integer.parseInt(userID);
        } catch (Exception var4) {
            log.log(Level.SEVERE, "IncorrectEnterData: ", var4);
            return false;
        }

        try {
            statement.execute(updatePremiumSql(userID));
            return true;
        } catch (SQLException var3) {
            log.log(Level.SEVERE, "SqlException: ", var3);
            return false;
        }
    }

    private static void send(String sqlRequest, String text) {
        try {
            try (ResultSet result = statement.executeQuery(sqlRequest)) {

                while (result.next()) {
                    String userID = result.getString("userID");
                    Sender.sendMsg(userID, text);
                }
            }
        } catch (SQLException var4) {
            log.log(Level.SEVERE, "SqlException: ", var4);
        }

    }

    public static void confirmBets() {
        (new Thread(new MyRunnable(sendPremiumSql(), User.PREMIUM_SEND_TEXT))).start();
        (new Thread(new MyRunnable(sendSimpleSql(), User.SIMPLE_SEND_TEXT))).start();
    }

    public static void sendToAll(String text) {
        (new Thread(new MyRunnable(sendPremiumSql(), text))).start();
        (new Thread(new MyRunnable(sendSimpleSql(), text))).start();
    }

    public static void nullMessageRequestAll() {
        doSql(nullMessageRequestSql());
    }

    public static void updatePremiumTime() {
        try {
            statement.execute(updatePremiumTimeSql());
            ResultSet result = statement.executeQuery(sendPremiumSql());

            while(result.next()) {
                String userID = Integer.toString(result.getInt("userID"));
                switch(result.getInt("premiumTime")) {
                    case 360:
                        Sender.sendMsg(userID, "Половина вашей подписки уже прошла! :)");
                        break;
                    case 552:
                        Sender.sendMsg(userID, "Осталось всего неделя до конца подписки! :)");
                        break;
                    case 672:
                        Sender.sendMsg(userID, "Осталось всего 2 дня до конца подписки!");
                        break;
                    case 720:
                        deletePremium(userID);
                        Sender.sendMsg(userID, "Ваша подписка закончилась :(");
                }
            }
        } catch (SQLException var2) {
            log.log(Level.SEVERE, "SqlException: ", var2);
        }

    }

    private static void deletePremium(String userID) {
        String sql = "update users set premium = 0, premiumTime = 0 where userID =" + userID + ";";
        doSql(sql);
    }

    public static void deleteUser(String userID) {
        doSql(deleteUserSql(userID));
    }

    private static void doSql(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException var2) {
            log.log(Level.SEVERE, "SQLException: ", var2);
        }

    }

    private static String findUserSql(String userID) {
        return "select premiumMessages, simpleMessages, premium, premiumTime from users where UserID =" + userID + ";";
    }

    private static String nullMessageRequestSql() {
        return "update users set simpleMessages = 0, premiumMessages = 0;";
    }

    private static String updateMessageRequestSql(String userID, String userStatus, int messageRequest) {
        return "update users set " + userStatus + "Messages = " + messageRequest + " where userID = " + userID + ";";
    }

    private static String updatePremiumSql(String userID) {
        return "update users set premium = 1 where userID =" + userID + ";";
    }

    private static String sendPremiumSql() {
        return "select userID, premiumTime from users where premium = 1;";
    }

    private static String sendSimpleSql() {
        return "select userID from users where premium = 0;";
    }

    private static String updatePremiumTimeSql() {
        return "update users set premiumTime = premiumTime + 1 where premium = 1;";
    }

    private static String deleteUserSql(String userID) {
        return "delete from users where userID =" + userID + ";";
    }

    void check(Update update) {
        String userID = update.getMessage().getFrom().getId().toString();

        try {
            ResultSet result = statement.executeQuery(findUserSql(userID));
            if (result.next()) {
                int premiumMessages = result.getInt("premiumMessages");
                int simpleMessages = result.getInt("simpleMessages");
                String[] var6 = adminList;
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String admin = var6[var8];
                    if (admin.equals(userID)) {
                        log.info("admin " + userID + " entered");
                        new AdminUser(update, premiumMessages, simpleMessages);
                        return;
                    }
                }

                if (result.getInt("premium") == 1) {
                    log.info("premium " + userID + " entered");
                    new PremiumUser(update, premiumMessages, simpleMessages);
                } else {
                    log.info("simple " + userID + " entered");
                    new SimpleUser(update, premiumMessages, simpleMessages);
                }
            } else {
                log.info("there is no such user" + userID + "in db");

                try {
                    statement.execute(this.addUserSql(userID));
                } catch (SQLException var10) {
                    log.log(Level.SEVERE, "SqlException: ", var10);
                }

                log.info("simple " + userID + " entered");
                new SimpleUser(update, 0, 0);
            }
        } catch (SQLException var11) {
            log.log(Level.SEVERE, "SqlException: ", var11);
            throw new DataBaseConnectionError();
        }
    }

    private String addUserSql(String userID) {
        return "insert into users (userID) values (" + userID + ");";
    }

    static class MyRunnable implements Runnable {
        private String sql;
        private String text;

        MyRunnable(String sql, String text) {
            this.text = text;
            this.sql = sql;
        }

        public void run() {
            try {
                DataBase.send(sql, text);
            } catch (Exception var2) {
                log.log(Level.SEVERE, "SqlException: ", var2);
            }

        }
    }

}

