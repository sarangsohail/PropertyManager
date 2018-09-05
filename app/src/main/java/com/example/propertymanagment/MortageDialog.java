package com.example.propertymanagment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MortageDialog extends AppCompatDialogFragment {

    private EditText mortgage;
    private Button mortgageBtn;
    private EditText mortgage_dialog;
    private MortageDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view  = inflater.inflate(R.layout.dialog_mortage, null, false);

        builder.setView(view)
                .setTitle("Set Mortgage")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mortgage = (EditText) view.findViewById(R.id.mortage_input_dialog);
                        String mortage = mortgage.getText().toString();
                        listener.applyMortgage(mortage);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        mortgage_dialog = (EditText) view.findViewById(R.id.mortage_input_dialog);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MortageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must add MortageDialogListener");
        }
    }

    public interface MortageDialogListener{
        void applyMortgage(String mortage);
    }
}
