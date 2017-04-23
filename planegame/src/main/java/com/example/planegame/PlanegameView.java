package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2017/4/23.
 */


public class PlanegameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private Bitmap hero;        //自己
    private Bitmap background;  //背景
    private Bitmap enemy;       //敌人
    private Bitmap explode;     //爆炸
    private Bitmap bullet;      //子弹
    private int display_w;      //屏幕宽
    private int display_h;      //屏幕高
    private List<GameImage> gameImages=new ArrayList();     //图片数组
    private Bitmap erjihuancun;     //二级缓存

    public PlanegameView(Context context) {
        super(context);
        getHolder().addCallback(this);          //回调


    }

    public void init(){                         //加载图片
        hero= BitmapFactory.decodeResource(getResources(),R.drawable.hero1);
        enemy=BitmapFactory.decodeResource(getResources(),R.drawable.enemy1);
        background=BitmapFactory.decodeResource(getResources(),R.drawable.background);
        explode=BitmapFactory.decodeResource(getResources(),R.drawable.enemy1_down1);
        bullet=BitmapFactory.decodeResource(getResources(),R.drawable.bullet1);

        erjihuancun=Bitmap.createBitmap(display_w,display_h, Bitmap.Config.ARGB_8888);

        gameImages.add(new backgroundImage(background));
    }

    private interface GameImage{
        public Bitmap getBitmap();
        public int getX();
        public int getY();
    }

    //处理背景照片
    private class backgroundImage implements GameImage{
        private Bitmap bg;
        private Bitmap newBitmap=null;
        private int height=0;

        private backgroundImage(Bitmap bg){
            this.bg=bg;
            newBitmap=Bitmap.createBitmap(display_w,display_h, Bitmap.Config.ARGB_8888);

        }

        public Bitmap getBitmap(){

            Paint p=new Paint();
            Canvas canvas=new Canvas(newBitmap);

            canvas.drawBitmap(bg,new Rect(0,0,bg.getWidth(),bg.getHeight())     //初始背景照片位置
                    ,new Rect(0,height,display_w,display_h+height),p);

            canvas.drawBitmap(bg,new Rect(0,0,bg.getWidth(),bg.getHeight())     //滚动下一个背景的位置
                    ,new Rect(0,-display_h+height,display_w,height),p);
            height++;
            if(height==display_h){
                height=0;
            }
            return newBitmap;

        }

        @Override
        public int getX() {

            return 0;
        }

        @Override
        public int getY() {

            return 0;
        }
    }

    private boolean state=false;
    private SurfaceHolder holder;

    //绘画方法
    @Override
    public void run() {
        Paint p1=new Paint();
        try {
            while (state){
                Canvas canvas= holder.lockCanvas();
                Canvas newCanvas=new Canvas(erjihuancun);

                for(GameImage image:gameImages){
                    newCanvas.drawBitmap(image.getBitmap(),image.getX(),image.getY(),p1);
                }

                canvas.drawBitmap(erjihuancun,0,0,p1);
                holder.unlockCanvasAndPost(canvas);
                Thread.sleep(10);
            }
        }catch (Exception e){

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        display_w=width;
        display_h=height;
        init();
        this.holder=holder;
        state=true;
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        state=false;
    }


}
