package com.fenazola.mxcome.fragment.main.tool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.GradienterView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.text.DecimalFormat;

/**
 * Created by zm on 2017/3/6.
 */

public class GradienterFragment extends BaseFragment implements SensorEventListener {

    private SensorManager sensorManager;
    private GradienterView gradienterView;
    private int MAX_ANGLE = 90;
    private TextView pointX, pointY;
    private DecimalFormat format;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_gradienter;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);

        pointX = (TextView) findViewById(R.id.pointX);

        pointY = (TextView) findViewById(R.id.pointY);

        pointX = (TextView) findViewById(R.id.pointX);
        gradienterView = (GradienterView) findViewById(R.id.gradienterView);
        //获取传感器
        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        format = new DecimalFormat("0.00");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //获取传感器的类型
        float values[] = event.values;
        //获取与Y轴的夹角
        float yAngle = values[1];
        //获取与Z轴的夹角
        float zAngle = values[2];

        pointX.setText(format.format(yAngle));
        pointY.setText(format.format(zAngle));

        //气泡位于中间时（水平仪完全水平）
        int x = (gradienterView.getBg().getWidth() - gradienterView.getBall().getWidth()) / 2;
        int y = (gradienterView.getBg().getHeight() - gradienterView.getBall().getHeight()) / 2;
        //如果与Z轴的倾斜角还在最大角度之内
        if (Math.abs(zAngle) <= MAX_ANGLE) {
            //根据与Z轴的倾斜角度计算X坐标轴的变化值
            int deltaX = (int) ((gradienterView.getBg().getWidth() - gradienterView.getBall().getWidth()) / 2
                    * zAngle / MAX_ANGLE);
            x += deltaX;
        }
        //如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
        else if (zAngle > MAX_ANGLE) {
            x = 0;
        }
        //如果与Z轴的倾斜角已经小于负的Max_ANGLE,气泡应到最右边
        else {
            x = gradienterView.getBg().getWidth() - gradienterView.getBall().getWidth();
        }

        //如果与Y轴的倾斜角还在最大角度之内
        if (Math.abs(yAngle) <= MAX_ANGLE) {
            //根据与Z轴的倾斜角度计算X坐标轴的变化值
            int deltaY = (int) ((gradienterView.getBg().getHeight() - gradienterView.getBall().getHeight()) / 2
                    * yAngle / MAX_ANGLE);
            y += deltaY;
        }
        //如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
        else if (yAngle > MAX_ANGLE) {
            y = gradienterView.getBg().getHeight() - gradienterView.getBall().getHeight();
        }
        //如果与Y轴的倾斜角已经小于负的Max_ANGLE,气泡应到最上边
        else {
            y = 0;
        }
        //如果计算出来的X，Y坐标还位于水平仪的仪表盘之内，则更新水平仪气泡坐标
        if (true) {
            gradienterView.setBallX(x);
            gradienterView.setBallY(y);
        }


        //通知组件更新
        gradienterView.postInvalidate();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setTitleColor(getColor(R.color.grey));
        toolbar.setTitle(getString(R.string.main_gradienter_title));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //注册
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onStop() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onPause() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onPause();
    }

}
