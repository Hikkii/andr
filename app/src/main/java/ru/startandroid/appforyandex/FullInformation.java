package ru.startandroid.appforyandex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Михаил on 22.04.16.
 */
public class FullInformation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full);
        Intent intent = getIntent();
        String fullInf = intent.getStringExtra("artist");
        try {
            JSONObject workObj = new JSONObject(fullInf);
            LinearLayout item = (LinearLayout) findViewById(R.id.mainLay);
            TextView tvStyles = (TextView) item.findViewById(R.id.genres);
            String gengers = "";
            for (int j = 0; j != workObj.getJSONArray("genres").length(); ++j) {
                gengers += workObj.getJSONArray("genres").getString(j) + ", ";  // задание исполняемых жанров
            }
            tvStyles.setText(gengers.length() != 0 ? gengers.substring(0, gengers.length() - 2) : "");
            TextView tvAl_So = (TextView) item.findViewById(R.id.al_so);
            int amountOfAlbum = workObj.getInt("albums");
            int ammountOftracks = workObj.getInt("tracks");
            String album = (amountOfAlbum % 100 > 10 && amountOfAlbum % 100 < 15) ? "альбомов" :
                    (amountOfAlbum % 10 > 1 && amountOfAlbum % 10 < 5) ? "альбома" : amountOfAlbum % 10 == 1 ? "альбом" : "альбомов";
            String song = (ammountOftracks % 100 > 10 && ammountOftracks % 100 < 15) ? "песен" :
                    (ammountOftracks % 10 > 1 && ammountOftracks % 10 < 5) ? "песни" : ammountOftracks % 10 == 1 ? "песня" : "песен";
            tvAl_So.setText(amountOfAlbum + " " + album + ", " + ammountOftracks + " " + song); // вычисляются и задаются правильные оккончания
            WebView ivIco = (WebView) item.findViewById(R.id.pic);
            ivIco.loadUrl(workObj.getJSONObject("cover").getString("big"));  //загружается картинка
            TextView fullText = (TextView) item.findViewById(R.id.fullText);
            fullText.setText(workObj.getString("description"));  //загружается полная информация
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } catch (JSONException e) {
        }
    }
}
