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
import example.mobilab.mzorz.com.imgurtest.model.Image;
import example.mobilab.mzorz.com.imgurtest.ui.MainActivity;

public class ImagesListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<BaseModel> items;
    private int MODE = 0;
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
            if (MODE == MainActivity.MODE_GRID)
			    view = inflater.inflate(R.layout.gallery_item_grid, null);
            else if (MODE == MainActivity.MODE_LIST)
                view = inflater.inflate(R.layout.gallery_item_list, null);
            else
                view = inflater.inflate(R.layout.gallery_item_grid, null);
			holder = new holder();
			holder.image = (ImageView) view.findViewById(R.id.image);
			holder.title = (TextView)  view.findViewById(R.id.name);
			view.setTag(holder);
			convertView = view;
		} else {
			holder = (holder) convertView.getTag();
		}
		
		final BaseModel item = (BaseModel) getItem(position);

        holder.title.setText(item.title);

        if (item instanceof Image){
            if (item.link != null)
                Picasso.with(mContext)
                        .load(getThumbnailURL(item.link))
                        .into(holder.image);
            else
                holder.image.setImageBitmap(null);
        }
        else // in case this is an album, just provide a placeholder
            holder.image.setImageBitmap(null);


		return convertView;
		
	}

    public void setMode(int mode){
        this.MODE = mode;
    }

    private String getThumbnailURL(String original){
        //read section "Image thumbnails" here https://api.imgur.com/models/image
        String thumb = original;
        if (original != null){
            String ext = original.substring(original.length()-4, original.length());
            thumb = original.substring(0, original.length()-4);
            thumb  = thumb + "m" + ext;
        }
        return thumb;
    }

	private class holder {
		public ImageView image;
		public TextView title;
	}
	

}
