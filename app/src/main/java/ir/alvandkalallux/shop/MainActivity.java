package ir.alvandkalallux.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    private boolean exit = false;
    FloatingActionButton refreshButton;
    ProgressBar progressBar;
    boolean noInternet=false;
    String currentUrl="https://alvandkalalux.ir/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.MainActivityWebView);
        refreshButton = findViewById(R.id.refreshFloatingButton);
        progressBar = findViewById(R.id.progressBar);



            String url = "https://alvandkalalux.ir/"; //Enter your url here
            WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//enable menu works
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if( url.startsWith("http:") || url.startsWith("https:") ) {
                        return false;
                    }
                    // Otherwise allow the OS to handle things like tel, mailto, etc.
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                    return true;
                }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {

                webView.loadUrl("javascript:(function() { " +
                        "document.getElementById('top-bar').style.display='none';" +
                        "document.getElementById('footer').style.display='none';})()");

                progressBar.setVisibility(View.INVISIBLE);
                if(noInternet){  webView.loadUrl("file:///android_asset/error.html");
                }
                noInternet=false;
            }

            @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                currentUrl = failingUrl;

                noInternet=true;

            }

            });//enable go forward and go back
        webView.loadUrl(url);
            webSettings.setAllowFileAccess(true);//extra
            webSettings.setAppCacheEnabled(true);//extra




        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(currentUrl);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            if(exit) {
                MainActivity.this.finish();
            }else {
                Toast.makeText(MainActivity.this, "برای خروج دوبار دکمه بازگشت را بزنید.", Toast.LENGTH_SHORT).show();
            }
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    exit=false;
                }
            },2000);
            exit=true;
        }
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}