package com.example.tpmaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InicioFragment extends Fragment {

    View layoutRoot;
    EditText edtNombre;
    Button btnNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_inicio, container, false);

            ObtenerReferencias();

            SetListeners();
        }

        return layoutRoot;
    }

    public void ObtenerReferencias() {
        edtNombre = layoutRoot.findViewById(R.id.edtNombre);
        btnNombre = layoutRoot.findViewById(R.id.btnNombre);
    }

    public void SetListeners() {
        btnNombre.setOnClickListener(btnNombreOnClick);
    }

    View.OnClickListener btnNombreOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activityContenedora = (MainActivity) getActivity();
            if(!edtNombre.getText().toString().equals("")){
                activityContenedora.setCurrentPlayer(edtNombre.getText().toString());
                activityContenedora.IrAFragmentObjetivo();
            }
            else {
                Toast.makeText(getContext(), "Debes completar tu nombre", Toast.LENGTH_SHORT).show();
            }
        }
    };
}