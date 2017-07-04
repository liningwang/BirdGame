package com.example.birdgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Bird {
	
private int mGameWidth;
private int mGameHeight;
private boolean jump = false;
private int mBirdWidth;
private int mBirdHeight;
float h;
float value = 0;
float a = 0.0f;
private RectF rect = new RectF();
private float x,y;
private Bitmap bird;
public Bird(Context context,int gameWidth,int gameHeight,Bitmap bt) {
	// TODO Auto-generated constructor stub
	this.mGameHeight = gameHeight;
	this.mGameWidth = gameWidth;
	mBirdWidth = Util.dp2px(context, 20);
	mBirdHeight = Util.dp2px(context, mBirdWidth*1.0f / gameWidth * gameHeight);
	bird = bt;
}
public void draw(Canvas canvas,Paint paint){
	
	y = y - h;
	if(y >= mGameHeight) {
		y = mGameHeight;
	}
	rect.set(x, y, x + mBirdWidth, y + mBirdHeight);
	canvas.drawBitmap(bird, null, rect, paint);
	h = h - 2;
}
public void setX(float x){
	this.x = x;
}
public float getX(){
	return x;
}
public void setY(float y){
	this.y = y;
}
public float getY(){
	return y;
}
public void jump(float h){
	this.h = h;
}
}
