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

public class InsuranceDialog extends AppCompatDialogFragment {



    private EditText insurance_input_dialog;

    private InsuranceDialogListener listener;
    private EditText insuranceEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_insurance, null);

        builder.setView(view)
                .setTitle("Set Insurance")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //on ok
                        insurance_input_dialog = (EditText) view.findViewById(R.id.insurance_input_dialog);
                        String insurance = insurance_input_dialog.getText().toString();
                        listener.applyInsurance(insurance);
                        listener.getInsurance(insurance);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        insuranceEditText = view.findViewById(R.id.insurance_input_dialog);


            return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (InsuranceDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must add InsuranceDialogListener");

        }

    }

    public interface InsuranceDialogListener{
        void applyInsurance(String insurance);
        void getInsurance(String newInsurance);
    }

}
