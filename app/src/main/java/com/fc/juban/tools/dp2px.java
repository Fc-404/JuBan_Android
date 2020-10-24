package com.fc.juban.tools;

import com.fc.juban.main_tree;

public class dp2px {
    public static int dp(int dp){
        final float scale =
                main_tree.main_tree.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
