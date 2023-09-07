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
 * Use the {@link alterProd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alterProd extends Fragment {

    private EditText edtAdminProdName, edtAdminProdPrice, edtAdminProdDesc, edtAdminProdListPrice, edtAdminProdRetailPrice, edtAdminProdCategory;
    private Button btnSaveProd;

    dbConnect db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public alterProd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment alterProd.
     */
    // TODO: Rename and change types and number of parameters
    public static alterProd newInstance(int id, String prodName, double prodPrice, String prodDescription, double prodListPrice, double prodRetailPrice, String prodCategory) {
        alterProd fragment = new alterProd();
        Bundle args = new Bundle();
        args.putInt("prodId", id);
        args.putString("prodName", prodName);
        args.putDouble("prodPrice", prodPrice);
        args.putString("prodDescription", prodDescription);
        args.putDouble("prodListPrice", prodListPrice);
        args.putDouble("prodRetailPrice", prodRetailPrice);
        args.putString("prodCategory", prodCategory);
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
        View view = inflater.inflate(R.layout.fragment_alter_prod, container, false);

        edtAdminProdName = view.findViewById(R.id.edtAdminProdName);
        edtAdminProdPrice = view.findViewById(R.id.edtAdminProdPrice);
        edtAdminProdDesc = view.findViewById(R.id.edtAdminProdDesc);
        edtAdminProdListPrice = view.findViewById(R.id.edtAdminProdListPrice);
        edtAdminProdRetailPrice = view.findViewById(R.id.edtAdminProdRetailPrice);
        edtAdminProdCategory = view.findViewById(R.id.edtAdminProdCategory);

        btnSaveProd = view.findViewById(R.id.btnSaveProd);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int prodId = bundle.getInt("prodId");
            String prodName = bundle.getString("prodName");
            double prodPrice = bundle.getDouble("prodPrice");
            String prodDescription = bundle.getString("prodDescription");
            double prodListPrice = bundle.getDouble("prodListPrice");
            double prodRetailPrice = bundle.getDouble("prodRetailPrice");
            String prodCategory = bundle.getString("prodCategory");

            edtAdminProdName.setText(prodName);
            edtAdminProdPrice.setText(String.valueOf(prodPrice));
            edtAdminProdDesc.setText(prodDescription);
            edtAdminProdListPrice.setText(String.valueOf(prodListPrice));
            edtAdminProdRetailPrice.setText(String.valueOf(prodRetailPrice));
            edtAdminProdCategory.setText(prodCategory);

            String strAdminProdName = edtAdminProdName.getText().toString();
            double strAdminProdPrice = Double.parseDouble(edtAdminProdPrice.getText().toString());
            String strAdminProdDesc = edtAdminProdDesc.getText().toString();
            double strAdminProdListPrice = Double.parseDouble(edtAdminProdListPrice.getText().toString());
            double strAdminProdRetailPrice = Double.parseDouble(edtAdminProdRetailPrice.getText().toString());
            String strAdminProdCategory = edtAdminProdCategory.getText().toString();


            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db = new dbConnect(getContext());
                    String strAdminProdName = edtAdminProdName.getText().toString();
                    double strAdminProdPrice = Double.parseDouble(edtAdminProdPrice.getText().toString());
                    String strAdminProdDesc = edtAdminProdDesc.getText().toString();
                    double strAdminProdListPrice = Double.parseDouble(edtAdminProdListPrice.getText().toString());
                    double strAdminProdRetailPrice = Double.parseDouble(edtAdminProdRetailPrice.getText().toString());
                    String strAdminProdCategory = edtAdminProdCategory.getText().toString();

                    boolean result = db.updateProd(prodId, strAdminProdName, strAdminProdPrice, strAdminProdDesc, strAdminProdCategory, strAdminProdListPrice, strAdminProdRetailPrice);
                    if(result){
                        Toast.makeText(getContext(), "Product Updated!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Update Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db = new dbConnect(getContext());
                    String strAdminProdName = edtAdminProdName.getText().toString();
                    double strAdminProdPrice = Double.parseDouble(edtAdminProdPrice.getText().toString());
                    String strAdminProdDesc = edtAdminProdDesc.getText().toString();
                    double strAdminProdListPrice = Double.parseDouble(edtAdminProdListPrice.getText().toString());
                    double strAdminProdRetailPrice = Double.parseDouble(edtAdminProdRetailPrice.getText().toString());
                    String strAdminProdCategory = edtAdminProdCategory.getText().toString();

                    db.addProd(strAdminProdName, strAdminProdPrice, strAdminProdDesc, strAdminProdCategory, strAdminProdListPrice, strAdminProdRetailPrice);
                }
            });
        }

        return view;
    }
}