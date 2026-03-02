#pragma once
#include <string>
#include <vector>
#include <openssl/sha.h>
#include <openssl/evp.h>
#include <stdexcept>

namespace GDCredential {

    const std::string XOR_KEY = "37526";
    const std::string SALT = "mI29fmAnxgTs";

    // XOR cipher
    inline std::string xorCipher(const std::string& input, const std::string& key = XOR_KEY) {
        std::string output = input;
        for (size_t i = 0; i < input.size(); i++) {
            output[i] = input[i] ^ key[i % key.size()];
        }
        return output;
    }

    // SHA1(password + salt) -> hex string
    inline std::string generateGjp2(const std::string& password, const std::string& salt = SALT) {
        std::string input = password + salt;
        unsigned char hash[SHA_DIGEST_LENGTH];
        SHA1(reinterpret_cast<const unsigned char*>(input.c_str()), input.size(), hash);

        static const char hex_chars[] = "0123456789abcdef";
        std::string hex;
        hex.reserve(SHA_DIGEST_LENGTH * 2);
        for (int i = 0; i < SHA_DIGEST_LENGTH; i++) {
            hex.push_back(hex_chars[(hash[i] >> 4) & 0xF]);
            hex.push_back(hex_chars[hash[i] & 0xF]);
        }
        return hex;
    }

    // Base64 URL-safe encode
    inline std::string base64UrlEncode(const std::string& input) {
        std::string encoded;
        encoded.resize(4 * ((input.size() + 2) / 3));
        int out_len;
        EVP_EncodeBlock(reinterpret_cast<unsigned char*>(&encoded[0]),
                        reinterpret_cast<const unsigned char*>(input.c_str()),
                        input.size());

        // Replace + / = with URL-safe chars
        for (auto& c : encoded) {
            if (c == '+') c = '-';
            else if (c == '/') c = '_';
        }
        // Remove padding '='
        while (!encoded.empty() && encoded.back() == '=') encoded.pop_back();
        return encoded;
    }

    // Base64 URL-safe decode
    inline std::string base64UrlDecode(const std::string& input) {
        std::string temp = input;
        // Restore URL-safe chars
        for (auto& c : temp) {
            if (c == '-') c = '+';
            else if (c == '_') c = '/';
        }
        // Add padding
        while (temp.size() % 4) temp += '=';

        std::vector<unsigned char> decoded(temp.size());
        int out_len = EVP_DecodeBlock(decoded.data(),
                                      reinterpret_cast<const unsigned char*>(temp.c_str()),
                                      temp.size());
        return std::string(reinterpret_cast<char*>(decoded.data()), out_len);
    }

    // XOR + Base64 (URL-safe)
    inline std::string encodeGjp(const std::string& password) {
        std::string xored = xorCipher(password);
        return base64UrlEncode(xored);
    }

    // Reverse Base64 URL-safe + XOR
    inline std::string decodeGjp(const std::string& gjp) {
        std::string decoded = base64UrlDecode(gjp);
        return xorCipher(decoded);
    }

}
