package bossometer.ben.de.bossometer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class activitiy_2 extends AppCompatActivity {

    private ImageView pic;
    private TextView count;
    private Handler handler;
    private int score;
    private int currentScore;
    private Button backwards;
    private Button share;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitiy_2);

        pic = (ImageView) findViewById(R.id.image_shot);
        count = (TextView) findViewById(R.id.text_counter);
        score = new Random().nextInt(101);
        currentScore = 0;
        text = "OMG! I scored " + score + " points in Bossometer! Do you think you can beat me? Download Bossometer from the Google Play Store.";
        backwards = findViewById(R.id.button_retry);
        share = findViewById(R.id.button_share);

        backwards.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String path = (String) getIntent().getStringExtra("path");
                Log.d("PETER", path);

                Uri sharedUri = Uri.parse(path);
                ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(activitiy_2.this);
                Intent chooserIntent = builder.createChooserIntent();
                startActivity(chooserIntent);
            }
        });

        Bitmap map = (Bitmap) getIntent().getParcelableExtra("picture");

        pic.setImageBitmap(map);

        handler = new Handler();
        startRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if (currentScore == score) {
                    stopRepeatingTask();
                    // make animation
                    return;
                }

                count.setText(String.valueOf(currentScore));
                currentScore += 1;
            } finally {
                handler.postDelayed(mStatusChecker, 20);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        handler.removeCallbacks(mStatusChecker);
    }


}
