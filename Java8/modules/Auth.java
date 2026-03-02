package modules.authentication;

import modules.credential.Credential;
import modules.endpoint_httprequest.EndpointRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Geometry Dash level scores helper
 */
public class Auth {

    private static final String SECRET = "Wmfd2893gb7";

    public static String getScores(String accountID, String gjp2, String levelID) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("accountID", accountID);
        params.put("gjp2", gjp2);
        params.put("levelID", levelID);
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/getGJScores20.php", params, null);
    }
}
