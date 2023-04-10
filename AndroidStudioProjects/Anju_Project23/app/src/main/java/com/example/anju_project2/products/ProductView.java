package com.example.anju_project2.products;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anju_project2.R;

public class ProductView extends RecyclerView.ViewHolder {

    private TextView textView;
    private ImageView imageView;
    private View view;
    private TextView textView1;
    private TextView textView2;
    public TextView getTextView1() {
        return textView1;
    }

    public void setTextView1(TextView textView1) {
        this.textView1 = textView1;
    }

    public ProductView(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView9);
        imageView = itemView.findViewById(R.id.imageView);
        textView1 = itemView.findViewById(R.id.textView2);
        textView2 = itemView.findViewById(R.id.textView6);
        this.view = itemView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public View getView() { return this.view ;}

    public void setView(View view) {
        this.view = view;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public void setTextView2(TextView textView2) {
        this.textView2 = textView2;
    }
}
