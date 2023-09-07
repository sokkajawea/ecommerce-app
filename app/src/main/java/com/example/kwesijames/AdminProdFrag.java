package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Date;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProdFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProdFrag extends Fragment {

    public ArrayList<product> products = new ArrayList<>();
    dbConnect db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminProdFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProdFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProdFrag newInstance(String param1, String param2) {
        AdminProdFrag fragment = new AdminProdFrag();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_prod, container, false);
        db = new dbConnect(getActivity());

        Button btnAddProd = view.findViewById(R.id.btnAddProd);

        loadProducts();

        RecyclerView rvProds = view.findViewById(R.id.rvAdminProds);
        adminProdRecyclerClass rv = new adminProdRecyclerClass(products, requireActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvProds.setLayoutManager(linearLayoutManager);
        rvProds.setAdapter(rv);

        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                Fragment alterProd = new alterProd();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, alterProd)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @SuppressLint("Range")
    private void loadProducts() {
        products.clear();
        Cursor cursor = db.getAllProds();

        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(db.prodID));
                String productName = cursor.getString(cursor.getColumnIndex(db.prodName));
                double productPrice = cursor.getDouble(cursor.getColumnIndex(db.prodPrice));
                String productDescription = cursor.getString(cursor.getColumnIndex(db.prodDescription));
                double listPrice = cursor.getDouble(cursor.getColumnIndex(db.prodListPrice));
                double retailPrice = cursor.getDouble(cursor.getColumnIndex(db.prodRetailPrice));
                String prodCategory = cursor.getString(cursor.getColumnIndex(db.prodCategory));

                product prod = new product(productId, productName, productPrice, productDescription, listPrice, retailPrice, prodCategory);
                products.add(prod);

                Log.d("ProductDetails", "ID: " + productId);
                Log.d("ProductDetails", "Name: " + productName);
                Log.d("ProductDetails", "Price: " + productPrice);
                Log.d("ProductDetails", "Description: " + productDescription);
                Log.d("ProductDetails", "List Price: " + listPrice);
                Log.d("ProductDetails", "Retail Price: " + retailPrice);
                Log.d("ProductDetails", "Category: " + prodCategory);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

}