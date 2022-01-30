package com.example.newbossbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    boolean Check = false;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 100;

    private Handler mHandler = new Handler();

    TextView time, totalHealth, pLevel;
    CountDownTimer timer;
    Button btn_start;

    int t = 30;

    int playerLvl = 3;
    int strengthLvl = 3;

//    Bundle bundle = getIntent().getExtras(); ------------------------------------------------------
//    int playerLvl = bundle.getInt("Level");
//    int stengthLvl = bundle.getInt("Strength");



    private int health = 100 + 80*playerLvl;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_main);


        time = (TextView)findViewById(R.id.time);
        totalHealth = (TextView)findViewById(R.id.totalHealth);
        ImageView imgMonster = (ImageView)findViewById(R.id.imgMonster);
        btn_start = (Button)findViewById(R.id.btn_start);
        ImageView img=(ImageView)findViewById(R.id.imgFlame);
        pLevel = (TextView)findViewById(R.id.pLevel);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        btn_start.setEnabled(true);

        pLevel.setText("Lvl: " + playerLvl);



        timer = new CountDownTimer( 30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                t--;
                time.setText("" + t);

                if (t % 2 == 0){
                    img.setVisibility(View.INVISIBLE);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgressStatus > 0){
                            mProgressStatus--;
                            android.os.SystemClock.sleep(5000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                }
                            });

                        }

                    }
                }).start();

            }

            @Override
            public void onFinish() {
                btn_start.setEnabled(true);
                time.setText("0");
                totalHealth.setText("HP 0");

                Intent primIntent = new Intent(MainActivity.this, out_of_time.class); // BRYSON replace
                startActivity(primIntent);

            }
        };

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                btn_start.setEnabled(false);
                Check = true;

                t = 30;

                time.setText("" + t);
                totalHealth.setText("HP "+ health);


            }
        });


            imgMonster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        if(Check == true) {
                            if ((health)- 2*strengthLvl > 0) {
                                health = health - 2*strengthLvl;
                                img.setVisibility(View.VISIBLE);
                                    totalHealth.setText("HP "+ health);

                            }
                            else {
                                timer.cancel();
                                Intent primIntent = new Intent(MainActivity.this, you_win.class); // BRYSON replace
                                startActivity(primIntent);
                            }
                            totalHealth.setText("HP "+ health);
                        }
                }

            });







    }



}