package edu.usc.cesr.ema_uas.webview;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.usc.cesr.ema_uas.ui.MainActivity;

public class MyWebViewClient extends WebViewClient {
    private  MainActivity activity;

    public MyWebViewClient(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (activity.getDialog() != null && activity.getDialog().isShowing()) {
            activity.getDialog().dismiss();
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        //        Toast.makeText(getBaseContext(), "WebView Error",Toast.LENGTH_SHORT).show();
        // V155
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //  FirebaseCrash.report(new Exception("Webview Error:" + error.getDescription() + " at " + request.getUrl()));
        }
        super.onReceivedError(view, request, error);
    }

    /*
      Added in API level 23
    */
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        //      Toast.makeText(getBaseContext(), "WebView Error", Toast.LENGTH_SHORT).show();
        // V155
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //  FirebaseCrash.report(new Exception("Webview Error:" + errorResponse.getReasonPhrase()  + " at " + request.getUrl()));
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }
}