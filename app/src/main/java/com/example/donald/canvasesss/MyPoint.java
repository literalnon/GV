package com.example.donald.canvasesss;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Vector;

/**
 * Created by Donald on 19.04.2016.
 */
public class MyPoint {
    int x;
    int y;

    Paint pointPaint;

    int pointColor = Color.BLACK;

    public MyPoint(int x, int y){
        this.x = x;
        this.y = y;

        pointPaint = new Paint();
        pointPaint.setColor(pointColor);
    }

    public void Draw(Canvas canvas){
        //pointPaint.setStrokeWidth(MainActivity.POINTS_WIDTH * DrawView.scale);

        canvas.drawCircle(x, y, MainActivity.POINTS_WIDTH * DrawView.scale, pointPaint);
    }

    public boolean isPoint(int x, int y){
        return (x -  MainActivity.POINTS_WIDTH * DrawView.scale  < this.x) && (x + MainActivity.POINTS_WIDTH * DrawView.scale > this.x) &&
                (y - MainActivity.POINTS_WIDTH * DrawView.scale < this.y) && (y + MainActivity.POINTS_WIDTH * DrawView.scale > this.y);
    }

    public void setColorPoint(int color){
        pointPaint.setColor(color);
    }

    public int getPointColor(){
        return pointColor;
    }

    public boolean equals(MyPoint point){
        return point.x == this.x && point.y == this.y;
    }
}
