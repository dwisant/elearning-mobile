package dwi.newthinkandroid.com.desamembangun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public WebView webView;
    SwipeRefreshLayout mSwipeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        CekKoneksi(webView);
        mSwipeLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);

        mSwipeLayout.setOnRefreshListener(this);


//        LoadIcon(webView);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack() == true) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }



    public void LoadIcon(View view) {
//        swipeLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                findViewById(R.id.progress1).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.progress1).setVisibility(View.GONE);
//                swipeLayout.setRefreshing(false);
            }
        });
    }


    public void CekKoneksi(final View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            CekKoneksiLagi(view);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    this);

            alertDialog.setTitle("Tidak Ada Jaringan Internet");

            alertDialog.setMessage("Aplikasi ini membutuhkan jaringan internet, atur jaringan ?");

            alertDialog.setPositiveButton("Iya",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
                        }
                    });

            alertDialog.setNegativeButton("Tidak",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            CekKoneksiLagi(view);

                        }
                    });

            alertDialog.show();
        }
    }


    public void CekKoneksiLagi(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            webView.loadUrl("http://122.200.145.178");
            webView.setWebViewClient(new WebViewClient());
        } else {

            Toast.makeText(MainActivity.this, "Internet Tidak Terhubung", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }
}
