package com.example.anju_project2.products;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anju_project2.R;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductView> {

    private ArrayList<ClothingDto> clothingDtoArrayList;
    LayoutInflater layoutInflater;
    Context context;

    public ProductRecyclerAdapter(ArrayList<ClothingDto> clothingDtoArrayList, Context context) {
        this.clothingDtoArrayList = clothingDtoArrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.prductcard, parent, false);
        return new ProductView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView holder, int position) {
        holder.getTextView().setText(clothingDtoArrayList.get(position).getName());
        holder.getTextView1().setText("$ "+clothingDtoArrayList.get(position).getPrice());
        final int i = position;
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                cll.touch(i);

            }
        });
        new Pictures(position, holder.getImageView(),context).start();
    }

    @Override
    public int getItemCount() {
        return clothingDtoArrayList.size();

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
            ImageRequest imageRequest = new ImageRequest(clothingDtoArrayList.get(i).getUrl(), new Response.Listener<Bitmap>() {
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
