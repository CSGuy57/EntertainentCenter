package org.pltw.examples.entertainmentcenter;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoGameFragment extends Fragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter movieAdapter;


    public VideoGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.rv_movie_list);
        recyclerView.setHasFixedSize(true);

        Backendless.Data.of(VideoGame.class).find(new AsyncCallback<List<VideoGame>>() {
            @Override
            public void handleResponse(List<VideoGame> response) {
                List<Entertainment> videoGames = new ArrayList<>();

                for(VideoGame vm : response) {
                    videoGames.add(vm);
                }

                movieAdapter = new EntertainmentAdapter(videoGames);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        return view;
    }

}
