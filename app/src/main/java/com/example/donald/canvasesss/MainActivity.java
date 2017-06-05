package com.example.donald.canvasesss;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;


public class MainActivity extends Activity {
    static Graph graph;
    static int POINTS_WIDTH = 3;
    static int elem_at = 0;
    static DrawView drawView;
    static ListView graphsList;

    static int index = 0;
    //static Vector<MyPoint> points = new Vector<>();
    final Handler handler = new Handler();
    //static boolean time_tapped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawView) findViewById(R.id.main_draw_view);
        graphsList = ((ListView) findViewById(R.id.lvGraphs));
    }

    public void pictureAlgorithm(View view){
        TextView textView = (TextView) findViewById(R.id.tvInfo);
        graph.sort();
        index = 0;
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(graph.points.size() <= index)
                handler.removeCallbacks(runnable);
            else {
                graph.points.elementAt(graph.sorted.elementAt(index++)).setColorPoint(Color.RED);
                handler.postDelayed(runnable, 1000);
                drawView.invalidate();
            }
        }
    };

    public void clearPoints(View view){
        graph.clearPoints();
        drawView.invalidate();
    }

    public void plusScale(View view){
        DrawView.scale += DrawView.scale_step;
        drawView.invalidate();
    }

    public void minusScale(View view){
        DrawView.scale -= DrawView.scale_step;
        drawView.invalidate();
    }

    public void saveGraph(View view){

        SQLiteDatabase database = new DBGraphs(this).getWritableDatabase();
        //database.beginTransaction();
        Cursor cursor = database.query(DBGraphs.NAME_TABLE, new String[]{"max("+DBGraphs.NAME_COLLUMN_GRAPH_ID+")"},
                null,null,null,null,null);

        int id = 1;
        if (cursor.moveToNext()) {
            id = cursor.getInt(0) + 1;
        }

        ContentValues contentValues_lines = new ContentValues();

        for (Line line : graph.lines) {
            contentValues_lines.put(DBGraphs.NAME_COLLUMN_X_START, line.Start().x);
            contentValues_lines.put(DBGraphs.NAME_COLLUMN_Y_START, line.Start().y);
            contentValues_lines.put(DBGraphs.NAME_COLLUMN_X_END, line.End().x);
            contentValues_lines.put(DBGraphs.NAME_COLLUMN_Y_END, line.End().y);
            contentValues_lines.put(DBGraphs.NAME_COLLUMN_GRAPH_ID, id);

            database.insert(DBGraphs.NAME_TABLE, null, contentValues_lines);
        }

        //database.delete(DBGraphs.NAME_TABLE, null, null);
        //database.endTransaction();
        Toast.makeText(this, "save Graph", Toast.LENGTH_SHORT).show();
    }

    public void loadGraph(View view){
        SQLiteDatabase database = new DBGraphs(this).getReadableDatabase();
        //database.beginTransaction();
        Cursor cursor = database.query(DBGraphs.NAME_TABLE, null, null, null, null, null, null);

        Vector<Line> lines = new Vector<>();
        Vector<MyPoint> points = new Vector<>();
        Vector<Graph> graphs = new Vector<>();

        int id = 1;

        while (cursor.moveToNext()) {

            if(id != cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_GRAPH_ID))) {
                graphs.add(new Graph(points, lines));
                lines = new Vector<>();
                points = new Vector<>();
            }

            MyPoint startPoint = new MyPoint(cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_X_START)),
                    cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_Y_START)));

            MyPoint newPoint = contains(points, startPoint);
            if (newPoint == startPoint)
                points.add(startPoint);
            else
                startPoint = newPoint;

            MyPoint endPoint = new MyPoint(cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_X_END)),
                    cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_Y_END)));

            newPoint = contains(points, endPoint);
            if (newPoint == endPoint)
                points.add(endPoint);
            else
                endPoint = newPoint;

            lines.add(new Line(startPoint, endPoint));

            Log.d("H", cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_GRAPH_ID)) + "");

            id  = cursor.getInt(cursor.getColumnIndex(DBGraphs.NAME_COLLUMN_GRAPH_ID));

        }

        graphs.add(new Graph(points, lines));
        //database.endTransaction();
        GraphsAdapter graphsAdapter = new GraphsAdapter(this, graphs);
        graphsList.setAdapter(graphsAdapter);
        graphsList.setVisibility(View.VISIBLE);
    }

    private MyPoint contains(Vector<MyPoint> points, MyPoint point){
        for(MyPoint myPoint : points){
            if(point.equals(myPoint))
                return myPoint;
        }
        return point;
    }
}
