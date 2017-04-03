package com.allebd.dispatchsystem.ui.fragment.profile.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allebd.dispatchsystem.R;
import com.allebd.dispatchsystem.data.model.RequestObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Digz on 02/04/2017.
 */

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    ArrayList<RequestObject> requests;

    public RequestListAdapter(ArrayList<RequestObject> requests) {
        this.requests = requests;
    }

    public RequestObject getItem(int position) {
        return requests.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RequestObject request = getItem(position);

        holder.hospitalName.setText(request.getHospitalId());
        holder.requestStatus.setText(request.getStatusText());
        holder.requestDate.setText(request.getDateString());

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hospitalName, requestStatus, requestDate;

        ViewHolder(View itemView) {
            super(itemView);
            hospitalName = (TextView) itemView.findViewById(R.id.hospitalName);
            requestStatus = (TextView) itemView.findViewById(R.id.requestStatus);
            requestDate = (TextView) itemView.findViewById(R.id.requestDate);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }


    }
}


