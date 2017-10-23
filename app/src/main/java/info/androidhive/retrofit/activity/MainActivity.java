package info.androidhive.retrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Random;

import info.androidhive.retrofit.Helper.OnShakeListener;
import info.androidhive.retrofit.Helper.ShakeDetector;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Fortune;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private  List<Fortune> mFortunes;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: how do you know that it is from sensor !!!
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        //How do you know that you need to override ?
        mShakeDetector.setOnShakeListener(new OnShakeListener() {
           @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            };

        });

        View button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = generateRandomNmber();
                String quote = mFortunes.get(number).getQuote();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("quote", quote);
                startActivity(intent);
            }
        });

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Fortune>> call = apiService.getItems();

        call.enqueue(new Callback<List<Fortune>>() {
            @Override
            public void onResponse(Call<List<Fortune>> call, Response<List<Fortune>> response) {
                mFortunes = response.body();


            }

            @Override
            public void onFailure(Call<List<Fortune>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }
// can we generate this @Override?
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }



    private void handleShakeEvent(int count) {

    }

    private int generateRandomNmber() {
        Random random = new Random();
        return random.nextInt(65);
    }
}
