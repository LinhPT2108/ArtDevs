package com.art.utils;

public class validUtil {
    public static boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }

    public static boolean containsSpecialCharacters(String str) {
        return str.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
