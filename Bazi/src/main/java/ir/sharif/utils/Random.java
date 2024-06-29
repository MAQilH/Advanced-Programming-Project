package ir.sharif.utils;

import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;

import java.security.SecureRandom;
import java.util.ArrayList;
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

	public static Card getRandFromArrayListCard(ArrayList<Card> arr){
		if(arr.isEmpty()) return null;
		return arr.get(getCardArrayListHashCard(arr)%arr.size());
	}

	public static CardTypes getRandFromArrayListCardTypes(ArrayList<CardTypes> arr){
		if(arr.isEmpty()) return null;
		return arr.get(getCardArrayListHashCardTypes(arr)%arr.size());
	}

	private static int getCardArrayListHashCard(ArrayList<Card> arrayList){
		long P = 9983;
		long MOD = (int) (1e9 + 7);
		int hashCode = 0;
		for (Card card : arrayList) {
			hashCode = (int)(((long)hashCode)*P%MOD);
			hashCode = (int) ((hashCode + card.hashCode())%MOD);
		}
		return hashCode;
	}

	private static int getCardArrayListHashCardTypes(ArrayList<CardTypes> arrayList){
		long P = 9983;
		long MOD = (int) (1e9 + 7);
		int hashCode = 0;
		for (CardTypes card : arrayList) {
			hashCode = (int)(((long)hashCode)*P%MOD);
			hashCode = (int) ((hashCode + card.hashCode())%MOD);
		}
		return hashCode;
	}
}
