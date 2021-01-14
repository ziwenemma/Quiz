package com.example.quiz;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    private NavController navController;
    private Button btn;

    public CategoryFragment() {

    }

    private GridView catView;
    private List<CategoryModel> catList= new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_category, container, false);

       catView=view.findViewById(R.id.cat_Grid);
        firestore = FirebaseFirestore.getInstance();

      CategoryAdapter adapter= new CategoryAdapter(DbQuery.g_catList);
      catView.setAdapter(adapter) ;
       return view;


    }

}

