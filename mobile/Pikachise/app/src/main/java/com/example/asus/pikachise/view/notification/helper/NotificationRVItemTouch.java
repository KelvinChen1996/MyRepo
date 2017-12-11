package com.example.asus.pikachise.view.notification.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.notification.adapter.NotificationRVAdapter;

/**
 * Created by WilliamSumitro on 18/11/2017.
 */

public class NotificationRVItemTouch extends ItemTouchHelper.SimpleCallback {
    private NotificationRVItemTouchListener listener;
    public NotificationRVItemTouch(int dragDirs, int swipeDirs, NotificationRVItemTouchListener listener){
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null){
            final View foregroundView = ((NotificationRVAdapter.ViewHolder) viewHolder).itemView.findViewById(R.id.itemnotification_foreground);
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NotificationRVAdapter.ViewHolder) viewHolder).itemView.findViewById(R.id.itemnotification_foreground);
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((NotificationRVAdapter.ViewHolder) viewHolder).itemView.findViewById(R.id.itemnotification_foreground);
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((NotificationRVAdapter.ViewHolder) viewHolder).itemView.findViewById(R.id.itemnotification_foreground);
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface NotificationRVItemTouchListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
