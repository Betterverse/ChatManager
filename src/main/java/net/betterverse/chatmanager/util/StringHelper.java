package net.betterverse.chatmanager.util;

public class StringHelper {

    public static String concatenate(String[] array, int startIndex) {
        StringBuilder reason = new StringBuilder();
        for (int i = startIndex; i < array.length; i++) {
            reason.append(array[i]);

            // Append a space if it is not the last element
            if (i + 1 < array.length) {
                reason.append(" ");
            }
        }

        return reason.toString();
    }

    public static String parseColors(String parse) {
        return parse.replace('&', '§');
    }

    public static String stripColors(String parse) {
        for (int i = 0; i < parse.length(); i++) {
            char check = parse.charAt(i);
            if (check == '&') {
                parse = parse.replaceFirst("&" + parse.charAt(i + 1), "");
            }
        }

        return parse;
    }
}
