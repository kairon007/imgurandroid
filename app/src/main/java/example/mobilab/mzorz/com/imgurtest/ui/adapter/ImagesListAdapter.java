package example.mobilab.mzorz.com.imgurtest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

import example.mobilab.mzorz.com.imgurtest.R;
import example.mobilab.mzorz.com.imgurtest.model.BaseModel;

public class ImagesListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<BaseModel> items;
	/**
	 * Constructor
	 *
	 * @param c Context of activity
	 */
	public ImagesListAdapter(Context c, List<BaseModel> items) {
		mContext = c;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
		
	}

	public void setItemsList(List<BaseModel> a_arrList){
		this.items = a_arrList;
	}

    public List<BaseModel> getItemsList(){
        return this.items;
    }
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return items.size();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final View view;
		holder holder;
		if (convertView == null) {
			view = inflater.inflate(R.layout.gallery_item, null);
			holder = new holder();
			holder.image = (ImageView) view.findViewById(R.id.image);
			holder.title = (TextView)  view.findViewById(R.id.name);
			view.setTag(holder);
			convertView = view;
		} else {
			holder = (holder) convertView.getTag();
		}
		
		final BaseModel item = (BaseModel) getItem(position);

        if (item.link != null)
            Picasso.with(mContext)
                    .load(item.link)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(holder.image);
        else
            holder.image.setImageResource(R.drawable.ic_launcher); //default placeholder


		return convertView;
		
	}


	private class holder {
		public ImageView image;
		public TextView title;
	}
	

}
