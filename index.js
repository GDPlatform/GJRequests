const { generateGjp2 } = require("./modules/gjp");
const { messages, read, upload } = require("./modules/message");
const { getScores } = require("./modules/scores"); // new module we defined
const { doPost } = require("./modules/endpoint-httprequest");

class Client {
    constructor() {
        this.accountID = null;
        this.gjp2 = null;
    }

    /**
     * "Login" by providing your account ID and password
     * for APIs that accept gjp2 (no actual login call needed)
     */
    async login({ accountID, password }) {
        if (!accountID || !password) {
            throw new Error("AccountID and password are required.");
        }

        this.accountID = accountID;
        this.gjp2 = generateGjp2(password);

        return {
            accountID: this.accountID,
            gjp2: this.gjp2
        };
    }

    async getLevelScores(levelID) {
        this._requireLogin();
        return getScores({
            accountID: this.accountID,
            gjp2: this.gjp2,
            levelID
        });
    }

    async sendMessage(toAccountID, subject = "untitled", body = "") {
        this._requireLogin();

        // Helper to match the Python base64.b64encode(b"...").decode() logic
        const encode = (str) => Buffer.from(str).toString('base64');

        return upload({
            accountID: this.accountID,
            gjp2: this.gjp2,
            toAccountID: toAccountID,
            subject: encode(subject), // Encodes "You're dumb lol"
            body: encode(body)         // Encodes "Mhm yep..."
        });
    }

    async readMessage(messageID) {
        this._requireLogin();

        return read({
            accountID: this.accountID,
            gjp: this.gjp2,
            messageID
        });
    }

    async readMessages({ page = 0, sent = false } = {}) {
        this._requireLogin();

        return messages({
            accountID: this.accountID,
            gjp2: this.gjp2,
            page,
            sent
        });
    }

    async requestEndpoint(endpoint, params = {}, secret = "Wmfd2893gb7") {
        this._requireLogin();

        // Always include accountID and gjp2
        params.accountID = this.accountID;
        params.gjp2 = this.gjp2;
        params.secret = secret;

        return doPost(`/database/${endpoint}`, params);
    }

    _requireLogin() {
        if (!this.accountID || !this.gjp2) {
            throw new Error("Client not logged in. Call login() first.");
        }
    }
}

module.exports = { Client };
