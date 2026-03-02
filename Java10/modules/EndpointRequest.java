package modules.credential;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Geometry Dash credential helper (Java 10)
 */
public class Credential {

    private static final String XOR_KEY = "37526";
    private static final String SALT = "mI29fmAnxgTs";

    /**
     * XOR cipher
     */
    public static String xorCipher(String input) {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        var keyBytes = XOR_KEY.getBytes(StandardCharsets.UTF_8);
        var output = new byte[inputBytes.length];

        for (int i = 0; i < inputBytes.length; i++) {
            output[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(output, StandardCharsets.UTF_8);
    }

    /**
     * SHA1(password + salt) → GJP2
     */
    public static String generateGjp2(String password) {
        try {
            var sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update((password + SALT).getBytes(StandardCharsets.UTF_8));
            var digest = sha1.digest();

            var hexString = new StringBuilder();
            for (var b : digest) {
                var hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 not supported", e);
        }
    }

    /**
     * Encode string → XOR + Base64 URL-safe
     */
    public static String encodeGjp(String input) {
        var xored = xorCipher(input);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(xored.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decode GJP → Base64 URL-safe + XOR
     */
    public static String decodeGjp(String gjp) {
        var decodedBytes = Base64.getUrlDecoder().decode(gjp);
        var decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        return xorCipher(decoded);
    }
}
