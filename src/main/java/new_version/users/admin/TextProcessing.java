package new_version.users.admin;

import java.util.ArrayList;

public class TextProcessing {

    public static String[] changeTextMessages (StringBuilder rawTextMessage) {
        ArrayList<String> arrayMessageText = new ArrayList<>();

        deleteSpaces(rawTextMessage);
        int stopIndex = rawTextMessage.indexOf("|");

        while (stopIndex > 0) {

            arrayMessageText.add(rawTextMessage.substring(0, stopIndex));
            rawTextMessage.delete(0, stopIndex + 1);

            deleteSpaces(rawTextMessage);
            stopIndex = rawTextMessage.indexOf("|");
        }

        arrayMessageText.add(rawTextMessage.toString());


        return arrayMessageText.toArray(new String[0]);
    }


    public static String getUserID (StringBuilder rawTextMessage) {

        while (true) {
            int slashIndex = rawTextMessage.indexOf("\n");
            if (slashIndex != -1) {
                rawTextMessage.deleteCharAt(slashIndex);
            }

            int spaceIndex = rawTextMessage.indexOf(" ") ;
            if (spaceIndex != -1) {
                rawTextMessage.deleteCharAt(spaceIndex);
            }

            if (slashIndex == spaceIndex && spaceIndex == -1) {
                break;
            }
        }

        return rawTextMessage.toString();
    }


    private static void deleteSpaces(StringBuilder rawTextMessage) {
        while (rawTextMessage.indexOf(" ") == 0 || rawTextMessage.indexOf("\n") == 0) {
            rawTextMessage.deleteCharAt(0);
        }
    }
}
