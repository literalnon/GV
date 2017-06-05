package com.example.donald.canvasesss;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Donald on 16.05.2017.
 */
public class Line {
    private MyPoint start_point, end_point;
    private Paint linePaint;
    private int lineColor = Color.GRAY;

    public Line(MyPoint point1, MyPoint point2){
        start_point = point1;
        end_point = point2;

        linePaint = new Paint();
        linePaint.setColor(lineColor);
    }

    public void Draw(Canvas canvas){
        linePaint.setStrokeWidth(MainActivity.POINTS_WIDTH / 2 * DrawView.scale);
        canvas.drawLine(start_point.x, start_point.y, end_point.x, end_point.y, linePaint);
    }

    public boolean Equal(Line other_line){
        return (this.end_point == other_line.end_point &&
                this.start_point == other_line.start_point) ||
                (this.end_point == other_line.start_point &&
                        this.start_point == other_line.end_point);
    }

    public int LineColor(){
        return lineColor;
    }
    public void LineColor(int color){
        linePaint.setColor(color);
    }

    public MyPoint Start(){return start_point;}
    public void Start(MyPoint point){start_point = point;}

    public MyPoint End(){return end_point;}
    public void End(MyPoint point){end_point = point;}

    public MyPoint contains(MyPoint point){
        if(this.Start() == point)
            return this.End();
        if(this.End() == point)
            return this.Start();
        return null;
    }

}
