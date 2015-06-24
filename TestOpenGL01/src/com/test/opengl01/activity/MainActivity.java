package com.test.opengl01.activity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.test.opengl01.view.OpenGLRenderer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new OpenGLRenderer());
        setContentView(view);
        
//        SQwanCore.getInstance().init(this, "7Q/Rh-p_goN,zd?", new SQResultListener() {
//			
//			@Override
//			public void onSuccess(Bundle arg0) {
//				Log.e("", "37 wan init success !!!");
//			}
//			
//			@Override
//			public void onFailture(int arg0, String arg1) {
//				Log.e("", "37 wan init fail !!!" + arg1);
//			}
//		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
