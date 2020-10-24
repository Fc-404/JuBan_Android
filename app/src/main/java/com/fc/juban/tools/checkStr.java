package com.fc.juban.tools;

public class checkStr {
    public static boolean checkUserPwd(String user, String pwd) {
        if (user.matches("[a-zA-Z0-9_.+\\u4e00-\\u9fbb]{1,16}")
                && pwd.matches("[a-zA-Z0-9_.+]{6,16}"))
            return true;
        return false;
    }

    public static boolean checkNeed(String str) {
        if (str.matches("[a-zA-Z0-9_.+\\u4e00-\\u9fbb]{1,21}"))
            return true;
        return false;
    }

    public static boolean checkLeave(String str) {
        if (str.matches("[a-zA-Z0-9_.+，,。！!‘“、@￥%^&*（）【】？?;；\\u4e00-\\u9fbb]{1,21}"))
            return true;
        return false;
    }
}
