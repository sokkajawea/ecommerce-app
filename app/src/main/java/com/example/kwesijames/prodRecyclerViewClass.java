package com.example.kwesijames;

import static com.example.kwesijames.R.drawable.placeholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class prodRecyclerViewClass extends RecyclerView.Adapter<prodRecyclerViewClass.ViewHolder> {

    public ArrayList<product> arrayList;
    private FragmentManager fragmentManager;

    public prodRecyclerViewClass(ArrayList<product> dataSet, FragmentManager fragmentManager) {
        arrayList = dataSet;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public prodRecyclerViewClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prodlayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull prodRecyclerViewClass.ViewHolder holder, int position) {
        product prod = arrayList.get(position);
        holder.imgprod.setImageResource(R.drawable.placeholder);
        holder.txvProdName.setText(prod.getProdName());
        holder.txvProdPrice.setText("£" + prod.getPrice().toString());

        Log.d("array pos", String.valueOf(arrayList.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    product clickedProduct = arrayList.get(clickedPosition);
                    Fragment prodViewFragment = new prodViewFrag();

                    Bundle bundle = new Bundle();
                    bundle.putString("prodName", clickedProduct.getProdName());
                    bundle.putDouble("prodPrice", clickedProduct.getPrice());
                    bundle.putString("prodDescription", clickedProduct.getDescription());
                    prodViewFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, prodViewFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txvProdName, txvProdPrice;
        public final ImageView imgprod;

        public ViewHolder(View itemView) {
            super(itemView);
            txvProdName = (TextView) itemView.findViewById(R.id.txvProdName);
            txvProdPrice = (TextView) itemView.findViewById(R.id.txvProdPrice);
            imgprod = (ImageView) itemView.findViewById(R.id.imgprod);


        }

    }
}
