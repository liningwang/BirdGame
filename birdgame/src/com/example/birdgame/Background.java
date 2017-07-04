package com.example.birdgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;

public class Background {

	private int mGameWidth;
	private int mGameHeight;
	private static final float FLOOR_Y_POS = 4/5f;
	private int x = 0;
	int y = 0;
	private Bitmap bm;
	private int speed;
	private BitmapShader mBgShader;
	public Background(int gameWidth,int gameHeight,Bitmap bg) {
		// TODO Auto-generated constructor stub
		mGameHeight = gameHeight;
		mGameWidth = gameWidth;
		bm = bg;
	
		mBgShader = new BitmapShader(bg, TileMode.REPEAT, TileMode.CLAMP);
	}
	public void draw(Canvas canvas,Paint paint){
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		y = (int) (mGameHeight * FLOOR_Y_POS);
		if(x > mGameWidth) {
			x = 0;
		}
		canvas.translate(-x, y);
		
		
		paint.setShader(mBgShader);
		//canvas移动，Shader里面的bitmap一起跟着移动。也就是说整个坐标系移动了
		canvas.drawRect(x, 0, x + mGameWidth, mGameHeight - y,paint);
//		canvas.drawBitmap(bm, x,0, paint);
		x = x + speed; 
		canvas.restore();
		paint.setShader(null);
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
