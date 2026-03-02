package main;

import modules.credential.Credential;
import modules.message.Message;
import modules.authentication.Auth;

import java.io.IOException;

/**
 * Geometry Dash Client for Java 7+
 */
public class Main {

    private String accountID;
    private String gjp2;

    public Main() {
        this.accountID = null;
        this.gjp2 = null;
    }

    /**
     * "Login" using accountID and password (generates GJP2)
     * @param accountID Account ID
     * @param password  Account password
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
     * @param levelID Level ID
     * @return Server response
     * @throws IOException
     */
    public String getLevelScores(String levelID) throws IOException {
        requireLogin();
        return Auth.getScores(this.accountID, this.gjp2, levelID);
    }

    /**
     * Send a private message
     * @param toAccountID Recipient ID
     * @param subject     Message subject
     * @param body        Message body
     * @return Server response
     * @throws IOException
     */
    public String sendMessage(String toAccountID, String subject, String body) throws IOException {
        requireLogin();
        return Message.upload(this.accountID, this.gjp2, toAccountID, subject, body);
    }

    /**
     * Read a single message
     * @param messageID Message ID
     * @return Server response
     * @throws IOException
     */
    public String readMessage(String messageID) throws IOException {
        requireLogin();
        return Message.read(this.accountID, this.gjp2, messageID);
    }

    /**
     * Read messages (inbox or sent)
     * @param page Page number
     * @param sent true = sent messages, false = inbox
     * @return Server response
     * @throws IOException
     */
    public String readMessages(int page, boolean sent) throws IOException {
        requireLogin();
        return Message.messages(this.accountID, this.gjp2, page, sent);
    }
}
