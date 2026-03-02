package modules.message;

import modules.credential.Credential;
import modules.endpoint_httprequest.EndpointRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Geometry Dash message helper
 * Compatible with Java 7+
 */
public class Message {

    private static final String SECRET = "Wmfd2893gb7";

    /**
     * Upload (send) a private message
     * @param accountID Sender account ID
     * @param gjp2      GJP2 hash of password
     * @param toAccountID Recipient account ID
     * @param subject   Message subject (plain text)
     * @param body      Message body (plain text)
     * @return Server response
     * @throws IOException
     */
    public static String upload(String accountID, String gjp2, String toAccountID, String subject, String body) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountID", accountID);
        params.put("gjp2", gjp2);
        params.put("toAccountID", toAccountID);
        params.put("subject", Credential.encodeGjp(subject));
        params.put("body", Credential.encodeGjp(body));
        params.put("gameVersion", "22");
        params.put("binaryVersion", "42");
        params.put("gdw", "0");
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/uploadGJMessage20.php", params, null);
    }

    /**
     * Read (download) a private message
     * @param accountID Account ID
     * @param gjp2      GJP2 hash
     * @param messageID Message ID to download
     * @return Server response
     * @throws IOException
     */
    public static String read(String accountID, String gjp2, String messageID) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountID", accountID);
        params.put("gjp", gjp2);
        params.put("messageID", messageID);
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/downloadGJMessage20.php", params, null);
    }

    /**
     * Get list of messages (inbox or sent)
     * @param accountID Account ID
     * @param gjp2      GJP2 hash
     * @param page      Page number (optional, default 0)
     * @param sent      true = sent messages, false = inbox
     * @return Server response
     * @throws IOException
     */
    public static String messages(String accountID, String gjp2, int page, boolean sent) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountID", accountID);
        params.put("gjp2", gjp2);
        params.put("page", String.valueOf(page));
        params.put("getSent", sent ? "1" : "0");
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/getGJMessages20.php", params, null);
    }
}
