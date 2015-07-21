package cn.sdu.bll;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;

import cn.sdu.utils.BitmapUtils;
import cn.sdu.utils.HttpUtils;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class ImageLoader {
	private Thread workThread;
	private boolean isLoop;
	private ArrayList<ImageLoadTask> tasks;
	private Handler handler;
	private HashMap<String, SoftReference<Bitmap>> caches;

	public ImageLoader(final Callback callback) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ImageLoadTask task = (ImageLoadTask) msg.obj;
				callback.imageLoaded(task.path, task.bitmap);
			}
		};
		isLoop = true;
		tasks = new ArrayList<ImageLoader.ImageLoadTask>();
		caches = new HashMap<String, SoftReference<Bitmap>>();
		workThread = new Thread() {
			@Override
			public void run() {
				// ��ѯ���񼯺�
				while (isLoop) {
					// ��ѯ����
					while (!tasks.isEmpty()) {
						try {
							// ��ȡ���Ƴ�һ������
							ImageLoadTask task = tasks.remove(0);
							// ִ��ͼƬ��������
							HttpEntity entity = HttpUtils.getEntity(HttpUtils.BASE_URL + task.path, null,
									HttpUtils.METHOD_GET);
							InputStream is = HttpUtils.getStream(entity);
							task.bitmap = BitmapUtils.loadBitmap(is, 4);

							// ����Ϣ�����߳�
							Message msg = Message.obtain();
							msg.obj = task;
							msg.what = 1;
							handler.sendMessage(msg);

							// ͼƬ�Ļ���
							// ���漯���л���ͼƬ��������
							caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
							// ��sd���л���
							String absPath = "/mnt/sdcard/" + task.path;
							BitmapUtils.save(task.bitmap, absPath);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// �������Ϊ�գ��̵߳ȴ�
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		workThread.start();
	}

	public Bitmap loadImage(String path) {
		
		Bitmap bm = null;
		// ����
		if (caches.containsKey(path)) {
			bm = caches.get(path).get();
			if (bm != null)
				return bm;
			else
				caches.remove(path);
		}

		// �ļ�������ж�
		bm = BitmapUtils.loadBitmap("/mnt/sdcard/" + path);
		if (bm != null)
			return bm;

		// ��ͼƬ����������ӵ�����
		ImageLoadTask task = new ImageLoadTask();
		task.path = path;
		if (!tasks.contains(task)) {
			tasks.add(task);
			synchronized (workThread) {
				try {
					workThread.notify();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	class ImageLoadTask {
		private String path;
		private Bitmap bitmap;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ImageLoadTask other = (ImageLoadTask) obj;

			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			return true;
		}

	}

	public interface Callback {
		void imageLoaded(String path, Bitmap bm);
	}
}
