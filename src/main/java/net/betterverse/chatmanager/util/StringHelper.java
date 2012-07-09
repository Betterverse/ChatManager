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

    public static boolean isValidString(String check, boolean letters, boolean numbers, char... validCharacters) {
        for (int i = 0; i < check.length(); i++) {
            char character = check.charAt(i);
            if ((letters && !Character.isLetter(character)) && (numbers && !Character.isDigit(character))) {
                boolean valid = false;
                innerLoop: for (char c : validCharacters) {
                    if (character == c) {
                        valid = true;
                        break innerLoop;
                    }
                }

                if (!valid) {
                    return false;
                }
            }
        }

        return true;
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
