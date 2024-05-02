package ir.sharif.utils;

public class Random {
    public static int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static String getRandomPassword() {
        return null;
    }
}
