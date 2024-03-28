package com.softices;

import java.util.regex.Pattern;

public class Utils {
    static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

}
