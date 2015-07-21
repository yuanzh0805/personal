package cn.sdu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;

public class BitmapUtils {
	/**
	 * ������������λͼ
	 * 
	 * @param is
	 * @param inSampleSize
	 * @return
	 */
	public static Bitmap loadBitmap(InputStream is, int inSampleSize) {
		Bitmap bm = null;
		if (is != null) {
			Options opts = new Options();
			opts.inSampleSize = inSampleSize;
			bm = BitmapFactory.decodeStream(is, null, opts);
		}
		return bm;
	}

	/**
	 * ���ļ�����λͼ
	 * 
	 * @param absPath
	 * @return
	 */
	public static Bitmap loadBitmap(String absPath) {
		Bitmap bm = null;
		if (absPath != null) {
			bm = BitmapFactory.decodeFile(absPath);
		}
		return bm;
	}

	/**
	 * ����λͼ���ļ�
	 * 
	 * @param bm
	 * @param absPath
	 * @throws IOException
	 */
	public static void save(Bitmap bm, String absPath) throws IOException {
		if (bm != null && absPath != null) {
			File file = new File(absPath);
			// ���ͼƬ����Ŀ¼�����ڣ�����
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			// ����ļ������ڣ�����
			if (!file.exists()) {
				file.createNewFile();
			}
			// ����ָ���ļ��������
			FileOutputStream stream = new FileOutputStream(file);
			// ����λͼ���󣬵�ָ���ļ�
			bm.compress(CompressFormat.JPEG, 100, stream);
		}
	}

}
