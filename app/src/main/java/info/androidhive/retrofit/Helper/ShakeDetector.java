package info.androidhive.retrofit.Helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.util.FloatMath;

public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mOnShakeListener;
    private long mShakeTimeStamp;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        this.mOnShakeListener = onShakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (mOnShakeListener != null) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY ) {
                final long now = System.currentTimeMillis();
                if (mShakeTimeStamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                if (mShakeTimeStamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                mShakeTimeStamp = now;
                mShakeCount++;
                mOnShakeListener.onShake(mShakeCount);

            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
