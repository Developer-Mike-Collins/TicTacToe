package com.example.mike.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Mike on 2/12/2017.
 */

public class WinDialog extends DialogFragment {

    public interface WinDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    WinDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (WinDialogListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WinDialogListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int messageID = R.string.value_not_set;
        switch (winner){
            case "X":
                messageID = R.string.x_wins;
                break;
            case "O":
                messageID = R.string.o_wins;
                break;
            case "C":
                messageID = R.string.cats_game;
                break;
        }
        builder.setMessage(messageID)
                .setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(WinDialog.this);
                    }
                })
                .setNegativeButton(R.string.close_app, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(WinDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private String winner = "";
    public void setWinner(String winner){
        winner = winner;
    }
}
