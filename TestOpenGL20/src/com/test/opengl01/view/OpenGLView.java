package com.test.opengl01.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLView extends GLSurfaceView {

	public OpenGLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OpenGLView(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		setRenderer(new OpenGLRenderer(context));
		
//		setRenderMode(RENDERMODE_WHEN_DIRTY);
	}


}
