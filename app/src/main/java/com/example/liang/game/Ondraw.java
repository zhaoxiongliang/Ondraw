package com.example.liang.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by liang on 2017/4/21.
 */

public class Ondraw extends SurfaceView implements Runnable,SurfaceHolder.Callback {

    public Ondraw(Context context) {
        super(context);
        getHolder().addCallback(this);
        gamebitmap=Bitmap.createBitmap(1000,1000, Bitmap.Config.ARGB_8888);

    }

    private boolean runState=false;
    private SurfaceHolder holder=null;
    private Bitmap gamebitmap=null;

    @Override
    public void run() {
        Random ran=new Random();
    try {
        while (true){
            //获得绘画
            Canvas canvas=holder.lockCanvas();

            Paint p=new Paint();

            Canvas c=new Canvas(gamebitmap);

           // p.setColor(Color.WHITE);
          //  c.drawRect(new Rect(0,0,500,500),p);
            //重刷白色背景

            p.setColor(Color.rgb(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
            c.drawLine(ran.nextInt(1000),ran.nextInt(1000),ran.nextInt(1000),ran.nextInt(1000),p);

            canvas.drawBitmap(gamebitmap,0,0,new Paint());
            //把绘画的内容提交上去
            holder.unlockCanvasAndPost(canvas);
            Thread.sleep(100);
        }
    }catch (Exception e){
        Log.e("APP,TAG","异常",e);
    }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder=holder;
        runState=true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("APP,TAG","sufachange");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("APP,TAG","sufades");
        runState=false;

    }


}
