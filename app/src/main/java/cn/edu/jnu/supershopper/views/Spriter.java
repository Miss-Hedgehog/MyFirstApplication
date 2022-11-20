package cn.edu.jnu.supershopper.views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import cn.edu.jnu.supershopper.R;
public class Spriter{
    float x,y;
    int drawableResourceId;
    float direction;
    private Context context;
    int[][] position=new int[][]{
            {100,1100},{400,1100},{700,1100},
            {300,700},{100,700},{500,700},{700,700},
            {80,400}, {200,400},{400,400},{700,400},{550,400}
    };
    public Spriter(Context context) {
        this.context = context;
    }
    public void move(float maxHeight, float maxWidth) {

        int pos=new Random().nextInt(position.length);//随机设置位置信息
        this.setX(position[pos][0]);
        this.setY(position[pos][1]);
    }
    public void draw(Canvas canvas){
        Bitmap bitmap;
        if(Math.random()<0.1){
            bitmap=((BitmapDrawable)context.getResources().getDrawable(R.drawable.zhadan)).getBitmap();//添加炸弹
            this.setDrawableResourceId(R.drawable.zhadan);
        }else{
            bitmap=((BitmapDrawable)context.getResources().getDrawable(R.drawable.mouse)).getBitmap();//地鼠图片
            this.setDrawableResourceId(R.drawable.mouse);
        }
        Paint mBitPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        canvas.drawBitmap(bitmap,getX(),getY(),mBitPaint);

    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getDrawableResourceId() {
        return drawableResourceId;
    }

    public void setDrawableResourceId(int drawableResourceId) {
        this.drawableResourceId = drawableResourceId;
    }
    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
    public interface SperitClickLinster {
        void onClick(Spriter spriter);
    }
    private SperitClickLinster clickLinster;
    public void setClickLinster(SperitClickLinster clickLinster) {
        this.clickLinster = clickLinster;
    }

}
