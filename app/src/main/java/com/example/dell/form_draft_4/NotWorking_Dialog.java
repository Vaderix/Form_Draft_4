package com.example.dell.form_draft_4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NotWorking_Dialog extends DialogFragment {

    TextView nw_deviceIDText, nw_deviceTypeText, nw_simNumText;
    TextView nw_deviceIDValue, nw_deviceTypeValue, nw_simNumValue;
    Button closeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_not_working, container, false);
        nw_deviceIDText = view.findViewById(R.id.nw_deviceIDText);
        nw_deviceTypeText = view.findViewById(R.id.nw_deviceTypeText);
        nw_simNumText = view.findViewById(R.id.nw_simNumText);
        nw_deviceIDValue = view.findViewById(R.id.nw_deviceIDValue);
        nw_deviceTypeValue = view.findViewById(R.id.nw_deviceTypeValue);
        nw_simNumValue = view.findViewById(R.id.nw_simNumValue);
        closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }


}
