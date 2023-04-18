package com.example.anju_project2.iam;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anju_project2.R;

public class CheckoutCardView extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView, textView1, textView2, textView3, textView4;
   ImageButton imageButton;
    public CheckoutCardView(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView5);
        textView = itemView.findViewById(R.id.textView7);
        textView1 = itemView.findViewById(R.id.textView8);
        textView2 = itemView.findViewById(R.id.textView10);
        textView3 = itemView.findViewById(R.id.textView11);
        imageButton = itemView.findViewById(R.id.imageButton2);

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public TextView getTextView1() {
        return textView1;
    }

    public void setTextView1(TextView textView1) {
        this.textView1 = textView1;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public void setTextView2(TextView textView2) {
        this.textView2 = textView2;
    }

    public TextView getTextView3() {
        return textView3;
    }

    public void setTextView3(TextView textView3) {
        this.textView3 = textView3;
    }

    public TextView getTextView4() {
        return textView4;
    }

    public void setTextView4(TextView textView4) {
        this.textView4 = textView4;
    }
}
