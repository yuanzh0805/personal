package cn.sdu.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import cn.sdu.domain.Novel;
import cn.sdu.utils.HttpUtils;
import cn.sdu.utils.JsonToBean;
import android.os.Handler;
import android.os.Message;

public class NovelBiz {
	private Handler handler;

	public NovelBiz(final Callback callback) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 更新ListView
				if(msg.what == 1){//收到成功消息
					ArrayList<Novel> novels = (ArrayList<Novel>) msg.obj;
					callback.updateUI(novels);
				}
			}
		};
	}

	/**
	 * 业务执行方法，该方法用于连接http服务端，并将返回的输入流解析为ArrayList<Music> 并将解析结果发送主线程处理
	 */
	public void execute() {
		new Thread() {
			public void run() {
				try {
					List<NameValuePair> list = new ArrayList<NameValuePair>();
					list.add(new BasicNameValuePair("method", "listNovel"));
					// 联网
					String result = cn.sdu.utils.HttpUtils.getNovel(HttpUtils.BASE_URL + "NovelServlet", list,
							HttpUtils.METHOD_GET);
					//InputStream is = HttpUtils.getStream(entity);
					// 解析xml
					//ArrayList<Music> musics = new MusicXmlParser().parse(is);
					// 发送消息到主线程
					if(!("").equals(result) && !("error").equals(result)){ 
						List<Novel> novels = JsonToBean.toListNovels(result);
                    	
						Message msg = Message.obtain();
						msg.obj = novels;
						msg.what = 1;
						handler.sendMessage(msg);
            	    }
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
					e.printStackTrace();
				}
			};
		}.start();
	}

	public interface Callback {
		void updateUI(ArrayList<Novel> novels);
	}
}
