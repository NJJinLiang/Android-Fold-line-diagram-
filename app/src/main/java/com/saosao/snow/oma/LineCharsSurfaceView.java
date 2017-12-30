package com.saosao.snow.oma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 19780 on 2017/12/30.
 */

public class LineCharsSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    SurfaceHolder mSurfaceHolder;
    Canvas canvas;
    private String[] xArray = {};//x轴
    private float[] yArray = {};//y轴
    int xsize;
    private int cha = 150;
    Paint p = new Paint();
    Paint textPaint = new Paint();
    Paint linePaint = new Paint();

    List<LinkedHashMap<String ,Integer>> lineList = new ArrayList<>();
    List<Integer> LinecolorList = new ArrayList<>();
    List<Integer> LinealphaList = new ArrayList<>();
    List<String> LinenameList = new ArrayList<>();
    private int height,width;
    boolean isDrawing = false;
    int x =0;
    int y =0;
    int oneheight;
    int onewidth;
    public void setXY(String[] x, float[] y , int xsize) {
        xArray = x;
        yArray = y;
        this.xsize = xsize;
    }
    public void setXY(String[] x, float yMax , int ySize , int xsize) {
        xArray = x;
        yArray = new float[ySize+1];
        for(int i = 0 ; i <= ySize ; i++){
            yArray[i] = i * yMax / ySize;
        }
        this.xsize = xsize;
    }
    public void addLine(LinkedHashMap<String ,Integer> map,int backColor , int alpha , String name){
        lineList.add(map);
        LinecolorList.add(backColor);
        LinealphaList.add(alpha);
        LinenameList.add(name);
    }
    public void setXYColor(int color){
        p.setColor(color);
    }
    public void setTextColor(int color){
        textPaint.setColor(color);
    }
    public void setTextSize(int size){
        textPaint.setTextSize(size);
    }
    public void setCha(int cha){
        this.cha=cha;
    }

    private void init() {
        p.setAntiAlias(true);
        p.setColor(Color.parseColor("#686868"));
        p.setStrokeWidth(5);

        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#686868"));
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);

        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(5);

        mSurfaceHolder =getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }
    public LineCharsSurfaceView(Context context) {
        super(context);
        init();
    }

    public LineCharsSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineCharsSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LineCharsSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        createView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing){
            createView();
        }
    }
    private void createView(){
        try {
            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            height = getHeight();
            width = getWidth();
            //画坐标轴
            canvas.drawLine(cha, cha/2, cha, height - cha, p);// 画Y轴
            canvas.drawLine(cha, height - cha, width-cha/2, height - cha, p);// 画X轴
            //画箭头
            canvas.drawLine(cha-cha/6 ,cha*0.7f , cha, cha/2,p);
            canvas.drawLine(cha+cha/6 ,cha*0.7f , cha, cha/2,p);
            canvas.drawLine(width-cha/2-cha/6,height - cha-cha/6,width-cha/2, height - cha,p);
            canvas.drawLine(width-cha/2-cha/6,height - cha+cha/6,width-cha/2, height - cha,p);

            oneheight = (height-2*cha) / (yArray.length-1);
            onewidth = (width-2*cha) / xsize;

            drawing();
        }finally {
            if(canvas !=null){
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawing(){
        //画xy轴字
        for (int i = 0; i < xArray.length; i++) {
            canvas.drawText(xArray[i], cha + (i * onewidth)+x, height - cha + 100+y, textPaint);
            canvas.drawLine(cha + (i * onewidth)+x, height - cha+y, cha + (i * onewidth)+x, height - cha + 25+y , textPaint);
        }
        textPaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < yArray.length; i++) {
            canvas.drawText((int)yArray[i]+"" ,10,height-cha-(i*oneheight)+10,textPaint);
            canvas.drawLine(cha-25 ,height-cha-(i*oneheight), cha , height-cha-(i*oneheight), textPaint);
        }
        textPaint.setTextAlign(Paint.Align.CENTER);

        //画折线
        for(int i = 0 ; i < lineList.size() ;i++){
            linePaint.setColor(LinecolorList.get(i));
            linePaint.setAlpha(LinealphaList.get(i));
            linePaint.setAntiAlias(true);
            LinkedHashMap<String,Integer> map = lineList.get(i);
            Path path = new Path();  // 路径对象
            Set<Map.Entry<String, Integer>> mapValues = map.entrySet();
            int maplength = mapValues.size();
            Map.Entry<String,Integer>[] test = new Map.Entry[maplength];
            mapValues.toArray(test);
            path.moveTo(getPostion(test[test.length-1].getKey())*onewidth+cha + x, test[test.length-1].getValue()/yArray[1]*oneheight + y);// 此点为多边形的起点
            path.lineTo(getPostion(test[test.length-1].getKey())*onewidth+cha + x, height-cha + y);
            path.lineTo(cha + x, height-cha + y);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if(getPostion(entry.getKey()) == -1)continue;
                path.lineTo(getPostion(entry.getKey())*onewidth+cha  + x, height -(entry.getValue()/yArray[1]*oneheight)-cha + y);
                canvas.drawText(entry.getValue()+"" , getPostion(entry.getKey())*onewidth+cha + x ,height -(entry.getValue()/yArray[1]*oneheight)-cha-15 + y , textPaint);
            }
            path.close(); // 使终点和起点链接，构成封闭图形
            canvas.drawPath(path, linePaint);
            //画代表折线的格子
            float xx = cha*2 + i*300;
            canvas.drawRect(xx ,cha/2 ,xx+cha,cha/2+cha/2 , linePaint);
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(LinenameList.get(i) ,xx+cha,cha/2+cha/2 ,textPaint );
            textPaint.setTextAlign(Paint.Align.CENTER);
        }
        //画数值
//        for(int i = 0 ; i < lineList.size() ;i++){
//            LinkedHashMap<String,Integer> map = lineList.get(i);
//            for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                if(getPostion(entry.getKey()) == -1)continue;
//                canvas.drawText(entry.getValue()+"" , getPostion(entry.getKey())*onewidth+cha ,height -(entry.getValue()/yArray[1]*oneheight)-cha-15 , textPaint);
//            }
//        }
    }
    private int getPostion(String str){
        int postion = -1;
        for(int i = 0 ;i < xArray.length ;i++){
            if(xArray[i].equals(str)){
                postion = i;
                break;
            }
        }
        return postion;
    }

    float beforex;
    float beforey;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                beforex = event.getX();
                beforey = event.getY();
                isDrawing = true;
                new Thread(this).start();
                break;
            case MotionEvent.ACTION_MOVE:
                float tt = event.getX() - beforex;
                x += tt;
//                    y += event.getY() - beforey;
//                    if(Math.abs(x) > Math.abs(y)){
//                        y -= event.getY() - beforey;
//                    }else{
//                        x -= event.getX() - beforex;
//                    }

                    beforex = event.getX();
                beforey = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                break;
        }
        return true;
    }
}
