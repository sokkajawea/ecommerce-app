package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminCatFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminCatFrag extends Fragment {

    public ArrayList<category> categories = new ArrayList<>();
    dbConnect db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminCatFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminCatFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminCatFrag newInstance(String param1, String param2) {
        AdminCatFrag fragment = new AdminCatFrag();
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
        View view = inflater.inflate(R.layout.fragment_admin_cat, container, false);
        db = new dbConnect(getActivity());

        Button btnAddCategory = view.findViewById(R.id.btnAddCategory);

        loadCategories();

        RecyclerView rvCats = view.findViewById(R.id.rvAdminCat);
        adminCategoryRvClass rv = new adminCategoryRvClass(categories, requireActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvCats.setLayoutManager(linearLayoutManager);
        rvCats.setAdapter(rv);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                Fragment alterCat = new alterCat();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, alterCat)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;

    }

    private void loadCategories() {
        categories.clear();
        Cursor cursor = db.getCategories();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dbConnect.catID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dbConnect.catName));
                category cat = new category(id, title);
                categories.add(cat);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}