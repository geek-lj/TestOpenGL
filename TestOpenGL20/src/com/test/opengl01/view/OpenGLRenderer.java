package com.test.opengl01.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.test.opengl01.mesh.BaseMesh;
import com.test.opengl01.mesh.Square;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer {
	
	private Context mContext;
	
	private BaseMesh mesh = new Square();
	
	public OpenGLRenderer(Context ctx) {
		this.mContext = ctx;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		long curTime = System.nanoTime();
		long lastTime = (curTime - lastUpdateTime)/1000000; 	// ms
		if (lastTime < INTERVAL) {
			try {
				Thread.sleep(INTERVAL - lastTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastUpdateTime = System.nanoTime();
		
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		mesh.draw(gl);
		System.out.println(">>> ondrawframe called!!!");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		float ratio = (float) width / height;

	    // 此投影矩阵在onDrawFrame()中将应用到对象的坐标
//	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
		
	}

	private long lastUpdateTime = 0l;
	private final int FPS = 30;
	private final long INTERVAL = 1000l/FPS; 	// ms/perframe
}
