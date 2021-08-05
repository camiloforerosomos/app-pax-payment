package com.example.paxpayment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {
    private static final String TAG = "Tab1Frament";

    private Button btnPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = super.onCreateView(inflater, container, savedInstanceState);

       btnPayment = view.findViewById(R.id.btnPayment);

       btnPayment.setOnClickListener(v -> {
           Toast.makeText(getActivity(), "Testeando Payment1", Toast.LENGTH_SHORT).show();
       });

       return view;
    }
}
