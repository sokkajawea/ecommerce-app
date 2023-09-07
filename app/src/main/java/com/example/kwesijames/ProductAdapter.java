package com.example.kwesijames;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>  {

    public ArrayList<product> arrayList;
    private FragmentManager fragmentManager;

    public ProductAdapter(ArrayList<product> arrayList, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.fragmentManager = fragmentManager;
    }





    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prodlayout, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        product prod = arrayList.get(position);
        holder.imgprod.setImageResource(R.drawable.placeholder);
        holder.txvProdName.setText(arrayList.get(position).getProdName());
        holder.txvProdPrice.setText("£" + arrayList.get(position).getPrice().toString());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txvProdName, txvProdPrice;
        public final ImageView imgprod;

        public final TextView txvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            txvTitle = (TextView) itemView.findViewById(R.id.txvCatTitle);
            txvProdName = (TextView) itemView.findViewById(R.id.txvProdName);
            txvProdPrice = (TextView) itemView.findViewById(R.id.txvProdPrice);
            imgprod = (ImageView) itemView.findViewById(R.id.imgprod);

        }

    }
}
