package com.example.donald.canvasesss;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Donald on 14.05.2017.
 */

public class DrawView extends View {
    Context context;
    static int scale = 10, scale_step = 5;

    public DrawView(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        this.context = context;
        MainActivity.graph = new Graph();
    }

    public DrawView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.context = context;
        MainActivity.graph = new Graph();
    }

    public DrawView(Context context){
        super(context);
        this.context = context;
        MainActivity.graph = new Graph();
    }

    public void onDraw(Canvas canvas){
        MainActivity.graph.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int m_x = (int) motionEvent.getX(), m_y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(MainActivity.graph.inPointsAndSetLastPoint(m_x, m_y) == null)
                    MainActivity.graph.addPoint(new MyPoint(m_x, m_y));
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.graph.lastPoint().x = m_x;
                MainActivity.graph.lastPoint().y = m_y;
                break;
            case MotionEvent.ACTION_UP:
                MyPoint t_point = MainActivity.graph.inPointsWithoutLast(m_x, m_y);
                if (t_point != null){
                    MainActivity.graph.concatWithLastPoint(t_point);
                }
                break;
        }

        invalidate();
        return true;
    }
}
