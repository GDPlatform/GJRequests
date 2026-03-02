package modules.endpoint_httprequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Generic POST request helper for Geometry Dash endpoints
 */
public class EndpointRequest {

    private static final String GD_SERVER = "https://www.boomlings.com";

    public static String doPost(String path, Map<String, String> params, String userAgent) throws IOException {
        if (userAgent == null) {
            userAgent = "GeometryDash/2.11";
        }

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        URL url = new URL(GD_SERVER + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", userAgent);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postDataBytes);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
