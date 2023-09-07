package com.example.kwesijames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link alterCat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alterCat extends Fragment {

    private static final String ARG_CAT_NAME = "catName";
    private static final String ARG_CAT_ID = "catId";

    private String catName;
    private int catId;

    dbConnect db;

    public alterCat() {
        // Required empty public constructor
    }

    public static alterCat newInstance(String catName, int catId) {
        alterCat fragment = new alterCat();
        Bundle args = new Bundle();
        args.putString(ARG_CAT_NAME, catName);
        args.putInt(ARG_CAT_ID, catId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            catName = getArguments().getString(ARG_CAT_NAME);
            catId = getArguments().getInt(ARG_CAT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alter_cat, container, false);

        TextView txvCatIntro = view.findViewById(R.id.txvCatIntro);
        EditText edtCatName = view.findViewById(R.id.edtCatName);
        Button btnSaveCat = view.findViewById(R.id.btnSaveCat);


        if (catName != null) {
            txvCatIntro.setText("Edit a Category");
            edtCatName.setText(catName);
            String catTitle = edtCatName.getText().toString();
            category c1 = new category(catId, catTitle);
            btnSaveCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String updatedCatName = edtCatName.getText().toString();
                    category updatedCategory = new category(catId, updatedCatName);
                    db = new dbConnect(getContext());
                    db.updateCat(updatedCategory);
                    Toast.makeText(getContext(), "Category updated!", Toast.LENGTH_SHORT).show();
                }
            });




        } else {
            txvCatIntro.setText("Add a Category");
            btnSaveCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String catTitle = edtCatName.getText().toString();
                    category newCategory = new category(catTitle);
                    db = new dbConnect(getContext());
                    db.addCat(newCategory);
                    Toast.makeText(getContext(), "Category added!", Toast.LENGTH_SHORT).show();
                }
            });


        }


        return view;
    }
}
