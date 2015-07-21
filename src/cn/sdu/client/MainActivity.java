package cn.sdu.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import cn.sdu.bll.NovelBiz;
import cn.sdu.bll.NovelBiz.Callback;
import cn.sdu.client.R;
import cn.sdu.domain.Novel;
import cn.sdu.adapter.NovleAdapter;


public class MainActivity extends Activity {
    private ListView lvData;

    private NovleAdapter adapter;

//    private ArrayList<Music> getData() {
//		ArrayList<Music> musics = null;
//		return musics;
//	}

    private void setupView() {
        //鑾峰彇listview鐨勫紩鐢�?
//    	lvData = (ListView)findViewById(R.id.lvData);
//        ArrayList<Music> musics = getData();
//        adapter = new MusicAdapter(this,musics);
//        lvData.setAdapter(adapter);
        lvData = (ListView) findViewById(R.id.lvData);
		adapter = new NovleAdapter(this, null,lvData);
		lvData.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        NovelBiz biz = new NovelBiz(new Callback() {
			
			@Override
			public void updateUI(ArrayList<Novel> novels) {
				adapter.update(novels);
			}
		});
		biz.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
