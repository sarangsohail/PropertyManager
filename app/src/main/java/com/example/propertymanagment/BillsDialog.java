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

public class BillsDialog extends AppCompatDialogFragment {

    private TextView billDisplay;
    private Button billDialogBtn;
    private EditText bills_input_dialog;

    private TextView billsEditText;

    private  billDialogListener listener;
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_bills, null);

        builder.setView(view)
                .setTitle("Set your bills")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bills_input_dialog = (EditText) view.findViewById(R.id.bills_input_dialog);
                        String getBill = bills_input_dialog.getText().toString();
                        listener.setBill(getBill);
                        listener.getBill(getBill);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        billsEditText = (TextView) view.findViewById(R.id.billsTitleTextViewFinance2);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (billDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must add billdialoglistener");
        }


    }

    public interface billDialogListener{
        void setBill(String bills);
        void getBill(String newBill);
    }
}
