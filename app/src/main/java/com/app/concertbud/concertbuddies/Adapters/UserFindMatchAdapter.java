package com.app.concertbud.concertbuddies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.concertbud.concertbuddies.Networking.Responses.UserResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.UserCardViewHolder;

import java.util.ArrayList;

/**
 * Created by hungnguyen on 4/6/18.
 */

public class UserFindMatchAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<UserResponse> mList;

    public UserFindMatchAdapter(Context mContext, ArrayList<UserResponse> mList) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.swipe_fling_item, parent, false);
            UserCardViewHolder holder = new UserCardViewHolder(convertView);
            convertView.setTag(holder);
        }

        UserCardViewHolder holder = (UserCardViewHolder) convertView.getTag();
        UserResponse response = mList.get(position);
        holder.initView(response);

        return convertView;
    }
}
