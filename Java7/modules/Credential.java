package modules.credential;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Credential helper for Geometry Dash authentication
 * Compatible with Java 7+
 */
public class Credential {

    private static final String XOR_KEY = "37526";
    private static final String SALT = "mI29fmAnxgTs";

    // XOR cipher
    public static String xorCipher(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = (char)(input.charAt(i) ^ XOR_KEY.charAt(i % XOR_KEY.length()));
            output.append(c);
        }
        return output.toString();
    }

    // Generate GJP2 hash (SHA1 of password + salt)
    public static String generateGjp2(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update((password + SALT).getBytes("UTF-8"));
            byte[] digest = md.digest();

            // Convert bytes to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate GJP2 hash", e);
        }
    }

    // Base64 URL-safe encode (without padding)
    public static String encodeGjp(String password) {
        String xored = xorCipher(password);
        String base64 = base64Encode(xored.getBytes());
        // URL-safe replacements
        base64 = base64.replace('+', '-').replace('/', '_').replaceAll("=+$", "");
        return base64;
    }

    // Base64 decode + XOR
    public static String decodeGjp(String gjp) {
        String normalized = gjp.replace('-', '+').replace('_', '/');
        while (normalized.length() % 4 != 0) {
            normalized += "=";
        }
        byte[] decodedBytes = base64Decode(normalized);
        String decoded = new String(decodedBytes);
        return xorCipher(decoded);
    }

    // Java7-compatible Base64 encode (simple)
    private static String base64Encode(byte[] data) {
        final char[] base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < data.length) {
            int b0 = data[i++] & 0xFF;
            int b1 = i < data.length ? data[i++] & 0xFF : 0;
            int b2 = i < data.length ? data[i++] & 0xFF : 0;

            sb.append(base64chars[(b0 >> 2) & 0x3F]);
            sb.append(base64chars[((b0 << 4) | ((b1 & 0xF0) >> 4)) & 0x3F]);
            sb.append(i - 1 < data.length ? base64chars[((b1 << 2) | ((b2 & 0xC0) >> 6)) & 0x3F] : '=');
            sb.append(i < data.length ? base64chars[b2 & 0x3F] : '=');
        }
        return sb.toString();
    }

    // Java7-compatible Base64 decode
    private static byte[] base64Decode(String data) {
        final int[] map = new int[256];
        for (int j = 0; j < 256; j++) map[j] = -1;
