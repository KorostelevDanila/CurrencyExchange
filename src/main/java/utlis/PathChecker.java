package utlis;

public class PathChecker {
    public static String checkCurrencyPath(String path) {
        if (path != null && !path.equals("/")) {
            return path.replaceFirst("/", "");
        }

        return null;
    }
}
