import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class SqueakyClean {
    static String clean(String identifier) {
        StringBuilder builder = new StringBuilder();
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
        boolean[] uppercaseNextCountainer = {false};

        identifier.chars().forEach( c -> {
            char charRep = (char) c;
            if (Character.isWhitespace(charRep)) {
                builder.append('_');
            } else if (charRep == '-') {
                uppercaseNextCountainer[0] = true;
            } else if (Character.isLetter(charRep) && uppercaseNextCountainer[0]) {
                builder.append(Character.toUpperCase(charRep));
                uppercaseNextCountainer[0] = false;
            } else if (leetMap.get(charRep) != null) {
                builder.append(leetMap.get(charRep));
            } else if (ignoreCharacterList.contains(charRep)) {
                return;
            } else {
                builder.append(charRep);
            }
        });

        return builder.toString();
    }
}
