package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import info.androidhive.retrofit.R;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);


        String quote = getIntent().getStringExtra("quote");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(quote);

    }
}
