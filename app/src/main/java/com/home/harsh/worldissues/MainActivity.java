package com.home.harsh.worldissues;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnTouchListener;

import com.wacom.ink.path.PathBuilder.PropertyFunction;
import com.wacom.ink.path.PathBuilder.PropertyName;
import com.wacom.ink.path.PathUtils;
import com.wacom.ink.path.PathUtils.Phase;
import com.wacom.ink.path.SpeedPathBuilder;
import com.wacom.ink.rasterization.BlendMode;
import com.wacom.ink.rasterization.InkCanvas;
import com.wacom.ink.rasterization.Layer;
import com.wacom.ink.rasterization.SolidColorBrush;
import com.wacom.ink.rasterization.StrokePaint;
import com.wacom.ink.rasterization.StrokeRenderer;
import com.wacom.ink.rendering.EGLRenderingContext.EGLConfiguration;
import com.wacom.ink.smooth.MultiChannelSmoothener;
import com.wacom.ink.smooth.MultiChannelSmoothener.SmoothingResult;

import java.nio.FloatBuffer;
import java.util.Timer;
/*import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;*/
import java.util.TimerTask;
/*import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;*/
public class MainActivity extends AppCompatActivity implements WorldFragment.OnFragmentInteractionListener,IssuesFragment.OnFragmentInteractionListener,P.OnFragmentInteractionListener,S.OnFragmentInteractionListener,H.OnFragmentInteractionListener,Percentage.OnFragmentInteractionListener,FragmentManager.OnBackStackChangedListener{
    TextView score1,score2,v_t,m_t,t_t;
    private InkCanvas inkCanvas;
    private Layer viewLayer;
    private SpeedPathBuilder pathBuilder;
    private StrokePaint paint;
    private SolidColorBrush brush;
    private MultiChannelSmoothener smoothener;
    private int pathStride;
    private StrokeRenderer strokeRenderer;
    private Layer strokesLayer;
    private Layer currentFrameLayer;

    private int step;
    private int colors[] = {0x77ff0000, //red with 0x77 alpha
            0x8800ff00, //green with 0x88 alpha
            0x990000ff	//blue with 0x99 alpha
    };
    Bitmap myChar;
    public static int  a;
    ImageButton v,m,t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pathBuilder = new SpeedPathBuilder();
        pathBuilder.setNormalizationConfig(100.0f, 4000.0f);
        pathBuilder.setMovementThreshold(2.0f);
        pathBuilder.setPropertyConfig(PropertyName.Width, 10f, 40f, Float.NaN, Float.NaN, PropertyFunction.Power, 1.0f, false);
        pathStride = pathBuilder.getStride();

        step = 0;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {



                                                @Override
                                                public void surfaceCreated(SurfaceHolder holder) {

                                                }

                                                @Override
                                                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                                    if (inkCanvas!=null && !inkCanvas.isDisposed()){
                                                        releaseResources();
                                                    }

                                                    inkCanvas = InkCanvas.create(holder, new EGLConfiguration(8, 8, 8, 8, 8, 8));

                                                    viewLayer = inkCanvas.createViewLayer(width, height);
                                                    strokesLayer = inkCanvas.createLayer(width, height);
                                                    currentFrameLayer = inkCanvas.createLayer(width, height);

                                                    inkCanvas.clearLayer(currentFrameLayer, Color.WHITE);

                                                    brush = new SolidColorBrush();

                                                    paint = new StrokePaint();
                                                    paint.setStrokeBrush(brush);		// Solid color brush.
                                                    paint.setWidth(Float.NaN);			// Expected variable width.

                                                    smoothener = new MultiChannelSmoothener(pathStride);
                                                    smoothener.enableChannel(2);

                                                    strokeRenderer = new StrokeRenderer(inkCanvas, paint, pathStride, width, height);

                                                    renderView();

                                                }

