package com.fc.juban.tools;

public class checkUserPwd {
    public static boolean check(String user, String pwd) {
        if (user.matches("[a-zA-Z0-9_.+\\u4e00-\\u9fbb]{1,16}")
                && pwd.matches("[a-zA-Z0-9_.+]{6,16}"))
            return true;
        return false;
    }
}
