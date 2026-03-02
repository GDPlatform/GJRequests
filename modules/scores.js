const https = require("https");
const querystring = require("querystring");

const GD_SERVER = "www.boomlings.com";
const SECRET = "Wmfd2893gb7"; // standard for user/score APIs

function doPost(path, params) {
    return new Promise((resolve, reject) => {
        const postData = querystring.stringify(params);

        const options = {
            hostname: GD_SERVER,
            path,
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Content-Length": Buffer.byteLength(postData),
                "User-Agent": "GeometryDash/2.11"
            }
        };

        const req = https.request(options, (res) => {
            let data = "";
            res.on("data", chunk => data += chunk);
            res.on("end", () => resolve(data));
        });

        req.on("error", reject);
        req.write(postData);
        req.end();
    });
}

/**
 * Get scores for a level
 * @param {Object} opts
 * Required fields:
 *   - accountID
 *   - gjp2 (SHA1 hash of your password)
 *   - levelID
 */
async function getScores({ accountID, gjp2, levelID }) {
    const params = {
        accountID,
        gjp2,
        levelID,
        secret: SECRET
    };

    return doPost("/database/getGJScores20.php", params);
}

module.exports = { getScores };
