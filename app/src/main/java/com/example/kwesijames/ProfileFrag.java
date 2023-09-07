package com.example.kwesijames;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment {
    dbConnect db;

    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = new dbConnect(getActivity());

        TextView user=getActivity().findViewById(R.id.txvLoginName);
        username = user.getText().toString();

        Cursor cursor = db.getUserData(username);


        if (cursor.moveToFirst()) {
            id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
            username = cursor.getString(cursor.getColumnIndex("Username"));
            firstname = cursor.getString(cursor.getColumnIndex("Firstname"));
            lastname = cursor.getString(cursor.getColumnIndex("Lastname"));
            password = cursor.getString(cursor.getColumnIndex("Password"));
        }

        EditText edtProfUsername, edtProfFirstname, edtProfLastname, edtProfPassword;

        edtProfUsername = view.findViewById(R.id.edtProfUsername);
        edtProfFirstname = view.findViewById(R.id.edtProfFirstname);
        edtProfLastname = view.findViewById(R.id.edtProfLastname);
        edtProfPassword = view.findViewById(R.id.edtProfPassword);

        edtProfUsername.setText(username);
        edtProfFirstname.setText(firstname);
        edtProfLastname.setText(lastname);
        edtProfPassword.setText(password);

        cursor.close();


        Button btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = Integer.parseInt(id);
                String username = edtProfUsername.getText().toString();
                String firstname = edtProfFirstname.getText().toString();
                String lastname = edtProfLastname.getText().toString();
                String password = edtProfPassword.getText().toString();

                users u1 = new users(Id, username, firstname, lastname, password);
                boolean result = db.updateUser(u1);
                TextView txvLogin = getActivity().findViewById(R.id.txvLoginName);
                txvLogin.setText(username);
                if (result) {
                    Toast.makeText(getContext(), "Successfully updated data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to update data", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return  view;
    }
}