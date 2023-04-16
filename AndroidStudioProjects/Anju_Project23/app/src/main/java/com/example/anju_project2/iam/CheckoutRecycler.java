package com.example.anju_project2.iam;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.anju_project2.R;
import com.example.anju_project2.products.ProductView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutRecycler extends RecyclerView.Adapter<CheckoutCardView> {



      Context context;
      ArrayList<OrderDto> arrayList;
      LayoutInflater layoutInflater;
      CheckoutRecycler(Context context , ArrayList<OrderDto> arrayList){
                this.arrayList = arrayList;
                this.context = context;
                this.layoutInflater = LayoutInflater.from(context);
      }

      @NonNull
      @Override
      public CheckoutCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.ordercard, parent, false);
            return new CheckoutCardView(view);
      }

      @Override
      public void onBindViewHolder(@NonNull CheckoutCardView holder, int position) {

            holder.getTextView().setText(arrayList.get(position).getName());
            holder.getTextView1().setText(arrayList.get(position).getTitle());
            holder.getTextView2().setText("Qty-"+arrayList.get(position).getQuantity());
            holder.getTextView3().setText("Size-"+arrayList.get(position).getSize());

            String url =  arrayList.get(position).getUrl();

            if(!arrayList.get(position).getUrl().contains("https")){
                  url = url.replaceFirst("http","https");
            }
            Picasso.get().load(url).placeholder(R.drawable.logo).into(holder.getImageView());
      }

      @Override
      public int getItemCount() {
            return arrayList.size();
      }



}
