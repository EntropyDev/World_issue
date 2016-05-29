package com.home.harsh.worldissues;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class WacomActivity extends AppCompatActivity {

    WebView web;
    ProgressBar progressBar;
    String URL = "";
    String lang = "hi";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wacom);

        button=(Button)findViewById(R.id.button);

        URL = "https://twitter.com/search?q=ngo&src=typd";
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.getSettings().setDefaultTextEncodingName("utf-8");
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
         progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        Toast t=Toast.makeText(getApplicationContext(), "Select the domain please", Toast.LENGTH_LONG);
        t.show();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                web.loadUrl(URL);
                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                },4000);

            }
        });
    }
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //web.loadUrl("http://m.axisbank.com/#/smartphone/personal/index.aspx/#googtrans(en|hi)");
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            progressBar.setVisibility(View.GONE);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.evaluateJavascript("function addRow() {\n" +
                        "    var div = document.createElement('div');\n" +
                        "\n" +
                        "    div.id = 'google_translate_element';}", null);

                view.evaluateJavascript("function googleTranslateElementInit() {" +
                        "  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');" +
                        "}", null);
            }*/
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.ngo:
                lang = "ngo";
                break;
            case R.id.politics:
                lang = "politics";
                break;
            case R.id.education:
                lang = "education";
                break;
            case R.id.women:
                lang = "women";
                break;
            default:
                lang = "ngo";
                break;
        }
        URL = "https://twitter.com/search?q="+lang+"&src=typd";;
        return super.onOptionsItemSelected(item);
    }
}

