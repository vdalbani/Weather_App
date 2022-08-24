package com.example.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.databinding.CountryItemRowBinding;
import com.example.weatherapp.models.CountryItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private static final String TAG = "CountryAdapter";
    private final Context myContext;
    private final ArrayList<CountryItem> countryItemList;
    private final OnCountryItemClickListener itemClickListener;

    public CountryAdapter(Context context, ArrayList<CountryItem> countryItemList, OnCountryItemClickListener itemClickListener) {
        this.myContext = context;
        this.countryItemList = countryItemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(CountryItemRowBinding.inflate(LayoutInflater.from(myContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        final CountryItem currentCountry = countryItemList.get(position);
        holder.bind(myContext,currentCountry, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.countryItemList.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder{
        CountryItemRowBinding binding;

        public CountryViewHolder(CountryItemRowBinding cirb){
            super(cirb.getRoot());
            binding = cirb;
        }

        public void bind(Context context, final CountryItem currentItem, final OnCountryItemClickListener clickListener){
            String imgFlag = new String();

            //BIND THE VALUES TO THE LAYOUT
            binding.tvCountryName.setText(currentItem.getName());
            binding.tvCountryCapital.setText(currentItem.getCapital());

            //SET THE IMAGES
            imgFlag = "https://www.countryflags.io/" + currentItem.getCountryCode() + "/shiny/64.png";
            Picasso.get()
//                     .load(currentItem.getUrlFlag())//Change the images
                    .load(imgFlag)
                    .placeholder(ContextCompat.getDrawable(context, android.R.drawable.ic_media_next))
                    .into(binding.imgFlag);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    clickListener.onCountryItemClicked(currentItem);
                }
            });
        }
    }
}