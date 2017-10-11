package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.FortunesAdapter;
import info.androidhive.retrofit.model.Fortune;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fortunes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Fortune>> call = apiService.getItems();

        call.enqueue(new Callback<List<Fortune>>() {
            @Override
            public void onResponse(Call<List<Fortune>> call, Response<List<Fortune>> response) {
                List<Fortune> fortunes = response.body();
                recyclerView.setAdapter(new FortunesAdapter(fortunes, R.layout.list_item_fortune, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<List<Fortune>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
