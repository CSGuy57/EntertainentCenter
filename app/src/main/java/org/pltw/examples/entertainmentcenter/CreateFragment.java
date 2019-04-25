package org.pltw.examples.entertainmentcenter;


import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Spinner spType;
    private Button btAddItem;
    private EditText etTitle;
    private EditText etDescription;
    private RatingBar rbRating;
    private String createType;

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        createType = getArguments().getString(MainActivity.CREATE_TYPE);

        btAddItem = view.findViewById(R.id.bt_add_item);
        spType = view.findViewById(R.id.sp_type);
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        rbRating = view.findViewById(R.id.rb_rating);

        if(createType.equals(MovieFragment.class.getSimpleName())) {
            spType.setSelection(0);
        } else if (createType.equals(VideoGameFragment.class.getSimpleName())) {
            spType.setSelection(1);
        } else if (createType.equals(BoardGameFragment.class.getSimpleName())) {
            spType.setSelection(2);
        }

        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();
            }
        });

        return view;
    }

    private void createItem(){
        String selectedType = spType.getSelectedItem().toString();

        Resources res = getResources();
        String[] types = res.getStringArray(R.array.entertainment_items);

        if(selectedType.equals(types[0])) {
            createMovie();
        } else if (selectedType.equals(types[1])) {
            createVideoGame();
        } else if (selectedType.equals(types[2])){
            createBoardGame();
        }
    }

    private void createMovie() {
        Movie movie = new Movie();
        populateEntertainmentFields(movie);

        final ProgressDialog pDialog = showSavingDialog();

        // save object asynchronously
        Backendless.Persistence.save(movie, new AsyncCallback<Movie>() {
            @Override
            public void handleResponse(Movie response) {
                Toast.makeText(getActivity(), "Item Saved", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new MovieFragment()).commit();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                pDialog.dismiss();
            }
        });
    }

    private void createVideoGame() {
        VideoGame videoGame = new VideoGame();
        populateEntertainmentFields(videoGame);

        final ProgressDialog pDialog = showSavingDialog();

        // save object asynchronously
        Backendless.Persistence.save(videoGame, new AsyncCallback<VideoGame>() {
            @Override
            public void handleResponse(VideoGame response) {
                Toast.makeText(getActivity(), "Item Saved", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new VideoGameFragment()).commit();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                pDialog.dismiss();
            }
        });
    }

    private void createBoardGame() {
        BoardGame boardGame = new BoardGame();
        populateEntertainmentFields(boardGame);

        final ProgressDialog pDialog = showSavingDialog();

        // save object asynchronously
        Backendless.Persistence.save(boardGame, new AsyncCallback<BoardGame>() {
            @Override
            public void handleResponse(BoardGame response) {
                Toast.makeText(getActivity(), "Item Saved", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
                getFragmentManager().beginTransaction()
                        .remove(CreateFragment.this).commit();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                pDialog.dismiss();
            }
        });
    }

    private void populateEntertainmentFields(Entertainment ent) {
        ent.setTitle(etTitle.getText().toString());
        ent.setDescription(etDescription.getText().toString());
        ent.setRating(rbRating.getRating());
    }

    private ProgressDialog showSavingDialog() {
        return ProgressDialog.show(getActivity(),
                getString(R.string.progress_title),
                getString(R.string.saving),
                true);
    }
}
