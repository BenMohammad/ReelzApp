package com.benmohammad.reelz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class NumberedEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;
    final NumberedEditText numberedEditText;

    public NumberedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.numberedEditText = this;
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setTextSize(24);
        setHorizontallyScrolling(false);
        setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLine = getBaseline();
        for(int i = 0; i < getLineCount(); i++) {
            canvas.drawText(String.format(" %d  ", (i + 1)), rect.left, baseLine, paint );
            baseLine += getLineHeight();
        }
    }
}
