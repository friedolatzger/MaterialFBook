package me.zeerooo.materialfb.WebView;

import android.net.UrlQuerySanitizer;
import android.util.Base64;
import android.webkit.WebView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JavaScriptHelpers {

    public static void updateNumsService(WebView view) {
        view.loadUrl("javascript:(function(){function n_s(){android.getNums(document.querySelector(\"#notifications_jewel > a > div > span[data-sigil=count]\").innerHTML,document.querySelector(\"#messages_jewel > a > div > span[data-sigil=count]\").innerHTML,document.querySelector(\"#requests_jewel > a > div > span[data-sigil=count]\").innerHTML,document.querySelector(\"#feed_jewel > a > div > span[data-sigil=count]\").innerHTML),setTimeout(n_s,5000)}try{n_s()}catch(_){}})()");
    }

    // Thanks to Simple for Facebook. - https://github.com/creativetrendsapps/SimpleForFacebook/blob/master/app/src/main/java/com/creativetrends/simple/app/helpers/BadgeHelper.java#L36
    public static void videoView(WebView view) {
        view.loadUrl("javascript:(function prepareVideo() { var el = document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;console.log(i);var jsonData = JSON.parse(el[i].dataset.store);el[i].setAttribute('onClick', 'Vid.LoadVideo(\"'+jsonData['src']+'\");');}}})()");
        view.loadUrl("javascript:( window.onload=prepareVideo;)()");
    }

    public static void paramLoader(WebView view, String url) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(url);
        String param = sanitizer.getValue("pageload");
        if (param != null) {
            switch (param) {
                case "composer":
                    view.loadUrl("javascript:(function(){try{document.querySelector('button[name=\"view_overview\"]').click()}catch(_){}})()");
                    break;
                case "composer_photo":
                    view.loadUrl("javascript:(function(){try{document.querySelector('button[name=\"view_photo\"]').click()}catch(_){}})()");
                    break;
                case "composer_checkin":
                    view.loadUrl("javascript:(function(){try{document.querySelector('button[name=\"view_location\"]').click()}catch(_){}})()");
                    break;
                default:
                    break;
            }
        }

    }

    public static void loadCSS(WebView view, String css) {
        try {
            InputStream inputStream = new ByteArrayInputStream(css.getBytes("UTF-8"));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            css = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + css + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
