package com.test.opengl01.mesh;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.test.opengl01.utils.ShaderUtils;

import android.opengl.GLES20;

public class Square implements BaseMesh {

	private FloatBuffer vertexBuffer;

	private ShortBuffer indexBuffer;
	
	private int mProgram;

	public Square() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		loadProgram();
	}
	
	private void loadProgram() {
		mProgram = GLES20.glCreateProgram();
		
		int vertexShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		
		GLES20.glLinkProgram(mProgram);
	}

	public void draw(GL10 gl) {
		
		GLES20.glUseProgram(mProgram);
		
		int positionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
		
		GLES20.glEnableVertexAttribArray(positionHandler);
		
		GLES20.glVertexAttribPointer(positionHandler, 4, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		int colorHandler = GLES20.glGetAttribLocation(mProgram, "vColor");
		
		GLES20.glUniform4fv(colorHandler, 4, colors, 0);
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 12);
//		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_FLOAT, indexBuffer);
		
		GLES20.glDisableVertexAttribArray(positionHandler);
		
	}
	
	private static final String vertexShaderCode =
	    "attribute vec4 vPosition;" +
	    "void main() {" +
	    "  gl_Position = vPosition;" +
	    "}";

	private static final String fragmentShaderCode =
	    "precision mediump float;" +
	    "uniform vec4 vColor;" +
	    "void main() {" +
	    "  gl_FragColor = vColor;" +
	    "}";
	
	private final static float ONE = 1.0f;
	private final static float Z_ONE = -1.0f;
	
	private float vertices[] = { 
			-ONE, 	ONE, 	Z_ONE, // 0, Top Left
			-ONE, 	-ONE, 	Z_ONE, // 1, Bottom Left
			ONE, 	-ONE, 	Z_ONE, // 2, Bottom Right
			ONE, 	ONE, 	Z_ONE, // 3, Top Right
	};
	
	private float colors[] = {
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
	};

	private short[] indices = { 0, 1, 2, 2, 3, 0 };
}
