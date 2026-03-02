package modules.message;

import modules.credential.Credential;
import modules.endpoint_httprequest.EndpointRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GD message helper (send/read messages)
 */
public class Message {

    private static final String SECRET = "Wmfd2893gb7";

    public static String upload(String accountID, String gjp2, String toAccountID, String subject, String body) throws IOException {
        Map<String, String> params = new HashMap<>();
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

    public static String read(String accountID, String gjp2, String messageID) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("accountID", accountID);
        params.put("gjp", gjp2);
        params.put("messageID", messageID);
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/downloadGJMessage20.php", params, null);
    }

    public static String messages(String accountID, String gjp2, int page, boolean sent) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("accountID", accountID);
        params.put("gjp2", gjp2);
        params.put("page", String.valueOf(page));
        params.put("getSent", sent ? "1" : "0");
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/getGJMessages20.php", params, null);
    }
}
