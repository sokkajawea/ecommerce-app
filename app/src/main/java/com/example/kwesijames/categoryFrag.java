package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link categoryFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoryFrag extends Fragment {

    dbConnect db;

    private static final String ARG_CATEGORY_ID = "categoryId";
    private static final String ARG_CATEGORY_TITLE = "categoryTitle";
    private int categoryId;
    private String categoryTitle;

    public categoryFrag() {
        // Required empty public constructor
    }

    public static categoryFrag newInstance(int categoryId, String categoryTitle) {
        categoryFrag fragment = new categoryFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        args.putString(ARG_CATEGORY_TITLE, categoryTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
            categoryTitle = getArguments().getString(ARG_CATEGORY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        db = new dbConnect(getContext());

        TextView txvCategory = view.findViewById(R.id.txvCategory);
        RecyclerView rvCatProds = view.findViewById(R.id.rvCatProds);

        txvCategory.setText(categoryTitle);

        Cursor c = db.findCategoryById(categoryId);

        if (c.moveToFirst()) {
            @SuppressLint("Range") String categoryName = c.getString(c.getColumnIndex(db.catName));

            if (c.moveToFirst()) {

                Cursor productsCursor = db.findProductsByCategory(categoryName);
                ArrayList<product> productList = new ArrayList<>();


                while (productsCursor.moveToNext()) {
                    @SuppressLint("Range") int productId = productsCursor.getInt(productsCursor.getColumnIndex(db.prodID));
                    @SuppressLint("Range") String productName = productsCursor.getString(productsCursor.getColumnIndex(db.prodName));
                    @SuppressLint("Range") double productPrice = productsCursor.getDouble(productsCursor.getColumnIndex(db.prodPrice));
                    @SuppressLint("Range") String productDescription = productsCursor.getString(productsCursor.getColumnIndex(db.prodDescription));

                    product prod = new product(productId, productName, productPrice, productDescription);
                    productList.add(prod);
                }

                ProductAdapter productAdapter = new ProductAdapter(productList, requireActivity().getSupportFragmentManager());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                rvCatProds.setLayoutManager(linearLayoutManager);
                rvCatProds.setAdapter(productAdapter);
            }
        }

        return view;
    }

}
