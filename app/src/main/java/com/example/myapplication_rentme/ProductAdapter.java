package com.example.myapplication_rentme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication_rentme.model.ProductModel;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UsersAdapter_Holder> {
    Context context;
    List<ProductModel> list;


    public ProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public UsersAdapter_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, viewGroup, false);
        return new UsersAdapter_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter_Holder holder, final int position) {

        final ProductModel model = list.get(position);
        Glide.with(context).load(model.getImage()).into(holder.SelectedImage);
        holder.name.setText(model.getTitle());
        holder.price.setText(model.getPrice()+" $$");
        holder.description.setText(model.getDescription());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,ProductDetailActivity.class).putExtra("Product",model));
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UsersAdapter_Holder extends RecyclerView.ViewHolder {

        ImageView  SelectedImage;
        TextView name,price,description;
        Button details;



        public UsersAdapter_Holder(@NonNull View v) {
            super(v);

            SelectedImage = v.findViewById(R.id.Seat_No);
            name = v.findViewById(R.id.name);
            price = v.findViewById(R.id.price);
            description = v.findViewById(R.id.description);
            details = v.findViewById(R.id.details);

        }
    }
}