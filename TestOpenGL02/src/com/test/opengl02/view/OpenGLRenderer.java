package com.test.opengl02.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.test.opengl02.R;
import com.test.opengl02.component.BaseComp;
import com.test.opengl02.component.Square;
import com.test.opengl02.component.Triangle;

public class OpenGLRenderer implements Renderer {
	
	BaseComp square = new Triangle();
	
	int width, height;
	
	private final float ONE = 1.0f;
	private final float z_value = -5.0f;
	
	private FloatBuffer textureBuffer;

	private FloatBuffer vertexBuffer;
	
	private FloatBuffer colorBuffer;
	
	private ShortBuffer indexBuffer;
	
	private Context mContext;
	
	private int mTextureId;
	
	private long lastUpdateTime = 0l;
	
	public OpenGLRenderer(Context ctx) {
		this.mContext = ctx;
	}
	
	/*
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
//		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//		
//		square.draw(gl);
		
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
		
		gl.glLoadIdentity();
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
//		gl.glTranslatef(0.0f, -0.0f, 0.001f);
//		gl.glRotatef(0.01f, 0, 0, 1);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		
//		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		
//		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 9);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		gl.glBlendFunc(GL10.GL_ONE_MINUS_DST_ALPHA, GL10.GL_DST_ALPHA);
//		
//		gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
		gl.glTranslatef(0.5f, 0.5f, 0.0f);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	/*
	 * If your device supports flipping between landscape and portrait you will
	 * get a call to this function when it happens. What you do here is setting
	 * up the new ratio.
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println(">>> OpenGLRenderer.onSurfaceChanged :: called!!! " + gl + " - " + width + " : " + height);

		this.width = width;
		this.height = height;
		
		float zNear = 1.0f;
		float size = (float) (zNear * Math.tan(Math.toRadians(45)/2));
		float ratio = 1.0f*width/height;
		
		System.out.println(">>> ratio = " + ratio + " size = " + size);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glFrustumf(-size, size, -size/ratio, size/ratio, zNear, 100.0f);
//		gl.glOrthof(-1.0f, 1.0f, -1.0f/ratio, 1.0f/ratio, 0.01f, 100.0f);
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		gl.glEnable(GL10.GL_CULL_FACE);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glCullFace(GL10.GL_BACK);
		
//		GLU.gluOrtho2D(gl, -1.0f, 1.0f, -1.0f*height/width, 1.0f*height/width);
//		gl.glOrthof(-1.0f, 1.0f, -1.0f*height/width, 1.0f*height/width, 0.1f, 200000);
//		gl.glFrustumf(-1.0f, 1.0f, -1.0f*height/width, 1.0f*height/width, 0.1f, 200000);
//		GLU.gluPerspective(gl, 90.0f, (float) width / (float) height, 0.1f, 100.0f);
		
//		gl.glViewport(0, 0, width, height);

//		gl.glMatrixMode(GL10.GL_PROJECTION);
		
//		gl.glLoadIdentity();
		
//		GLU.gluPerspective(gl, 80.0f, 1.0f, 0.1f, 100.0f);
		
//		gl.glMatrixMode(GL10.GL_MODELVIEW);
//		gl.glLoadIdentity();
	}

	/*
	 * Here it's a good thing to setup things that you don't change so often in
	 * the rendering cycle. Stuff like what color to clear the screen with,
	 * enabling z-buffer and so on.
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println(">>> OpenGLRenderer.onSurfaceCreate :: called!!! " + gl);
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		ByteBuffer tbb = ByteBuffer.allocateDirect(this.textures.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		
		textureBuffer = tbb.asFloatBuffer();
		textureBuffer.put(this.textures);
		textureBuffer.position(0);
		
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);

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
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        gl.glEnable(GL10.GL_BLEND);

		Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
		
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureId = textures[0];
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        
        // Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                           GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                           GL10.GL_LINEAR);
        
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);
	}

	private float vertices[] = { 
			-ONE, 	ONE, 	z_value, 	// 0, Top Left
			-ONE, 	-ONE, 	z_value,  	// 1, Bottom Left
			ONE, 	-ONE, 	z_value,  	// 2, Bottom Right
			ONE, 	ONE, 	z_value,  	// 3, Top Right
	};
	
	private float colors[] = {
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
	};
	
	private short indices[] = {
			0, 1, 2,
			2, 3, 0,
	};
	
	private float textures[] = {
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
	};
	
	private final int FPS = 30;
	private final long INTERVAL = 1000l/FPS; 	// ms/perframe

}
