package com.example.donald.canvasesss;

import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Donald on 19.04.2016.
 */
public class Graph {
    Vector<MyPoint> points;
    Vector<Line> lines;

    int lastPoint = -1;

    Vector<Integer> sorted;

    public Graph(){
        points = new Vector<>();
        lines = new Vector<>();
    }

    public Graph(Vector<MyPoint> points, Vector<Line> lines){
        this.points = points;
        this.lines = lines;
        lastPoint = 0;
    }

    public void addPoint(MyPoint point){
        points.add(point);

        if(lastPoint != -1)
            lines.add(new Line(points.elementAt(lastPoint), point));

        lastPoint = points.size()-1;
    }

    public void clearPoints(){
        points.clear();
        lines.clear();
        lastPoint = -1;
    }

    public MyPoint inPointsAndSetLastPoint(int x, int y){
        for(MyPoint point : points)
            if(point.isPoint(x, y)) {
                lastPoint = points.indexOf(point);
                return point;
            }
        return null;
    }

    public MyPoint inPointsWithoutLast(int x, int y){
        for(MyPoint point : points)
            if(point.isPoint(x, y) && point != lastPoint())
                return point;
        return null;
    }

    public MyPoint inPoints(int x, int y){
        for(MyPoint point : points)
            if(point.isPoint(x, y))
                return point;
        return null;
    }

    public void draw(Canvas canvas){
        for(MyPoint point : points)
            point.Draw(canvas);

        for(Line line : lines)
            line.Draw(canvas);
    }

    public void concat(MyPoint point1, MyPoint point2){
        for(int i = lines.size() - 1; i >= 0; --i){
            if(lines.elementAt(i).Equal(new Line(point1, point2)))
                lines.remove(i);
            else {
                if (lines.elementAt(i).Start() == point1)
                    lines.elementAt(i).Start(point2);
                if (lines.elementAt(i).End() == point1)
                    lines.elementAt(i).End(point2);
            }
        }

        points.remove(point1);

        lastPoint = points.indexOf(point2);

    }

    public void concatWithLastPoint(MyPoint point){
        concat(point, lastPoint());
    }

    public MyPoint elementAt(int index){
        return points.elementAt(index);
    }

    public MyPoint lastPoint(){
        return points.elementAt(lastPoint);
    }

    public void lastPoint(MyPoint point){
        lastPoint = points.indexOf(point);
    }

    private Vector<Line> sortLines(MyPoint in_point){
        Vector<Line> lineVector = new Vector<>();
        Vector<MyPoint> myPoints = new Vector<>();

        for(Line line : lines){
            MyPoint point = line.contains(in_point);
            if(point != null && !lineVector.contains(line) && !myPoints.contains(point)) {
                lineVector.add(line);
                myPoints.add(point);
            }
        }
        for(MyPoint p : myPoints)
            lineVector.addAll(sortLines(p));

        return lineVector;

    }



    public void sort(){
        sortPoints();
    }

    private void sortPoints(){
        sorted = new Vector<>();
        sorted.add(lastPoint);
        Log.d("pop", lastPoint + "");
        int index = 0;
        for(MyPoint p : points){
            findChild(sorted.elementAt(index));
            Log.d("p", sorted.toString());
            index++;
        }
    }

    private void findChild(Integer index) {
        MyPoint point = points.elementAt(index);

        for(Line line : lines){
            MyPoint p = line.contains(point);
            if(p != null && !sorted.contains(points.indexOf(p))) {
                sorted.add(points.indexOf(p));
                Log.d("sd", "add"+points.indexOf(p));
            }
        }
    }

}
