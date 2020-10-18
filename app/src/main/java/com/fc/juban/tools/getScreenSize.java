package com.fc.juban.tools;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.app.Activity;
import android.view.WindowManager;

public class getScreenSize {
    Resources resources;
    Point point = new Point();
    Activity win;

    public getScreenSize(Activity win){
        //App尺寸
        Display defaultDisplay = win.getWindowManager().getDefaultDisplay();
        defaultDisplay.getSize(point);
        resources = win.getResources();
        this.win = win;
    }

    public Point getAppSize(){
        //底部导航栏
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        point.y -= height;
        return point;
    }
}
