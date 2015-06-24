package com.test.opengl01.utils;

import android.opengl.GLES20;

public class ShaderUtils {


	/**
	 * @param type 	</br> GLES20.GL_VERTEX_SHADER </br> GLES20.GL_FRAGMENT_SHADER
	 * @param code
	 * @return
	 */
	public static int loadShader(int type, String code) {
		int shader = GLES20.glCreateShader(type);
		
		GLES20.glShaderSource(shader, code);
		GLES20.glCompileShader(shader);
		
		return shader;
	}
}
