package utlis;

import java.util.HashMap;
import java.util.Map;

public class PathChecker {
    public static String checkCurrencyPath(String path) {
        if (path != null && !path.equals("/")) {
            return path.replaceFirst("/", "");
        }

        return null;
    }

    public static Map<String, String> checkExchangeRatePath(String path) {
        if (path != null && !path.equals("/")) {
            path = path.replaceFirst("/", "");
            if (path.length() == 6) {
                String from = path.substring(0, 3);
                String to = path.substring(3);
                Map<String, String> exchangePair = new HashMap<>();
                exchangePair.put("from", from);
                exchangePair.put("to", to);
                return exchangePair;
            }
        }

        return null;
    }
}
