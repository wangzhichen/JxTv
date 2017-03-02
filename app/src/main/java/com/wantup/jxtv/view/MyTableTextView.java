package com.wantup.jxtv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wantup on 2017/1/12.
 */

public class MyTableTextView extends TextView{
    private Paint paint;

    public MyTableTextView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.parseColor("#80b9f2"));
    }

    public MyTableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.parseColor("#80b9f2"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);//左右
        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);//左下
        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);//右下
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);//下右
    }
}
