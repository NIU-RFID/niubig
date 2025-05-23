package com.example.niubig;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean game1_isDone, game2_isDone, game3_isDone, game4_isDone, game5_isDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("isDone", MODE_PRIVATE);
        game1_isDone = sharedPreferences.getBoolean("game1", false);
        game2_isDone = sharedPreferences.getBoolean("game2", false);
        game3_isDone = sharedPreferences.getBoolean("game3", false);
        game4_isDone = sharedPreferences.getBoolean("game4", false);
        game5_isDone = sharedPreferences.getBoolean("game5", false);

        // 啟動背景音樂
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }
    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void startReward(View view) {
        if (game1_isDone && game2_isDone && game3_isDone && game4_isDone && game5_isDone) {
            Intent intent = new Intent(this, Reward.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "請先通關所有關卡", Toast.LENGTH_SHORT).show();
        }
    }
    public void startInfo(View view) {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }
    @SuppressLint("QueryPermissionsNeeded")
    public void startMap(View view) {
        double latitude = 24.633295697260273;
        double longitude = 121.71218206489003;
        String label = "大進社區發展協會";
        String query = latitude + "," + longitude + "(" + label + ")";
        String uriString = "geo:0,0?q=" + Uri.encode(query);
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Uri playStoreUri = Uri.parse("market://details?id=com.google.android.apps.maps");
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);
            if (playStoreIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(playStoreIntent);
            } else {
                Uri webUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                startActivity(new Intent(Intent.ACTION_VIEW, webUri));
            }
        }
    }

}