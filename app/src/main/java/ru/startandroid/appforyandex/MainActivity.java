package ru.startandroid.appforyandex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str = null;
        try {
            str = new forNetThread().execute("http://cache-default02g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json").get();
        } catch (InterruptedException e) {                  // получение JSON
        } catch (ExecutionException e) {
        }
        final LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater ltInflater = getLayoutInflater();
        try {
            final JSONArray arr = new JSONArray(str); // разбор JSON
            for (int i = 0; i != arr.length(); ++i) {  // создание списка всех исполнителей
                final JSONObject workObj = arr.getJSONObject(i);
                View item = ltInflater.inflate(R.layout.item, linLayout, false);
                TextView tvName = (TextView) item.findViewById(R.id.artist_name);  // задание имени исполнителя
                tvName.setText(workObj.getString("name"));
                TextView tvStyles = (TextView) item.findViewById(R.id.styles);
                String gengers = "";
                for (int j = 0; j != workObj.getJSONArray("genres").length(); ++j) {
                    gengers += workObj.getJSONArray("genres").getString(j) + ", ";           // задание исполняемых жанров
                }
                tvStyles.setText(gengers.length() != 0 ? gengers.substring(0, gengers.length() - 2) : "");
                TextView tvAl_So = (TextView) item.findViewById(R.id.album_song);
                int amountOfAlbum = workObj.getInt("albums");
                int ammountOftracks = workObj.getInt("tracks");
                String album = (amountOfAlbum % 100 > 10 && amountOfAlbum % 100 < 15) ? "альбомов" :
                        (amountOfAlbum % 10 > 1 && amountOfAlbum % 10 < 5) ? "альбома" : amountOfAlbum % 10 == 1 ? "альбом" : "альбомов";
                String song = (ammountOftracks % 100 > 10 && ammountOftracks % 100 < 15) ? "песен" :
                        (ammountOftracks % 10 > 1 && ammountOftracks % 10 < 5) ? "песни" : ammountOftracks % 10 == 1 ? "песня" : "песен";
                tvAl_So.setText(amountOfAlbum + " " + album + ", " + ammountOftracks + " " + song); // вычисляются и задаются правильные оккончания
                WebView ivIco = (WebView) item.findViewById(R.id.artists_img);
                ivIco.loadUrl(workObj.getJSONObject("cover").getString("small")); //загружается картинка
                item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                final MainActivity forFunc = this;
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(forFunc, FullInformation.class);
                        try {
                            intent.putExtra("artist", arr.getJSONObject(linLayout.indexOfChild(v)).toString());
                            startActivity(intent);          //задается обработчик вызывающий подробную информацию
                        } catch (JSONException e) {
                        }
                        startActivity(intent);
                    }
                });
                linLayout.addView(item);
            }
        } catch (JSONException e) {
        }
    }
}
