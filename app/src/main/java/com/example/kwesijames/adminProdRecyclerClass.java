package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adminProdRecyclerClass extends RecyclerView.Adapter<adminProdRecyclerClass.ViewHolder> {

    public ArrayList<product> arrayList;
    private FragmentManager fragmentManager;

    public adminProdRecyclerClass(ArrayList <product> dataSet, FragmentManager fragmentManager) {
        arrayList = dataSet;
        this.fragmentManager = fragmentManager;
    }

    dbConnect db;

    @NonNull
    @Override
    public adminProdRecyclerClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminprodlayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminProdRecyclerClass.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        product prod = arrayList.get(position);
        holder.imgprod.setImageResource(R.drawable.placeholder);
        holder.txvProdName.setText(prod.getProdName());
        holder.txvProdPrice.setText("£" + prod.getPrice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    product clickedProduct = arrayList.get(clickedPosition);
                    Fragment alterProd = new alterProd();

                    Bundle bundle = new Bundle();
                    bundle.putInt("prodId", clickedProduct.getId());
                    bundle.putString("prodName", clickedProduct.getProdName());
                    bundle.putDouble("prodPrice", clickedProduct.getPrice());
                    bundle.putString("prodDescription", clickedProduct.getDescription());
                    bundle.putDouble("prodListPrice", clickedProduct.getListPrice());
                    bundle.putDouble("prodRetailPrice", clickedProduct.getRetailPrice());
                    bundle.putString("prodCategory", clickedProduct.getCategory());

                    alterProd.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, alterProd)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        holder.btnDeleteProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new dbConnect(view.getContext());
                boolean isDeleted = db.deleteProd(prod.getId());
                if(isDeleted){
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(view.getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), "Unable to delete", Toast.LENGTH_SHORT).show();
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
        public final Button btnDeleteProd;


        public ViewHolder(View itemView) {
            super(itemView);
            txvProdName = (TextView) itemView.findViewById(R.id.txvProdName);
            txvProdPrice = (TextView) itemView.findViewById(R.id.txvProdPrice);
            imgprod = (ImageView) itemView.findViewById(R.id.imgprod);
            btnDeleteProd = (Button) itemView.findViewById(R.id.btnDeleteProd);


        }

    }
}
