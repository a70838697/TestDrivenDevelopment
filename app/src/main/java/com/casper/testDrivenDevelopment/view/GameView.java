package com.casper.testDrivenDevelopment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.casper.testDrivenDevelopment.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private int xTouch=-1,yTouch=-1,count=0;

    public GameView(Context context) {
        super(context);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        sprites.add(new Sprite(R.drawable.book_icon));
        sprites.add(new Sprite(R.drawable.book_icon));
        sprites.add(new Sprite(R.drawable.book_icon));
        sprites.add(new Sprite(R.drawable.book_icon));
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (null == drawThread) {
            drawThread = new DrawThread();
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (null != drawThread) {
            drawThread.stopThread();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float npx = event.getX();
        float npy = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                this.xTouch=(int)npx;
                this.yTouch=(int)npy;
                break;
        }
        return true;
    }


    private class DrawThread extends Thread {
        private boolean beAlive = false;

        public void stopThread() {
            beAlive = false;
            while (true) {
                try {
                    this.join();//保证run方法执行完毕
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void run() {
            beAlive = true;
            while (beAlive) {
                Canvas canvas = null;
                try {
                    synchronized (surfaceHolder) {
                        canvas = surfaceHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);

                        Paint paint = new Paint();
                        paint.setTextSize(50);
                        paint.setColor(Color.BLACK);
                        canvas.drawText("击中"+count+"个", 40, 40, paint);
                        for (int i = 0; i < sprites.size(); i++) sprites.get(i).move();
                        for (int i = 0; i < sprites.size(); i++) sprites.get(i).draw(canvas);

                        GameView.this.xTouch=-1;
                        GameView.this.yTouch=-1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != canvas) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Sprite {
        private int resouceId;
        private int x, y;
        private double direction;

        public Sprite(int resouceId) {
            this.resouceId = resouceId;
            RandomInit();
        }

        private void RandomInit() {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
            direction = Math.random() * 2 * Math.PI;
        }

        public void move() {
            Drawable drawable = getContext().getResources().getDrawable(this.resouceId);
            Rect drawableRect = new Rect(x, y, x + drawable.getIntrinsicWidth(), y + drawable.getIntrinsicHeight());
            if(drawableRect.contains(GameView.this.xTouch,GameView.this.yTouch))
            {
                GameView.this.count++;
                RandomInit();
            }
            else {

                x += 15 * Math.cos(direction);
                if (x < 0) x = getWidth();
                else if (x > getWidth()) x = 0;
                y += 15 * Math.sin(direction);
                if (y < 0) y = getHeight();
                else if (y > getHeight()) y = 0;
                if (Math.random() < 0.05) direction = Math.random() * 2 * Math.PI;
            }

        }

        public void draw(Canvas canvas) {
            Drawable drawable = getContext().getResources().getDrawable(this.resouceId);
            Rect drawableRect = new Rect(x, y, x + drawable.getIntrinsicWidth(), y + drawable.getIntrinsicHeight());
            drawable.setBounds(drawableRect);
            drawable.draw(canvas);
        }
    }
}
