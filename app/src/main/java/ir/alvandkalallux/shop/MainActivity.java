package ir.alvandkalallux.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    private boolean exit = false;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.MainActivityWebView);

        if(isOnline()){

            String url = "https://alvandkalalux.ir/"; //Enter your url here
            webView.loadUrl(url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);//enable menu works
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setWebViewClient(new WebViewClient());//enable go forward and go back
            webSettings.setAllowFileAccess(true);//extra
            webSettings.setAppCacheEnabled(true);//extra
        }
        else {
            Toast.makeText(this, "اینترنت وصل نیست...", Toast.LENGTH_LONG).show();

        }


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