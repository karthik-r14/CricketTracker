package com.example.kr_pc.crickettracker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr_pc.crickettracker.R;

/**
 * Created by KR-PC on 13-11-2017.
 */

public class RateUsDialogFragment extends android.support.v4.app.DialogFragment {

    public static final String TAG = RateUsDialogFragment.class.getSimpleName();

    RatingBar ratingBar;
    Button submitButton;
    Button notNowButton;
    LinearLayout buttonLayout;
    TextView ratingMessage;
    View view;

    public static RateUsDialogFragment newInstance() {
        return new RateUsDialogFragment();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.rate_us, null);

        submitButton = view.findViewById(R.id.rating_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });

        notNowButton = view.findViewById(R.id.notnow_button);
        notNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNotNowButtonClick();
            }
        });

        ratingMessage = view.findViewById(R.id.rating_message);
        buttonLayout = view.findViewById(R.id.button_layout);
        ratingBar = view.findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float rating = ratingBar.getRating();
                if (rating == 0.0f) {
                    ratingMessage.setVisibility(View.VISIBLE);
                    buttonLayout.setVisibility(View.GONE);
                } else {
                    if (ratingBar.getRating() <= 3) {
                        submitButton.setText(getResources().getString(R.string.feedback));
                    } else {
                        submitButton.setText(getResources().getString(R.string.submit));
                    }
                    buttonLayout.setVisibility(View.VISIBLE);
                    ratingMessage.setVisibility(View.GONE);
                }
            }
        });

        builder.setView(view);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void onSubmitButtonClick() {
        if (submitButton.getText().equals(getResources().getString(R.string.feedback))) {
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
            dismiss();
        } else {
            Toast.makeText(getContext(), R.string.valid_ratingbar_text, Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    private void onNotNowButtonClick() {
        dismiss();
    }
}