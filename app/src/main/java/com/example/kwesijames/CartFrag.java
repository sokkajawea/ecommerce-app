package com.example.kwesijames;

import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFrag extends Fragment {
    public ArrayList<cartItem> cartItems  = new ArrayList<>();
    dbConnect db;

    private String user;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFrag newInstance(String param1, String param2) {
        CartFrag fragment = new CartFrag();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        db = new dbConnect(getActivity());

        TextView username=getActivity().findViewById(R.id.txvLoginName);
        user = username.getText().toString();

        loadCart();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("cartItems", new HashSet<>());
        editor.apply();


        Log.d("array", cartItems.toString());


        RecyclerView rvCart = view.findViewById(R.id.rvCart);
        cartRecyclerViewClass rv = new cartRecyclerViewClass(cartItems, requireActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(rv);

        return view;
    }

    private void loadCart() {
        Set<String> cartItemsSet = getCartItemsFromSharedPreferences();
        cartItems.clear();

        for (String item : cartItemsSet) {
            String[] itemDetails = item.split(" - ");
            String productName = itemDetails[0];
            String price = itemDetails[1];
            cartItems.add(new cartItem(productName, price));
        }

        saveCartItemsToSharedPreferences(cartItemsSet);
    }

    private void saveCartItemsToSharedPreferences(Set<String> cartItemsSet) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("cartItems", cartItemsSet);
        editor.apply();
    }


    private Set<String> getCartItemsFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
        return sharedPreferences.getStringSet("cartItems", new HashSet<>());
    }




}