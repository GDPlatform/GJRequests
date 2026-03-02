package main;

import modules.credential.Credential;
import modules.message.Message;
import modules.authentication.Auth;

import java.io.IOException;
import java.util.Base64;

/**
 * Geometry Dash Client (Java 8)
 */
public class Main {

    private String accountID;
    private String gjp2;

    public Main() {
        this.accountID = null;
        this.gjp2 = null;
    }

    /**
     * Login using accountID and password (generates GJP2)
     */
    public void login(String accountID, String password) {
        if (accountID == null || password == null) {
            throw new IllegalArgumentException("AccountID and password are required.");
        }
        this.accountID = accountID;
        this.gjp2 = Credential.generateGjp2(password);
    }

    private void requireLogin() {
        if (this.accountID == null || this.gjp2 == null) {
            throw new IllegalStateException("Client not logged in. Call login() first.");
        }
    }

    /**
     * Get level scores
     */
    public String getLevelScores(String levelID) throws IOException {
        requireLogin();
        return Auth.getScores(this.accountID, this.gjp2, levelID);
    }

    /**
     * Send a private message
     */
    public String sendMessage(String toAccountID, String subject, String body) throws IOException {
        requireLogin();
        // Encode subject/body to Base64 (GD safe)
        String encodedSubject = Base64.getEncoder().encodeToString(subject.getBytes("UTF-8"));
        String encodedBody = Base64.getEncoder().encodeToString(body.getBytes("UTF-8"));
        return Message.upload(this.accountID, this.gjp2, toAccountID, encodedSubject, encodedBody);
    }

    /**
     * Read a single message
     */
    public String readMessage(String messageID) throws IOException {
        requireLogin();
        return Message.read(this.accountID, this.gjp2, messageID);
    }

    /**
     * Read messages (inbox or sent)
     */
    public String readMessages(int page, boolean sent) throws IOException {
        requireLogin();
        return Message.messages(this.accountID, this.gjp2, page, sent);
    }

    /**
     * Generic endpoint request
     */
    public String requestEndpoint(String endpoint, java.util.Map<String, String> params) throws IOException {
        requireLogin();
        if (params == null) {
            params = new java.util.HashMap<>();
        }
        params.put("accountID", this.accountID);
        params.put("gjp2", this.gjp2);
        params.put("secret", "Wmfd2893gb7");
        return modules.endpoint_httprequest.EndpointRequest.doPost("/database/" + endpoint, params, null);
    }
}
