package ir.sharif.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class Random {
    public static int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

	public static int getRandomInt(int max) {
		return getRandomInt(0, max);
	}


    public static String getRandomPassword() {
		// generate a random password with length 16 containing digits, lowercase and uppercase and special characters
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			int type = getRandomInt(0, 3);
			if (type == 0) {
				password.append((char) getRandomInt(48, 57));
			} else if (type == 1) {
				password.append((char) getRandomInt(65, 90));
			} else if (type == 2) {
				password.append((char) getRandomInt(97, 122));
			} else {
				password.append((char) getRandomInt(33, 47));
			}
		}
		return password.toString();
    }

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

	public static String generateNewToken() {
		byte[] randomBytes = new byte[24];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}
}
