import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class SqueakyClean {
    static String clean(String identifier) {
        StringBuilder builder = new StringBuilder();
        boolean uppercaseNext = false;
        var leetMap = Map.of(
            '@', 'a',
            '3', 'e',
            '1', 'l',
            '5', 's',
            '0', 'o',
            '4', 'a',
            '7', 't'
        );

        var ignoreCharacterList = List.of('¡', '!', '$', '#', '.');

        for (char c : identifier.toCharArray()) {
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
