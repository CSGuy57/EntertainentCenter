package org.pltw.examples.entertainmentcenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    private TextView tvWelcomeMessage;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        tvWelcomeMessage = view.findViewById(R.id.tv_welcome_message);

        BackendlessUser currentUser = Backendless.UserService.CurrentUser();

        String name = currentUser.getProperty("name").toString();

        tvWelcomeMessage.setText(name + ", your favorite entertainment is: ");

        List<Entertainment> favoriteEntertainment = new ArrayList<>();

        Backendless.Data.of(Movie.class).find(new AsyncCallback<List<Movie>>() {
            @Override
            public void handleResponse(List<Movie> response) {
                favoriteEntertainment.add(response.get(0));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        return view;
    }
}
