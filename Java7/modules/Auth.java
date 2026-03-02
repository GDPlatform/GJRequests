package modules.authentication;

import modules.credential.Credential;
import modules.endpoint_httprequest.EndpointRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Geometry Dash authentication / level scores helper
 * Compatible with Java 7+
 */
public class Auth {

    private static final String SECRET = "Wmfd2893gb7";

    /**
     * Get scores for a level
     * @param accountID Account ID
     * @param gjp2      GJP2 hash of password
     * @param levelID   Level ID
     * @return Server response (JSON / GD response)
     * @throws IOException
     */
    public static String getScores(String accountID, String gjp2, String levelID) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountID", accountID);
        params.put("gjp2", gjp2);
        params.put("levelID", levelID);
        params.put("secret", SECRET);

        return EndpointRequest.doPost("/database/getGJScores20.php", params, null);
    }
}