                                                @Override
                                                public void surfaceDestroyed(SurfaceHolder holder) {
                                                    releaseResources();
                                                }

                                            });

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args= new Bundle();
        score1=(TextView)findViewById(R.id.score1);
        score2=(TextView)findViewById(R.id.score2);


        v=(ImageButton)findViewById(R.id.Volu);
        m=(ImageButton)findViewById(R.id.media);
        t=(ImageButton) findViewById(R.id.tech);
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
        surfaceView.setZOrderMediaOverlay(true);
        surfaceView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buildPath(event);
                drawStroke(event);
                Log.d("x,y",String.valueOf(event.getX())+","+String.valueOf(event.getY()));
                renderView();
                return true;
            }
        });
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
    private void renderView() {
        inkCanvas.setTarget(viewLayer);
        // Copy the current frame layer in the view layer to present it on the screen.
        inkCanvas.drawLayer(currentFrameLayer, BlendMode.BLENDMODE_OVERWRITE);
        inkCanvas.invalidate();
    }

    private void buildPath(MotionEvent event){
        if (event.getAction()!=MotionEvent.ACTION_DOWN
                && event.getAction()!=MotionEvent.ACTION_MOVE
                && event.getAction()!=MotionEvent.ACTION_UP){
            return;
        }

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            // Reset the smoothener instance when starting to generate a new path.
            smoothener.reset();
        }

        Phase phase = PathUtils.getPhaseFromMotionEvent(event);
        // Add the current input point to the path builder
        FloatBuffer part = pathBuilder.addPoint(phase, event.getX(), event.getY(), event.getEventTime());
        SmoothingResult smoothingResult;
        int partSize = pathBuilder.getPathPartSize();

        if (partSize>0){
            // Smooth the returned control points (aka path part).
            smoothingResult = smoothener.smooth(part, partSize, (phase==Phase.END));
            // Add the smoothed control points to the path builder.
            pathBuilder.addPathPart(smoothingResult.getSmoothedPoints(), smoothingResult.getSize());
        }

        // Create a preliminary path.
        FloatBuffer preliminaryPath = pathBuilder.createPreliminaryPath();
        // Smoothen the preliminary path's control points (return inform of a path part).
        smoothingResult = smoothener.smooth(preliminaryPath, pathBuilder.getPreliminaryPathSize(), true);
        // Add the smoothed preliminary path to the path builder.
        pathBuilder.finishPreliminaryPath(smoothingResult.getSmoothedPoints(), smoothingResult.getSize());
    }

    private void drawStroke(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            paint.setColor(colors[step]);
            strokeRenderer.setStrokePaint(paint);
            step++;
            if (step==colors.length){
                step = 0;
            }
        }

        strokeRenderer.drawPoints(pathBuilder.getPathBuffer(), pathBuilder.getPathLastUpdatePosition(), pathBuilder.getAddedPointsSize(), event.getAction()==MotionEvent.ACTION_UP);
        strokeRenderer.drawPrelimPoints(pathBuilder.getPreliminaryPathBuffer(), 0, pathBuilder.getFinishedPreliminaryPathSize());

        if (event.getAction()!=MotionEvent.ACTION_UP){
            inkCanvas.setTarget(currentFrameLayer, strokeRenderer.getStrokeUpdatedArea());
            inkCanvas.clearColor(Color.WHITE);
            inkCanvas.drawLayer(strokesLayer, BlendMode.BLENDMODE_NORMAL);
            strokeRenderer.blendStrokeUpdatedArea(currentFrameLayer, BlendMode.BLENDMODE_NORMAL);
        } else {
            strokeRenderer.blendStroke(strokesLayer, BlendMode.BLENDMODE_NORMAL);
            inkCanvas.setTarget(currentFrameLayer);
            inkCanvas.clearColor(Color.WHITE);
            inkCanvas.drawLayer(strokesLayer, BlendMode.BLENDMODE_NORMAL);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void releaseResources(){
        strokeRenderer.dispose();
        inkCanvas.dispose();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ngo) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
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

    @Override
    public void onBackStackChanged() {

        boolean canback = this.getSupportFragmentManager().getBackStackEntryCount()>0;
        Log.d("backstack",String.valueOf(canback));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(canback);

    }
}
