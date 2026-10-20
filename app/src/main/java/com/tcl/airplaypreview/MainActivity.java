package com.tcl.airplaypreview;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
  private WebView web;
  private final class DeviceBridge {
    @JavascriptInterface public String getName() {
      String n = null;
      try { n = Settings.Global.getString(getContentResolver(), "device_name"); } catch(Exception ignored) {}
      if(n == null || n.trim().isEmpty()) try { n = Settings.Secure.getString(getContentResolver(), "bluetooth_name"); } catch(Exception ignored) {}
      if(n == null || n.trim().isEmpty()) n = Build.MODEL;
      return n == null || n.trim().isEmpty() ? "Android Projector" : n;
    }
  }
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    getWindow().getDecorView().setSystemUiVisibility(5894 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    web = new WebView(this);
    web.setBackgroundColor(0xff000000);
    web.getSettings().setJavaScriptEnabled(true);
    web.getSettings().setAllowFileAccess(true);
    web.addJavascriptInterface(new DeviceBridge(), "Projector");
    web.setWebViewClient(new WebViewClient());
    setContentView(web);
    web.loadUrl("file:///android_asset/index.html");
  }
  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    String action = null;
    switch(keyCode) {
      case KeyEvent.KEYCODE_DPAD_UP: action="up"; break;
      case KeyEvent.KEYCODE_DPAD_DOWN: action="down"; break;
      case KeyEvent.KEYCODE_DPAD_CENTER:
      case KeyEvent.KEYCODE_ENTER: action="select"; break;
      case KeyEvent.KEYCODE_BACK: action="back"; break;
      case KeyEvent.KEYCODE_DPAD_LEFT: action="left"; break;
      case KeyEvent.KEYCODE_DPAD_RIGHT: action="right"; break;
    }
    if(action != null) { web.evaluateJavascript("window.remote && window.remote('"+action+"')", null); return true; }
    return super.onKeyDown(keyCode,event);
  }
  @Override protected void onDestroy() { web.destroy(); super.onDestroy(); }
}
