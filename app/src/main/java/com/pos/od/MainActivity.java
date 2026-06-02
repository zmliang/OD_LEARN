package com.pos.od;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.od.R;
import com.pos.od.kb.NKeyboard;
import com.pos.ui.MyComposeActivity;
import com.zml.guide.GuiderCreator;
import com.zml.guide.IGuiderLayer;
import com.zml.guide.Offset;
import com.zml.guide.OnNextStepListener;
import com.zml.guide.Position;
import com.zml.guide.RoundCornerDrawable;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class MainActivity extends Activity {
    static{
        System.loadLibrary("gles");
    }
    private final int CONTEXT_CLIENT_VERSION = 3;
    private IGuiderLayer guide;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        guide = GuiderCreator.Companion.create(this)
                .addTarget(findViewById(R.id.go_compose))
                .addTarget(findViewById(R.id.go_opengl))
                .setOnNextStepListener(new OnNextStepListener() {
                    @Override
                    public void onNext(int index, @NonNull IGuiderLayer guideLayer) {
                        if (guideLayer.getStepView() == null){
                            LayoutInflater inflater = LayoutInflater.from(guideLayer.context());
                            View v = inflater.inflate(R.layout.default_step_view, null);
                            v.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    guideLayer.dismiss();
                                }
                            });
                            v.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    guideLayer.nextStep();
                                }
                            });
                            v.setBackground(new RoundCornerDrawable(8,
                                    Color.WHITE,
                                    15f,
                                    Position.TOP,
                                    new Offset(16f,0f)));
                            guideLayer.setStepView(v);
                        }
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dp2px(196),dp2px(83));
                        RectF rectF = guideLayer.currentTargetRect();
                        lp.leftMargin = (int) (rectF.left+20);
                        lp.topMargin = (int) (rectF.bottom+20);
                        guideLayer.getStepView().setLayoutParams(lp);
                    }
                })
        ;
        ((Button)findViewById(R.id.go_compose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                flag[0] = !flag[0];
//                if (!flag[0]){
//                    aav.setValue("999999.0000000abcedfft");
//                }else {
//                    aav.setValue("999");
//                }

                startActivity(new Intent(MainActivity.this, MyComposeActivity.class));

            }
        });
        ((Button)findViewById(R.id.go_opengl)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EGLActivity.class));

            }
        });

        ((Button)findViewById(R.id.go_v8)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, V8Activity.class));

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("zml","权限通过了");
        testSM3();
    }

    private void testSM3(){
        try {
            //File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //String downloadsPath = downloadsDir.getAbsolutePath();
           // Log.e("ZML","SM3 downloadsPath: " + downloadsPath);
            //String p = "/storage/emulated/0/20240617490.zip";
            //File file = new File(p);
            String sm3Digest = SM3Utils.sm3Digest(this);
            Log.e("ZML","SM3 Digest: " + (sm3Digest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        NKeyboard.Companion.get().hide(this,true);
        return super.onTouchEvent(event);
    }

    public void onBackPressed() {
        if (NKeyboard.Companion.get().isKeyBoardShowing()) {
            NKeyboard.Companion.get().hide(this,true);
            return;
        }
        super.onBackPressed();
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                this.getResources().getDisplayMetrics()
        );
    }


    private boolean detectOpenGLES30() {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e("ZML","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
