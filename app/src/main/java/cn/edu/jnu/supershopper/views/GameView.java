package cn.edu.jnu.supershopper.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import cn.edu.jnu.supershopper.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private float touchedX;
    private float touchedY;
    private boolean isTouched=false;
    private int hitmouseCount=0;
    private int hitZhadanCount=0;
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public GameView(Context context) {
        super(context);
        initView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    //surfaceHolder
    private void initView(){
        surfaceHolder=getHolder();
        setBackgroundResource(R.drawable.background);
        setZOrderOnTop(true);//使surfaceview放到最顶层
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        this.getHolder().addCallback(this);
    }
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread=null;

    private int addscore=0;
    private int subscore=0;
    private ArrayList<Spriter> spriterArrayList=new ArrayList<>();
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        int[][] position=new int[][]{
                {100,1100},{400,1100},{700,1100},
                {300,700},{100,700},{500,700},{700,700},
                {80,400}, {200,400},{400,400},{700,400},{550,400}
        };//各个地鼠坑的位置
        for(int i=0;i<2;i++){
            int pos=new Random().nextInt(position.length);
            Spriter spriter=new Spriter(this.getContext());
            spriter.setX(position[pos][0]);
            spriter.setY(position[pos][1]);
            spriterArrayList.add(spriter);
        }
        drawThread=new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        drawThread.stopThread();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchedX=event.getRawX();
        touchedY=event.getRawY();
        isTouched=true;
        return true;
    }

    class DrawThread extends Thread{
        private boolean isDrawing=true;

        public void stopThread(){
            isDrawing=false;
            try {
                join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        @Override
        public  void run(){
            while (isDrawing){
                Canvas canvas=null;
                try{
                    canvas=surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    Paint textPaint=new Paint();
                    textPaint.setColor(Color.RED);
                    textPaint.setTextSize(40);
                    while(isTouched){
                        float tempX=touchedX;
                        float tempY=touchedY;
                        isTouched=false;
                        for(Spriter spriter:spriterArrayList){
                            if(spriter.isTouched(tempX,tempY)){
                                if(spriter.getDrawableResourceId()==R.drawable.mouse){
                                    hitmouseCount+=1;
                                }
                                if(spriter.getDrawableResourceId()==R.drawable.zhadan){
                                    hitZhadanCount+=1;
                                }
                            }
                        }
                        canvas.drawText("你击中了"+hitmouseCount+"只地鼠！你击中了"+hitZhadanCount+"只炸弹！",40,40,textPaint);
                    }

                    for(Spriter spriter:spriterArrayList){

                        spriter.move(canvas.getHeight(),canvas.getWidth());
                    }

                    for(Spriter spriter:spriterArrayList){
                        spriter.draw(canvas);
                    }
                }finally {
                    if(null!=canvas){
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

    }


}
