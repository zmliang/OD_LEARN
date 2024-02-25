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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.od.R;
import com.pos.ui.MyComposeActivity;

import com.zml.guide.GuideLayer;
import com.zml.guide.GuiderCreator;
import com.zml.guide.IGuiderLayer;
import com.zml.guide.Offset;
import com.zml.guide.OnNextStepListener;
import com.zml.guide.Position;
import com.zml.guide.RoundCornerDrawable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
                startActivity(new Intent(MainActivity.this, MyComposeActivity.class));

            }
        });
        ((Button)findViewById(R.id.go_opengl)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EGLActivity.class));

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.test_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = "string " + i;
        }
        //Log.e("zml","最后一个="+String.valueOf(array[array.length-1]));
        recyclerView.setAdapter(new ArrayAdapter(this, array));

        guide.show();
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


    static class ArrayAdapter extends RecyclerView.Adapter<ViewHolder>{

        private String[] mArray;
        private Context mContext;

        public ArrayAdapter(Context context, String[] array) {
            mContext = context;
            mArray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText(mArray[i]);
        }

        @Override
        public int getItemCount() {
            return mArray.length;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }


    private final OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/article/list/1/json")
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.i("zml","\r\n\r\n");
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.i("zml",responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.i("zml",response.body().string());
            }
        });
    }


}
