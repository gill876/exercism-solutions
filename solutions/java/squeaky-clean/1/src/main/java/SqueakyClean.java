import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class SqueakyClean {
    static String clean(String identifier) {
        char[] stringAsCharArray = identifier.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean uppercaseNext = false;
        Map<Character, Character> leetMap = new HashMap<Character, Character>();
        leetMap.put('@', 'a');
        leetMap.put('3', 'e');
        leetMap.put('1', 'l');
        leetMap.put('5', 's');
        leetMap.put('0', 'o');
        leetMap.put('4', 'a');
        leetMap.put('7', 't');

        List<Character> ignoreCharacterList = new ArrayList<>(Arrays.asList('¡', '!', '$', '#', '.'));

        for (char c : stringAsCharArray) {
            if (Character.isWhitespace(c)) {
                builder.append('_');
            } else if (c == '-') {
                uppercaseNext = true;
            } else if (Character.isLetter(c) && uppercaseNext) {
                builder.append(Character.toUpperCase(c));
                uppercaseNext = false;
            } else if (leetMap.get(c) != null) {
                builder.append(leetMap.get(c));
            } else if (ignoreCharacterList.contains(c)) {
                continue;
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }
}
