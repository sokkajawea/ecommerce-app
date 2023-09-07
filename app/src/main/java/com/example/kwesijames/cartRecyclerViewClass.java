package com.example.kwesijames;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class cartRecyclerViewClass extends RecyclerView.Adapter<cartRecyclerViewClass.ViewHolder> {

    public List<cartItem> arrayList;
    private FragmentManager fragmentManager;


    public cartRecyclerViewClass(List<cartItem> arrayList, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public cartRecyclerViewClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartRecyclerViewClass.ViewHolder holder, int position) {

        cartItem cartItem = arrayList.get(position);

        holder.txvCartProdName.setText(arrayList.get(position).getProductName());
        holder.txvCartProdPrice.setText("£" + arrayList.get(position).getProductPrice().toString());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txvCartProdName, txvCartProdPrice;
        public final Button btnDeleteFromCart;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txvCartProdName = (TextView) itemView.findViewById(R.id.txvCartProdName);
            txvCartProdPrice = (TextView) itemView.findViewById(R.id.txvCartProdPrice);
            btnDeleteFromCart = (Button) itemView.findViewById(R.id.btnDeleteFromCart);
        }
    }

    }
