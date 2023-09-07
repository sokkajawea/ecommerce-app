package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */



public class HomeFrag extends Fragment {
    public ArrayList<product> products = new ArrayList<>();
    dbConnect db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new dbConnect(getActivity());

        load10Prods();

        RecyclerView rvProducts = view.findViewById(R.id.rvIndex);
        prodRecyclerViewClass rv = new prodRecyclerViewClass(products, requireActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(rv);

        TextView txvCat1 = view.findViewById(R.id.txvCat1);
        TextView txvCat2 = view.findViewById(R.id.txvCat2);
        TextView txvCat3 = view.findViewById(R.id.txvCat3);
        TextView txvCat4 = view.findViewById(R.id.txvCat4);


        Cursor cursor = db.findCategory();
        if (cursor != null && cursor.moveToFirst()) {
            txvCat1.setText(cursor.getString(cursor.getColumnIndex(dbConnect.catName)));
            if (cursor.moveToNext()) {
                txvCat2.setText(cursor.getString(cursor.getColumnIndex(dbConnect.catName)));
            }
            if (cursor.moveToNext()) {
                txvCat3.setText(cursor.getString(cursor.getColumnIndex(dbConnect.catName)));
            }
            if (cursor.moveToNext()) {
                txvCat4.setText(cursor.getString(cursor.getColumnIndex(dbConnect.catName)));
            }
            cursor.close();
        }


        return view;
    }


    private void load10Prods() {
        products.clear();
            Cursor cursor = db.getProducts10();

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dbConnect.prodID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(dbConnect.prodName));
                    @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(dbConnect.prodPrice));
                    @SuppressLint("Range") String desc = cursor.getString(cursor.getColumnIndex(dbConnect.prodDescription));
                    product prod = new product(id, name, price, desc);
                    products.add(prod);

                    Log.d("Product", "ID: " + id + ", Name: " + name + ", Price: " + price + ", Description: " + desc);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
