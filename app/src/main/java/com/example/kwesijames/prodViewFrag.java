package com.example.kwesijames;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link prodViewFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class prodViewFrag extends Fragment {

    public ArrayList<cartItem> cartItems  = new ArrayList<>();

    TextView txvProdViewName, txvProdViewPrice, txvProdViewDesc;
    Button btnAddtoCart;

    dbConnect db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public prodViewFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment prodViewFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static prodViewFrag newInstance(String param1, String param2) {
        prodViewFrag fragment = new prodViewFrag();
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
        View view = inflater.inflate(R.layout.fragment_prod_view, container, false);

        txvProdViewDesc=view.findViewById(R.id.txvProdViewDesc);
        txvProdViewName=view.findViewById(R.id.txvProdViewName);
        txvProdViewPrice=view.findViewById(R.id.txvProdViewPrice);

        btnAddtoCart=view.findViewById(R.id.btnAddToCart);

        db=new dbConnect(getContext());

        Bundle bundle = getArguments();

        String user = getActivity().getIntent().getStringExtra("username");

        String prodName = bundle.getString("prodName");
        double prodPrice = bundle.getDouble("prodPrice");
        String prodDescription = bundle.getString("prodDescription");
        int productId = bundle.getInt("prodId");

        txvProdViewName.setText(prodName);
        txvProdViewPrice.setText("£" + prodPrice);
        txvProdViewDesc.setText(prodDescription);



        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ArrayList<product> productIds = new ArrayList<>();
                    product productId1 = new product(prodName, prodPrice);
                    productIds.add(productId1);

                    addToCart(productIds);

                    Toast.makeText(view.getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                }

        });






        return view;
    }

    public void addToCart(ArrayList<product> products) {
        Set<String> cartItemsSet = getCartItemsFromSharedPreferences();

        for (product product : products) {
            String item = product.getProdName() + " - " + product.getPrice();
            cartItemsSet.add(item);
        }

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("cartItems", cartItemsSet);
        editor.apply();

        loadCart();
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