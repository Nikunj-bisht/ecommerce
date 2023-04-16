package com.example.anju_project2.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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


import com.cloudinary.android.MediaManager;
import com.example.anju_project2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductView> {

    public interface ProductCallback {

       void getProductDetails(String id);
       void deleteProducct(String id);

    }
    private ArrayList<ClothingDto> clothingDtoArrayList;
    LayoutInflater layoutInflater;
    Context context;

    ProductCallback callback;
    SharedPreferences sharedPref ;


    public ProductRecyclerAdapter(ArrayList<ClothingDto> clothingDtoArrayList, Context context,ProductCallback productCallback) {
        this.clothingDtoArrayList = clothingDtoArrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.callback = productCallback;

        this.sharedPref = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.prductcard, parent, false);
        return new ProductView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView holder, @SuppressLint("RecyclerView") int position) {
        holder.getTextView().setText(clothingDtoArrayList.get(position).getName());
        holder.getTextView2().setText(clothingDtoArrayList.get(position).getTitle());
        holder.getView().setOnClickListener(click -> callback.getProductDetails(clothingDtoArrayList.get(position).getId()));
        holder.getTextView1().setText(clothingDtoArrayList.get(position).getPrice());
        if (sharedPref.getString("userId", "").equals("3RxOxSuH6UBqqLOWXV37")) {
            holder.getImageButton().setImageResource(R.drawable.baseline_delete_24);
        } else {
            holder.getImageButton().setImageResource(R.drawable.baseline_shopping_bag_24);
        }
        holder.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(sharedPref.getString("userId", "").equals("3RxOxSuH6UBqqLOWXV37")){

                          callback.deleteProducct(clothingDtoArrayList.get(position).getId());

                  }
            }
        });
        final int i = position;
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callback.getProductDetails(clothingDtoArrayList.get(position).getId());
            }
        });
        String url =  clothingDtoArrayList.get(i).getUrl();

        if(!clothingDtoArrayList.get(i).getUrl().contains("https")){
            url = url.replaceFirst("http","https");
        }
           Picasso.get().setLoggingEnabled(true);
            Picasso.get().load(url).placeholder(R.drawable.logo).resize(204,274).into(holder.getImageView());

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
            if(clothingDtoArrayList.get(i).getUrl().contains("cloudinary")){

                Picasso.get().load(clothingDtoArrayList.get(i).getUrl()).into(imageView);
            }else{
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

}
