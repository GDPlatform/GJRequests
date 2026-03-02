package modules.credential;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Geometry Dash credential helper (Java 8)
 */
public class Credential {

    private static final String XOR_KEY = "37526";
    private static final String SALT = "mI29fmAnxgTs";

    /**
     * XOR cipher
     */
    public static String xorCipher(String input) {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = XOR_KEY.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[inputBytes.length];

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
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update((password + SALT).getBytes(StandardCharsets.UTF_8));
            byte[] digest = sha1.digest();

            // Convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
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
        String xored = xorCipher(input);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(xored.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decode GJP → Base64 URL-safe + XOR
     */
    public static String decodeGjp(String gjp) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(gjp);
        String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        return xorCipher(decoded);
    }
}
