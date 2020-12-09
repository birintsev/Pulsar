package org.example.data;

import java.util.Random;

public class RandomData {
    private static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String generateString(){
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
    public static String generatePass(int length){
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
    public static int generateAge(){
        return (int)Math.round(Math.random()*30)+16;
    }
}
