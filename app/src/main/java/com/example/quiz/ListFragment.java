package com.example.quiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;


public class ListFragment extends Fragment implements com.example.quiz.QuizListAdapter.OnQuizListItemClicked {

    private NavController navController;

    private RecyclerView listView;
    private com.example.quiz.QuizListViewModel quizListViewModel;

    private com.example.quiz.QuizListAdapter adapter;
    private ProgressBar listProgress;

    private Animation fadeInAnim;
    private Animation fadeOutAnim;

    public ListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        listView = view.findViewById(R.id.list_view);
        adapter = new com.example.quiz.QuizListAdapter(this);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        fadeInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        quizListViewModel = new ViewModelProvider(getActivity()).get(com.example.quiz.QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(), new Observer<List<com.example.quiz.QuizListModel>>() {
            @Override
            public void onChanged(List<com.example.quiz.QuizListModel> quizListModels) {
                listView.startAnimation(fadeInAnim);
                adapter.setQuizListModels(quizListModels);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action = ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}
