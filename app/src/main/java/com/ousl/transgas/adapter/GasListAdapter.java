package com.ousl.transgas.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ousl.transgas.R;
import com.ousl.transgas.model.GasModel;

import java.util.List;

public class GasListAdapter extends RecyclerView.Adapter<GasListAdapter.MyViewHolder> {

    private List<GasModel> gasModelList;
    private GasListClickListener clickListener;

    public GasListAdapter(List<GasModel> gasModelList,GasListClickListener clickListener) {
        this.gasModelList = gasModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<GasModel> gasModelList) {
        this.gasModelList = gasModelList;
    }

    @NonNull
    @Override
    public GasListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GasListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.gasName.setText(gasModelList.get(position).getName());
        holder.gasAddress.setText("Location: " + gasModelList.get(position).getAddress());
        holder.gasDeliveryCharge.setText("Delivery Charge Rs." + gasModelList.get(position).getDelivery_charge()+"0");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(gasModelList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(gasModelList.get(position).getImage())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return gasModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gasName;
        TextView gasAddress;
        TextView gasDeliveryCharge;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            gasName = view.findViewById(R.id.gasName);
            gasAddress = view.findViewById(R.id.gasAddress);
            thumbImage = view.findViewById(R.id.thumbImage);
            gasDeliveryCharge = view.findViewById(R.id.gasDeliveryCharge);

        }
    }
    public interface GasListClickListener {
        public void onItemClick(GasModel gasModel);
    }
}
