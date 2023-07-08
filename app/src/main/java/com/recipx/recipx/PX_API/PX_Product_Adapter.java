package com.recipx.recipx.PX_API;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recipx.recipx.R;

import java.util.ArrayList;
import java.util.List;

public class PX_Product_Adapter extends RecyclerView.Adapter<PX_Product_Adapter.MyViewHolder>{
    Context context;
    LayoutInflater inflater;
    ArrayList<PX_Product> PX_Products;
    public PX_Product_Adapter(Context context, ArrayList<PX_Product> PX_Products){
        this.context = context;
        this.PX_Products = PX_Products;
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
        String title = PX_Products.get(position).getTitle();
        holder.title.setText(title);
        Drawable drawable=null;
        String imageURL = "no";
        if(title.contains("테라")) {
            drawable = context.getResources().getDrawable(R.drawable.terra);
        }
        else if(title.contains("카스")) {
            drawable = context.getResources().getDrawable(R.drawable.cass);
        }
        else if(title.contains("참이슬")) {
            drawable = context.getResources().getDrawable(R.drawable.fresh);
        }
        else{
            imageURL = PX_Products.get(position).getSrc();
        }
        if(imageURL.equals("no")){
            Glide.with(context)
                    .load(drawable)
                    .into(holder.image);
        }
        else{
            Glide.with(holder.itemView.getContext())
                    .load(imageURL)
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return PX_Products.size();
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
