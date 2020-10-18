package com.fc.juban.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    float width,height;
    private int round = 12;
    private boolean[] location = {false, false, false, false};

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > round && height > round) {
            Path path = new Path();
            //top left
            if (location[0] == false)
                path.moveTo(0, 0);
            else
                path.moveTo(round, 0);
            //top right
            if (location[1] == false)
                path.lineTo(width, 0);
            else {
                path.lineTo(width - round, 0);
                path.quadTo(width, 0, width, round);
            }
            //bottom right
            if (location[2] == false)
                path.lineTo(width, height);
            else {
                path.lineTo(width, height - round);
                path.quadTo(width, height, width - round, height);
            }
            //bottom left
            if (location[3] == false)
                path.lineTo(0, height);
            else {
                path.lineTo(round, height);
                path.quadTo(0, height, 0, height - round);
            }
            //return
            if (location[0] == false)
                path.lineTo(0, 0);
            else {
                path.lineTo(0, round);
                path.quadTo(0, 0, round, 0);
            }
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }

    //
    public void setRound(int round){
        this.round = round;
    }
    //
    public boolean setLocation(boolean[] location){
        if (location.length != 4)
            return false;
        for (int i = 0; i < 4; i++)
            this.location[i] = location[i];
        return true;
    }
}