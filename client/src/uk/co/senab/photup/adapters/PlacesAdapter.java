package uk.co.senab.photup.adapters;

import java.util.List;

import uk.co.senab.bitmapcache.BitmapLruCache;
import uk.co.senab.photup.PhotupApplication;
import uk.co.senab.photup.R;
import uk.co.senab.photup.model.Place;
import uk.co.senab.photup.util.Utils;
import uk.co.senab.photup.views.NetworkedCacheableImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlacesAdapter extends BaseAdapter {

	private final List<Place> mItems;
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private final BitmapLruCache mCache;

	public PlacesAdapter(Context context, List<Place> items) {
		mItems = items;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		mCache = PhotupApplication.getApplication(context).getImageCache();
	}

	public int getCount() {
		return null != mItems ? mItems.size() : 0;
	}

	public long getItemId(int position) {
		return position;
	}

	public Place getItem(int position) {
		return mItems.get(position);
	}

	public View getView(int position, View view, ViewGroup parent) {
		if (null == view) {
			view = mLayoutInflater.inflate(R.layout.item_list_places, parent, false);
		}

		final Place place = getItem(position);

		NetworkedCacheableImageView imageView = (NetworkedCacheableImageView) view.findViewById(R.id.iv_photo);
		imageView.loadImage(mCache, place.getAvatarUrl());

		TextView mTitle = (TextView) view.findViewById(R.id.tv_place_name);
		mTitle.setText(place.getName());

		StringBuffer sb = new StringBuffer();
		final int distance = place.getDistanceFromLocation();
		if (distance > 0) {
			sb.append(Utils.formatDistance(distance));
			sb.append(" - ");
		}
		sb.append(place.getCategory());

		TextView mDescription = (TextView) view.findViewById(R.id.tv_place_description);
		mDescription.setText(sb);

		return view;
	}

}
