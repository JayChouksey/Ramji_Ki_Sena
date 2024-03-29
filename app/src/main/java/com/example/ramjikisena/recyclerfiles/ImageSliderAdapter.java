package com.example.ramjikisena.recyclerfiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ramjikisena.R;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private List<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageSliderAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.activity_gallery, container, false);
        ImageView imageView = imageLayout.findViewById(R.id.imageView);
        imageView.setImageResource(images.get(position));
        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
