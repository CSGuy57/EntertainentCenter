package org.pltw.examples.entertainmentcenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardGameFragment extends Fragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter boardGameAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public BoardGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_game, container, false);

        recyclerView = view.findViewById(R.id.rv_board_game_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setSortBy("Rating DESC", "Title");

        Backendless.Data.of(BoardGame.class).find(queryBuilder, new AsyncCallback<List<BoardGame>>() {
            @Override
            public void handleResponse(List<BoardGame> response) {
                List<Entertainment> boardGames = new ArrayList<>();

                for(BoardGame boardGame : response) {
                    boardGames.add(boardGame);
                }

                boardGameAdapter = new EntertainmentAdapter(boardGames);
                recyclerView.setAdapter(boardGameAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        return view;
    }
}
