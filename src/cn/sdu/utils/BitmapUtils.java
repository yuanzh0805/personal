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
	 * 从输入流加载位图
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
	 * 从文件加载位图
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
	 * 保存位图到文件
	 * 
	 * @param bm
	 * @param absPath
	 * @throws IOException
	 */
	public static void save(Bitmap bm, String absPath) throws IOException {
		if (bm != null && absPath != null) {
			File file = new File(absPath);
			// 如果图片所在目录不存在，创建
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			// 如果文件不存在，创建
			if (!file.exists()) {
				file.createNewFile();
			}
			// 创建指向文件的输出流
			FileOutputStream stream = new FileOutputStream(file);
			// 保存位图对象，到指定文件
			bm.compress(CompressFormat.JPEG, 100, stream);
		}
	}

}
