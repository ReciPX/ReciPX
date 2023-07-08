package com.recipx.recipx.PX_API;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recipx.recipx.R;

import java.util.List;

public class PX_Product_Adapter extends RecyclerView.Adapter<PX_Product_Adapter.MyViewHolder>{

    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    public PX_Product_Adapter(Context context, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public PX_Product_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.px_product_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PX_Product_Adapter.MyViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.image.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.px_product_title);//itemView = layout
            image = itemView.findViewById(R.id.px_product_src);
        }
    }
}
