package com.test.opengl01.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.test.opengl01.component.BaseComp;
import com.test.opengl01.component.Triangle;

public class OpenGLRenderer implements Renderer {
	
	BaseComp square = new Triangle();

	/*
	 * Here is where the actual drawing take place. ������ʵ�ʻ�����Ļ�ĵط�
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// System.out.println(">>> OpenGLRenderer.onDrawFrame :: called!!! " +
		// gl);

		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		square.draw(gl);
	}

	/*
	 * If your device supports flipping between landscape and portrait you will
	 * get a call to this function when it happens. What you do here is setting
	 * up the new ratio.
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println(">>> OpenGLRenderer.onSurfaceChanged :: called!!! " + gl + " - " + width + " : " + height);

		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height/2);

		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);

		// Reset the projection matrix
		gl.glLoadIdentity();

		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 90.0f, (float) width / (float) height, 0.1f, 100.0f);

		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		// Reset the modelview matrix
		gl.glLoadIdentity();
	}

	/*
	 * Here it's a good thing to setup things that you don't change so often in
	 * the rendering cycle. Stuff like what color to clear the screen with,
	 * enabling z-buffer and so on.
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println(">>> OpenGLRenderer.onSurfaceCreate :: called!!! " + gl);

		// Set the background color to black ( rgba ).
		gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);

		// Depth buffer setup.
		gl.glClearDepthf(1.0f);

		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);

		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

}
