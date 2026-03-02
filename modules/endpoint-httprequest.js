const https = require("https");
const querystring = require("querystring");

const GD_SERVER = "www.boomlings.com";

/**
 * Generic POST request helper for Geometry Dash endpoints
 */
function doPost(path, params, userAgent = "GeometryDash/2.11") {
    return new Promise((resolve, reject) => {
        const postData = querystring.stringify(params);

        const options = {
            hostname: GD_SERVER,
            path,
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Content-Length": Buffer.byteLength(postData),
                "User-Agent": userAgent
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

module.exports = { doPost };
