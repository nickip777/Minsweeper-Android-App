package com.example.nick.minesweeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by user on 2/24/2017.
 */

public class MessageFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_layout,null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                switch(which) {
                    //finish current activity
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("All the bombs have been found!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
