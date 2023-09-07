package com.example.kwesijames;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adminCategoryRvClass extends RecyclerView.Adapter<adminCategoryRvClass.ViewHolder> {

    public ArrayList<category> arrayList;
    private FragmentManager fragmentManager;
    public adminCategoryRvClass(ArrayList<category> dataSet, FragmentManager fragmentManager) {
        arrayList = dataSet;
        this.fragmentManager = fragmentManager;
    }

    dbConnect db;


    @NonNull
    @Override
    public adminCategoryRvClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_cat_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminCategoryRvClass.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        category cat = arrayList.get(position);
        holder.txvAdminCat.setText(arrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    category clickedCat = arrayList.get(clickedPosition);
                    Fragment alterCat = new alterCat();

                    Bundle bundle = new Bundle();
                    bundle.putString("catName", clickedCat.getTitle());
                    bundle.putInt("catId", clickedCat.getId());
                    alterCat.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, alterCat)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        holder.btnCatDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new dbConnect(view.getContext());
                boolean isDeleted = db.deleteCat(cat);
                if(isDeleted){
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(view.getContext(), "Category deleted", Toast.LENGTH_SHORT).show();
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

        public final TextView txvAdminCat;
        public final Button btnCatDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txvAdminCat = (TextView) itemView.findViewById(R.id.txvAdminCat);
            btnCatDelete = (Button) itemView.findViewById(R.id.btnCatDelete);
        }

    }
}
