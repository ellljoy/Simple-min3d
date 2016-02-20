package com.example.simplemin3d;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;

/**
 * Created by hero on 2016/1/31.
 *
 */
public class MyRendererActivity  extends RendererActivity{
    Object3dContainer mObject3d;
    private Button addx;
    private Button addy;
    private TextView mTextView;
    private float angleX = 0;
    private float angleY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addx = (Button) findViewById(R.id.addx);
        addy = (Button) findViewById(R.id.addy);
        mTextView = (TextView) findViewById(R.id.text_view);
        addx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angleX+=10;
                mObject3d.rotation().x=angleX;
                mTextView.setText("angleX=" + angleX);
            }
        });
        addy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angleY+=10;
                mObject3d.rotation().z=angleY;
                mTextView.setText("angleY="+angleY);
            }
        });
        FrameLayout ll = (FrameLayout) findViewById(R.id.ll);
        ll.addView(_glSurfaceView);
        Toast.makeText(this,"运行正常",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initScene() {
        scene.lights().add(new Light()); //设置灯光，否则一片黑，什么都看不见
        /**
         * 3DMax 模型加载
         * 3DMax文件放在\res\raw\
         * 贴图路径\res\drawable\中
         */
        IParser myParser = Parser.createParser(Parser.Type.MAX_3DS,getResources(),"com.example.simplemin3d:raw/newstaff",false);
        myParser.parse();
        mObject3d = myParser.getParsedObject();
        /**
         * 这个xyz设置模型的大小
         */
        mObject3d.scale().x=0.03f;
        mObject3d.scale().z=0.03f;
        mObject3d.scale().y=0.03f;
        /**
         * 设置初始位置，平截头体中xyz的坐标位置
         */
        mObject3d.position().x=0;//
        mObject3d.position().y=0;
        mObject3d.position().z=0;

        scene.camera().target = mObject3d.position();
        scene.addChild(mObject3d);  //把模型加入到场景中，这样模型才能显示
        mObject3d.colorMaterialEnabled(true);   //打开材质支持，默认关闭
    }

    @Override
    public void updateScene() {

    }
}
