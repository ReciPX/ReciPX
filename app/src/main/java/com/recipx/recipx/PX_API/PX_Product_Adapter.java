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

import java.util.ArrayList;
import java.util.List;

public class PX_Product_Adapter extends RecyclerView.Adapter<PX_Product_Adapter.MyViewHolder>{
    LayoutInflater inflater;
    ArrayList<PX_Product> PX_Products;
    public PX_Product_Adapter(Context context, ArrayList<PX_Product> PX_Products){
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
        holder.title.setText(PX_Products.get(position).getTitle());
        holder.image.setImageResource(PX_Products.get(position).getSrc());
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
