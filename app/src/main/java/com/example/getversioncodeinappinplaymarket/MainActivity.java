package com.example.getversioncodeinappinplaymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String latestVersionName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkingForUpdate();


    }

    void checkingForUpdate(){
        Button button = findViewById(R.id.btn_update);
        if ( compareVersions()){
            TextView tvcurrent = findViewById(R.id.tv_current_version);
            TextView tvPlaymarket = findViewById(R.id.tv_play_market_version);
            tvcurrent.setText(BuildConfig.VERSION_NAME);
            tvPlaymarket.setText(latestVersionName);
            button.setText("Update");
            Log.d("TAG", "initViews: " + compareVersions());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW);
                    myIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=net.giosis.shopping.sg&hl=en&gl=US"));
                    startActivity(myIntent);
                }
            });
        }

    }

    String getVersion() {
        VersionChecker versionChecker = new VersionChecker();
        try {
            latestVersionName = versionChecker.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return latestVersionName;
    }

    boolean compareVersions() {
        getVersion();

        String applicationVerName = BuildConfig.VERSION_NAME;
        String split1;
        split1 = applicationVerName.substring(0,applicationVerName.lastIndexOf("."));
        String split2;
        split2 = latestVersionName.substring(0,latestVersionName.lastIndexOf("."));

        return Double.parseDouble(split2)>Double.parseDouble(split1);
    }

}