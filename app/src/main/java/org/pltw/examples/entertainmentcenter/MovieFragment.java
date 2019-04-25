package org.pltw.examples.entertainmentcenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.rv_movie_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setSortBy("Rating DESC", "Title");

        Backendless.Data.of(Movie.class).find(queryBuilder, new AsyncCallback<List<Movie>>() {
            @Override
            public void handleResponse(List<Movie> response) {
                List<Entertainment> movies = new ArrayList<>();

                for(Movie m : response) {
                    movies.add(m);
                }

                movieAdapter = new EntertainmentAdapter(movies);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        return view;
    }
}
