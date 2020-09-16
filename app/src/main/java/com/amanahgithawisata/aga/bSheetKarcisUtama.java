package com.amanahgithawisata.aga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bSheetKarcisUtama extends BottomSheetDialogFragment {

    public bSheetKarcisUtama(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.entity_card_karcis_utama,container,false);

        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
