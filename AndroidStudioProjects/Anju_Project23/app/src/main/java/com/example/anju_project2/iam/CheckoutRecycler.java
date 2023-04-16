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
            holder.getTextView1().setText(arrayList.get(position).getName());
            holder.getTextView2().setText("Qty-"+arrayList.get(position).getQuantity());
            holder.getTextView3().setText("Size-"+arrayList.get(position).getSize());
            new Thread(new Pictures(position,holder.getImageView(),context)).start();
      }

      @Override
      public int getItemCount() {
            return arrayList.size();
      }

      class Pictures extends Thread {

            int i;
            ImageView imageView;
            Context context;

            Pictures(int pos, ImageView imageView, Context context) {
                  this.i = pos;
                  this.imageView = imageView;
                  this.context = context;
            }

            @Override
            public void run() {
                  RequestQueue requestQueue = Volley.newRequestQueue(context);
                  ImageRequest imageRequest = new ImageRequest(arrayList.get(i).getUrl(), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {

                              imageView.setImageBitmap(response);

                        }
                  }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                  });

                  requestQueue.add(imageRequest);

            }
      }

}
