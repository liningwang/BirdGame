package com.example.birdgame;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("NewApi") public class GameView extends SurfaceView implements Callback, Runnable {

	
	SurfaceHolder mHolder;
	Canvas mCanvas;
	Thread th;
	int x;
	Bitmap bm;
	Context context;
	Background bg;
	int mGameWidth;
	int mGameHeight;
	Paint mPaint;
	Bitmap backg;
	Bitmap bd;
	Bitmap top;
	Bitmap bottom;
	Rect destRect;
	Bird bird;
	Pipe pipe;
	
	private static final int SPEED = 5;
	private static final float BIRD_INIT_HEIGHT = 2.0f/3;
	private static final float BIRD_INIT_WIDTH = 1.0f/2;
	private static final float BIRD_INIT_DISTANCE = 4.0f/5;
	private static final float BIRD_JUMP = 16.0f;
	boolean failed = false;
	int buttonX = 100;
	int buttonY = 100;
	int buttonW = 100;
	int buttonH = 50;
	ArrayList<Pipe> mPipes = new ArrayList<Pipe>();
	private int score;
	private Pipe mP;
    public GameView(Context context) {
    	this(context,null);
    	
    }
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		initDraw();
		
		initBitmap();
		
		
		
		
	}
	
	private void initBitmap(){
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.floor_bg2);
		backg = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg1);
		bd = BitmapFactory.decodeResource(getResources(),
				R.drawable.b1);
		top = BitmapFactory.decodeResource(getResources(),
				R.drawable.g2);
		bottom = BitmapFactory.decodeResource(getResources(),
				R.drawable.g1);
	}
	private void initDraw(){
		mHolder = getHolder();
		mHolder.addCallback(this);
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d("wang","surfaceCreated");
		th = new Thread(this);
		th.start();
	}

	public void drawbg(){
		mCanvas.drawBitmap(backg, null, destRect, mPaint);
	}
	public void drawfloor(){
		bg.setSpeed(SPEED);
		bg.draw(mCanvas, mPaint);
	}
	public void drawBird(){
		bird.draw(mCanvas, mPaint);
	}
	public void drawPipe(Pipe p){
		p.setSpeed(SPEED);
		p.draw(mCanvas, mPaint);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

	
	while(true){
		
		long time = System.currentTimeMillis();
		mCanvas = mHolder.lockCanvas();
		mCanvas.drawColor(Color.WHITE);
		
		drawbg();
		
//		drawPipe();
		logicPipe();
		drawBird();
		logicBird();
		drawfloor();
		drawScore();
		if(failed) {
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Style.FILL);
			mCanvas.drawRoundRect(buttonX, buttonY, buttonX + buttonW, buttonY + buttonH, 10, 10, paint);
			paint.setColor(Color.WHITE);
			paint.setTextSize(20);
			String text = "重新开始";
			int size = (int) paint.measureText(text);
			mCanvas.drawText("重新开始", 150.0f - size / 2 , 125.0f - (paint.ascent() + paint.descent()) / 2,
					paint);
			mHolder.unlockCanvasAndPost(mCanvas);
			break;
		}
		mHolder.unlockCanvasAndPost(mCanvas);
	
		long timeafter = System.currentTimeMillis();
		try {
			if((timeafter - time) < 50) {
				Thread.sleep(50-(timeafter - time));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	public void drawScore(){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(15);
		mCanvas.drawText("您的得分：" + score, 10, 20, paint);
	}
	public void logicBird(){
		for(Pipe p : mPipes) {
			Log.d("wang","bird x " + bird.getX() + "p x " + p.getX() + " p width " + p.getWidth());
			if(bird.getX() > p.getX() && bird.getX() < (p.getX() + p.getWidth())) {
				Log.d("wang","bire y " + bird.getY() + " p height " + p.getTopHeight()  + " p margin " + p.getMargin());
				if(bird.getY() < p.getTopHeight() || bird.getY() > (p.getTopHeight() + p.getMargin())) {
					failed = true;
					Log.d("wang","failed is " + failed);
					break;
				} else {
					if(mP != p) {
						score += 1;
						mP = p;
					}
					Log.d("wang","score is " + score);
				}
			}
		}
	}
	public void logicPipe(){
		
			Pipe p = mPipes.get(mPipes.size() - 1);
			if((mGameWidth - p.getX()) >= BIRD_INIT_DISTANCE*mGameWidth) {
				Pipe pi = new Pipe(getContext(), mGameWidth, mGameHeight, top, bottom);
				mPipes.add(pi);
			}
			Pipe p1 = mPipes.get(0);
			if(p1.getX() <= -top.getWidth()) {
				mPipes.remove(p1);
			}
			for(Pipe temp : mPipes) {
				drawPipe(temp);
			}
		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mGameHeight = h;
		mGameWidth = w;
		destRect = new Rect(0, 0, w, h);
		bg = new Background(mGameWidth, mGameHeight, bm);
		
		bird = new Bird(context, w, h, bd);
		bird.setY(h*BIRD_INIT_HEIGHT);
		bird.setX(w*BIRD_INIT_WIDTH);
		
		pipe = new Pipe(context, w, h, top, bottom);
		mPipes.add(pipe);
	}

	public void reset(){
		bird.setY(mGameHeight*BIRD_INIT_HEIGHT);
		bird.setX(mGameWidth*BIRD_INIT_WIDTH);
		
		mPipes.clear();
		pipe = new Pipe(context, mGameWidth, mGameHeight, top, bottom);
		mPipes.add(pipe);
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
				if(failed) {
					if(event.getX() > buttonX && event.getX() < buttonX + buttonW && 
							event.getY() > buttonY && event.getY() < buttonY + buttonH) {
						failed = false;
						score = 0;
						reset();
						th = new Thread(this);
						th.start();
					}
				} else {
					bird.jump(BIRD_JUMP);
				}
			break;

		default:
			break;
		}
		return true;
	}
}
