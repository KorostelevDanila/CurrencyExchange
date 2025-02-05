package utlis;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

public class JSONResponser {
    public static void sendJSONErrorMessage(String jsonErrorMessage, int errorCode, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", jsonErrorMessage);
        response.getWriter().write(jsonObject.toString());

        response.setStatus(errorCode);
    }
}
