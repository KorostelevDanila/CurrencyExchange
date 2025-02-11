package utlis;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GetParameters {
    static public Map<String, String> parseFormBody(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<>();
        String body = request.getReader().lines().collect(Collectors.joining("&"));
        for (String param : body.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                params.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
            }
        }
        return params;
    }
}
