package com.ousl.transgas.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ousl.transgas.R;
import com.ousl.transgas.model.GasModel;
import com.ousl.transgas.model.Menu;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener) {
        this.menuList = menuList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Rs." + menuList.get(position).getPrice()+"0");
        holder.menuRating.setText("★" + menuList.get(position).getRating());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu  = menuList.get(position);
                menu.setTotalInCart(1);
                clickListener.onAddToCartClick(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getTotalInCart()+"");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onAddToCartClick(menuList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView menuPrice;
        TextView menuRating;
        TextView addToCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView  tvCount;
        LinearLayout addMoreLayout;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            menuRating = view.findViewById(R.id.menuRating);
            thumbImage = view.findViewById(R.id.thumbImage);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);

            addMoreLayout  = view.findViewById(R.id.addMoreLayout);

        }
    }
    public interface MenuListClickListener {
        public void onAddToCartClick(Menu menu);
    }
}
