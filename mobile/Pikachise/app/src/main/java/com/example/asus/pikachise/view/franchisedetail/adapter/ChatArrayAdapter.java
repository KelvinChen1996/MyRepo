package com.example.asus.pikachise.view.franchisedetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asus.pikachise.R;

import com.example.asus.pikachise.model.ChatMessage;

import java.util.List;

/**
 * Created by USER on 12/11/2017.
 */

public class ChatArrayAdapter extends RecyclerView.Adapter<ChatArrayAdapter.EventHolder> {
    List<ChatMessage> messagelist;
    private LayoutInflater inflater;
    Context context;
    public interface itemClickCallBack{
        void onItemClick(int p);
    }

    private ChatArrayAdapter.itemClickCallBack itemClickCallBack;

    public ChatArrayAdapter(List<ChatMessage> messagelist, Context c){
        this.messagelist = messagelist;
        this.inflater = LayoutInflater.from(c);
    }
    public void setitemclickcallback(final ChatArrayAdapter.itemClickCallBack itemclickcallback) {
        this.itemClickCallBack = itemclickcallback;
    }

    @Override
    public ChatArrayAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatArrayAdapter.EventHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public EventHolder(View itemView) {
            super(itemView);
        }
    }
}

