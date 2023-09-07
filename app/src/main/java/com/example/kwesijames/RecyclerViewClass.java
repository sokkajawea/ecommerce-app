package com.example.kwesijames;


import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewClass extends RecyclerView.Adapter<RecyclerViewClass.ViewHolder> {

        public ArrayList<category>arrayList;
        private FragmentManager fragmentManager;
        public RecyclerViewClass(ArrayList<category> dataSet, FragmentManager fragmentManager) {
        arrayList = dataSet;
        this.fragmentManager = fragmentManager;
    }


        @NonNull
        @Override
        public RecyclerViewClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.categorylayout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewClass.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            category cat = arrayList.get(position);
            holder.txvTitle.setText(arrayList.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category clickedCategory = arrayList.get(position);
                    int categoryId = clickedCategory.getId();
                    String categoryTitle = clickedCategory.getTitle();

                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryId", categoryId);
                    bundle.putString("categoryTitle", categoryTitle);

                    Fragment fragment = categoryFrag.newInstance(categoryId, categoryTitle);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }



    @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView txvTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                txvTitle = (TextView) itemView.findViewById(R.id.txvCatTitle);

            }

        }
}
