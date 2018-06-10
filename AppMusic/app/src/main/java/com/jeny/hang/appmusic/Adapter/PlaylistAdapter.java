package com.jeny.hang.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeny.hang.appmusic.Model.Playlist;
import com.jeny.hang.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    //lưu giá trị ánh xạ lần đầu tiên
    class ViewHolder{
        TextView txttenplaylist;
        ImageView imgbackground,imgplaylist;
    }
    //gắn các layout của item cho list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewholder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist,null);
            viewholder = new ViewHolder();
            viewholder.txttenplaylist = convertView.findViewById(R.id.textviewtenplaylist);
            viewholder.imgplaylist = convertView.findViewById(R.id.imageviewplaylist);
            viewholder.imgbackground = convertView.findViewById(R.id.imageviewbackgroundplaylist);
            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhNen()).into(viewholder.imgbackground);
        Picasso.with(getContext()).load(playlist.getHinhIcon()).into(viewholder.imgplaylist);
        viewholder.txttenplaylist.setText(playlist.getTen());
        return convertView;
    }
}
