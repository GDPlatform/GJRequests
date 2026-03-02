const https = require("https");
const querystring = require("querystring");

const GD_SERVER = "www.boomlings.com"; // primary Geometry Dash API server
const SECRET = "Wmfd2893gb7"; // the secret param required for most GD API requests

function doPost(path, params) {
    return new Promise((resolve, reject) => {
        const postData = querystring.stringify(params);

        const options = {
            hostname: GD_SERVER,
            path,
            method: "POST",
            headers: {
                "User-Agent": "",
                "Content-Type": "application/x-www-form-urlencoded",
                "Content-Length": Buffer.byteLength(postData)
            }
        };

        const req = https.request(options, (res) => {
            let data = "";
            res.on("data", (chunk) => {
                data += chunk;
            });
            res.on("end", () => {
                resolve(data);
            });
        });

        req.on("error", reject);
        req.write(postData);
        req.end();
    });
}

/**
 * Upload (send) a private message
 * @param {Object} opts 
 * Required fields typically include:
 *   - accountID
 *   - gjp (GJP2 hash of password)
 *   - message (text)
 *   - toAccountID (the user you’re messaging)
 */
async function upload(opts) {
    const params = {
        secret: SECRET,
        accountID: opts.accountID,
        gjp2: opts.gjp2, // Use gjp2 for modern 2.2+ auth
        toAccountID: opts.toAccountID,
        subject: opts.subject, // API expects 'subject'
        body: opts.body,       // API expects 'body'
        gameVersion: 22,
        binaryVersion: 42,
        gdw: 0
    };

    return doPost("/database/uploadGJMessage20.php", params);
}

/**
 * Read (download) a private message
 * @param {Object} opts 
 * Required fields typically include:
 *   - accountID
 *   - gjp
 *   - messageID (the ID of the message to download)
 */
async function read(opts) {
    const params = {
        secret: SECRET,
        accountID: opts.accountID,
        gjp: opts.gjp,
        messageID: opts.messageID,
    };

    return doPost("/database/downloadGJMessage20.php", params);
}

async function messages(opts) {
    const params = {
        secret: SECRET,
        accountID: opts.accountID,
        gjp2: opts.gjp2, // SHA1(password + "mI29fmAnxgTs")
        page: opts.page || 0,     // optional (default 0)
        getSent: opts.sent ? 1 : 0 // optional (0 = inbox, 1 = sent)
    };

    return doPost("/database/getGJMessages20.php", params);
}

module.exports = { upload, read, messages };
