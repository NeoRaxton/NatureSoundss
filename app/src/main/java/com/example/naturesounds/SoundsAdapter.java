package com.example.naturesounds;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SoundsAdapter extends RecyclerView.Adapter<SoundsAdapter.RviewHolder>{
    private ArrayList<SoundsModel> mSoundsModels;
    private OnNoteListener mOnNoteListener;
    Context mContext;
    public SoundsAdapter(Context context,ArrayList<SoundsModel> soundsModels,OnNoteListener mOnNoteListener)
    {
        this.mContext= context;
        this.mSoundsModels = soundsModels;
        this.mOnNoteListener = mOnNoteListener;
    }
    @NonNull
    @Override
    public RviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sounds,viewGroup,false);
        RviewHolder rvh= new RviewHolder(v, mOnNoteListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RviewHolder rviewHolder, int i) {
        SoundsModel current_sound = mSoundsModels.get(i);
        rviewHolder.imgview_icon.setImageResource(current_sound.getImgRes());
        rviewHolder.tv_title.setText(current_sound.getTitle());
    }
    

    @Override
    public int getItemCount() {
        return mSoundsModels.size();
    }

    public class RviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgview_icon;
        TextView tv_title;
        OnNoteListener mOnNoteListener;
        public RviewHolder(@NonNull View itemView,OnNoteListener mOnNoteListener) {
            super(itemView);
            imgview_icon =itemView.findViewById(R.id.imgView_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            this.mOnNoteListener = mOnNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnNoteListener.onNoteClick(v,mSoundsModels.get(getAdapterPosition()).getId());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(View view,int position);
    }
}
