package com.easycore.nomadesk.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.easycore.nomadesk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class CheckOutDialog extends DialogFragment {

    private String title;
    private String burnedTime;

    @BindView(R.id.venue_txv_name)
    protected TextView txvTitle;
    @BindView(R.id.txv_price)
    protected TextView txvPrice;
    @BindView(R.id.btn_send)
    protected Button btnSend;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_check_out, null);
        ButterKnife.bind(this, v);

        txvTitle.setText(title);
        txvPrice.setText(String.format("%s / 0.25 £", burnedTime));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Rating has been send. Thank you.", Toast.LENGTH_LONG).show();
                dismiss();
                getActivity().finish();
            }
        });

        builder.setView(v);
        return builder.create();
    }


    public void setBurnedTime(String burnedTime) {
        this.burnedTime = burnedTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
