package modules.endpoint_httprequest;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Generic HTTP POST helper for Geometry Dash endpoints
 * Compatible with Java 7+
 */
public class EndpointRequest {

    private static final String GD_SERVER = "https://www.boomlings.com";

    /**
     * Send a POST request to a Geometry Dash endpoint
     *
     * @param endpoint API endpoint, e.g., "/database/getGJScores20.php"
     * @param params   Map of POST parameters
     * @param userAgent Optional user agent (default: GeometryDash/2.11)
     * @return Server response as a String
     * @throws IOException
     */
    public static String doPost(String endpoint, Map<String, String> params, String userAgent) throws IOException {
        if (userAgent == null || userAgent.isEmpty()) {
            userAgent = "GeometryDash/2.11";
        }

        // Build POST data
        String postData = encodeParams(params);

        URL url = new URL(GD_SERVER + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", userAgent);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.getBytes("UTF-8").length));

        // Write POST data
        OutputStream os = conn.getOutputStream();
        os.write(postData.getBytes("UTF-8"));
        os.flush();
        os.close();

        // Read response
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();

        return new String(baos.toByteArray(), "UTF-8");
    }

    /**
     * Helper to URL-encode parameters for POST request
     *
     * @param params Map of key-value pairs
     * @return URL-encoded query string
     * @throws IOException
     */
    private static String encodeParams(Map<String, String> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        boolean first = true;
        for (Map.Entry<String, String> entry : entrySet) {
            if (!first) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            first = false;
        }
        return sb.toString();
    }
}
