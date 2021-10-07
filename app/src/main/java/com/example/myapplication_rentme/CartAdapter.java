package com.example.myapplication_rentme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication_rentme.Database.CartDbHelper;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartProductModel> list;
    Context context;

    public CartAdapter(List<CartProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CartProductModel model = list.get(position);

        holder.foodName.setText(model.getProductName());

        Glide.with(context).load(model.getProductImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                holder.avi.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                holder.avi.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.foodImage);

        double price = Double.valueOf(model.getProductPrice()) * Integer.valueOf(model.getQuantity());

        holder.foodPrice.setText(model.getProductPrice() +" * " + model.getQuantity() + " = " + String.valueOf(price) + " $$");

//        holder.foodPrice.setText("Rs " + model.getProductPrice());

        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] colors = {"Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if (which == 0) {
                            CartDbHelper dbHelper = new CartDbHelper(context);
                            dbHelper.deleteANote(model);
                            notifyItemRemoved(position);
                            CartActivity.getCartActivity().recallOnResume();
                        }
                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView foodName, foodPrice;
        ImageView foodImage;
        ProgressBar avi;
        ImageView settingBtn;

        CardView bgCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.food_name_cart_card);
            foodPrice = itemView.findViewById(R.id.price_text_cart_card);
            foodImage = itemView.findViewById(R.id.food_image_cart_card);
            avi = itemView.findViewById(R.id.avi_cart_card);
            settingBtn = itemView.findViewById(R.id.sett_btn_cart_card);
        }
    }
}
