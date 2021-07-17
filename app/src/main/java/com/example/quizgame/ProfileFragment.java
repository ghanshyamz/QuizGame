package com.example.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizgame.databinding.FragmentProfileBinding;
import com.example.quizgame.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentProfileBinding binding;
    FirebaseFirestore database;
    User user;
    FirebaseAuth auth ;



    @NotNull
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getContext())
                .setTitle("Exit")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        auth.signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        String name, email;



        FirebaseFirestore database = FirebaseFirestore.getInstance();


        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                user = documentSnapshot.toObject(User.class);
                binding.emailBoxProfile.setText(String.valueOf(user.getEmail()));
                binding.nameBoxProfile.setText(String.valueOf(user.getName()));
                //binding.currentCoins.setText(user.getCoins() + "");
            }
        });



        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });

        return binding.getRoot();
    }



}