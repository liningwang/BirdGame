package com.example.birdgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Pipe {

	private static final int PIPE_WIDTH = 30;
	private float height;
	private static final float PIPE_MAX_HEIGHT = 2.0f / 5; 
	private static final float PIPE_MIN_HEIGHT = 1.0f / 5;
	private static final float PIPE_MARGIN = 1.0f / 5;
	RectF rect = new RectF();
	Random ran = new Random();
	private float x;
	private float margin;
	private int speed;
	private Bitmap mTop;
	private Bitmap mBottom;
	private int gameHeight;
	int width;
	public Pipe(Context context,int w,int h,Bitmap top,Bitmap bottom) {
		// TODO Auto-generated constructor stub
		height = ran.nextInt((int)(h * PIPE_MAX_HEIGHT));
		height = height + h * PIPE_MIN_HEIGHT;
		x = w;
		margin = h * PIPE_MARGIN;
		mTop = top;
		width = mTop.getWidth();
		gameHeight = h;
		mBottom = bottom;
	}
	public void draw(Canvas canvas,Paint paint){
		x = x - speed;
		canvas.save();
		rect.set(0,0,PIPE_WIDTH,mTop.getHeight());
		canvas.translate(x, -(mTop.getHeight()-height));
		canvas.drawBitmap(mTop, null, rect, paint);
		
		canvas.translate(0, (mTop.getHeight()-height) + height + margin);
		rect.set(0,0,PIPE_WIDTH,mBottom.getHeight());
		canvas.drawBitmap(mBottom, null, rect, paint);
		canvas.restore();
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getX(){
		return x;
	}
	public int getWidth(){
		return width;
	}
	public float getTopHeight(){
		return height;
	}
	public float getMargin(){
		return margin;
	}
}
