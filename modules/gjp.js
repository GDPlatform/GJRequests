const crypto = require("crypto");

const XOR_KEY = "37526";
const SALT = "mI29fmAnxgTs";

// XOR cipher
function xorCipher(input, key = XOR_KEY) {
    const inputBuffer = Buffer.from(input, "utf8");
    const keyBuffer = Buffer.from(key, "utf8");

    const output = Buffer.alloc(inputBuffer.length);

    for (let i = 0; i < inputBuffer.length; i++) {
        output[i] = inputBuffer[i] ^ keyBuffer[i % keyBuffer.length];
    }

    return output.toString("utf8");
}

// SHA1(password + salt)
function generateGjp2(password = "", salt = SALT) {
    return crypto
        .createHash("sha1")
        .update(password + salt)
        .digest("hex");
}

// XOR + Base64 (URL-safe)
function encodeGjp(password) {
    const xored = xorCipher(password);

    return Buffer.from(xored, "utf8")
        .toString("base64")
        .replace(/\+/g, "-")
        .replace(/\//g, "_")
        .replace(/=+$/, ""); // remove padding (GD-safe)
}

// Reverse URL-safe Base64 + XOR
function decodeGjp(gjp) {
    // Restore URL-safe chars
    let normalized = gjp
        .replace(/-/g, "+")
        .replace(/_/g, "/");

    // Restore padding
    while (normalized.length % 4) {
        normalized += "=";
    }

    const decoded = Buffer.from(normalized, "base64").toString("utf8");

    return xorCipher(decoded);
}

module.exports = {
    generateGjp2,
    encodeGjp,
    decodeGjp
};
