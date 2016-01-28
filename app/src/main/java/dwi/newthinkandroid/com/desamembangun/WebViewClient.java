package dwi.newthinkandroid.com.desamembangun;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

/**
 * Created by Dwi on 1/13/2016.
 */
public class WebViewClient extends android.webkit.WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (Uri.parse(url).getHost().endsWith("122.200.145.178")){
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);

        return true;
    }
}
