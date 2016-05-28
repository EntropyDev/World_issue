package com.home.harsh.worldissues;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements WorldFragment.OnFragmentInteractionListener,IssuesFragment.OnFragmentInteractionListener,P.OnFragmentInteractionListener,S.OnFragmentInteractionListener,H.OnFragmentInteractionListener{
    TextView score1,score2,v_t,m_t,t_t;
    public static int  a;
    Button v,m,t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args= new Bundle();
        score1=(TextView)findViewById(R.id.score1);
        score2=(TextView)findViewById(R.id.score2);


        v=(Button)findViewById(R.id.Volu);
        m=(Button)findViewById(R.id.media);
        t=(Button)findViewById(R.id.tech);
        v_t=(TextView)findViewById(R.id.Volu_f);
        m_t=(TextView)findViewById(R.id.M_T_f);
        t_t=(TextView)findViewById(R.id.Tech_f);
        a=12000;
        final int[] v_a = {new Integer(v_t.getText().toString()).intValue()};
        final int[] m_a = {new Integer(m_t.getText().toString()).intValue()};
        final int[] t_a = {new Integer(t_t.getText().toString()).intValue()};
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_a[0] +=1;
                v_t.setText(String.valueOf(v_a[0]));
                a-=50;
                score2.setText(String.valueOf(a));
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               m_a[0] +=1;
                m_t.setText(String.valueOf(m_a[0]));
                a-=70;
                score2.setText(String.valueOf(a));
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t_a[0] +=1;
                t_t.setText(String.valueOf(t_a[0]));
                a-=120;
                score2.setText(String.valueOf(a));
            }
        });

        startTimer(a);
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        args.putString("Adding","true");
        WorldFragment worldFragment = new WorldFragment();
        worldFragment.setArguments(args);
        fragmentTransaction.add(R.id.frame_container,worldFragment);
        fragmentTransaction.commit();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*a=0;
        final Handler handler = new Handler();
        Timer    timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            a=a+20;
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 600);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onFragmentInteraction(Uri uri){

        }
    CountDownTimer countDownTimer;
    private void startTimer(final int minuti) {
        countDownTimer = new CountDownTimer(60 * minuti * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = (leftTimeInMilliseconds) / 1000;
                if (seconds>0) {
                    a+=5;
                    score2.setText(String.format("%02d", (a)));
                }
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
            }
        }.start();

    }
}
