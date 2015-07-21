package cn.sdu.adapter;

import java.util.ArrayList;

import cn.sdu.bll.ImageLoader;
import cn.sdu.bll.ImageLoader.Callback;
import cn.sdu.client.R;
import cn.sdu.domain.Novel;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NovleAdapter extends BaseAdapter {
	private ArrayList<Novel> novlels;
	private LayoutInflater inflater;
	private ImageLoader loader;

	public NovleAdapter(Context context, ArrayList<Novel> novlels, final ListView lvNovlels) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		if (novlels != null)
			this.novlels = novlels;
		else
			this.novlels = new ArrayList<Novel>();
		loader =  new ImageLoader(new Callback() {
			@Override
			public void imageLoaded(String path, Bitmap bm) {
				ImageView iv = (ImageView) lvNovlels.findViewWithTag(path);
				if (iv != null) {
					if (bm != null) {
						iv.setImageBitmap(bm);
					} else {
						iv.setImageResource(R.drawable.ic_launcher);
					}
				}
			}
		});

	}

	public void update(ArrayList<Novel> novlels) {
		if (novlels != null) {
			this.novlels.addAll(novlels);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return novlels.size();
	}

	@Override
	public Novel getItem(int position) {
		// TODO Auto-generated method stub
		return novlels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 加载或复用item界面
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_music, null);
			holder = new ViewHolder();
			holder.ivAlbum = (ImageView) convertView.findViewById(R.id.ivAlbum);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvAlbum = (TextView) convertView.findViewById(R.id.tvAlbum);
			holder.tvDuration = (TextView) convertView.findViewById(R.id.tvId);
			holder.tvSinger = (TextView) convertView.findViewById(R.id.tvSinger);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 取数据
		Novel m = getItem(position);
		// 绑定数据
		holder.tvName.setText(m.getName());
		holder.tvAlbum.setText(m.getType());
		holder.tvDuration.setText(m.getZishu());
		holder.tvSinger.setText(m.getAuthor());

		// 为图片框设置 tag
		holder.ivAlbum.setTag(m.getPicpath());
		Bitmap bm = loader.loadImage(m.getPicpath());
		if (bm != null) {
			holder.ivAlbum.setImageBitmap(bm);
		} else {
			holder.ivAlbum.setImageResource(R.drawable.ic_launcher);
		}

		// 返回item
		return convertView;
	}

	class ViewHolder {
		private ImageView ivAlbum;
		private TextView tvName;
		private TextView tvAlbum;
		private TextView tvDuration;
		private TextView tvSinger;
	}

}
