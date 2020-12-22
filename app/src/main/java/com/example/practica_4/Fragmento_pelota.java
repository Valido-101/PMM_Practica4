package com.example.practica_4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmento_pelota#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_pelota extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //Vista necesaria para ejecutar la animación
    ImageView imageView;

    //Objeto de tipo animation
    Animation bote;

    //static Context context;

    // TODO: Rename and change types of parameters

    public Fragmento_pelota() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragmento_pelota.
     */
    // TODO: Rename and change types and number of parameters
    //Método necesario para instanciar el fragmento
    public static Fragmento_pelota newInstance() {
        Fragmento_pelota fragment = new Fragmento_pelota();
        //context = pcontext;
        return fragment;
    }

    //Método perteneciente al ciclo de vida de un fragmento. Encontramos el ImageView, le establecemos
    // un OnClickListener y cargamos la animación usando el recurso xml de la carpeta anim
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.pelota);

        imageView.setOnClickListener(this::onClick);

        bote = AnimationUtils.loadAnimation(getContext(), R.anim.botar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Cada vez que se cree la vista, usamos el inflater
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_pelota, container, false);
        return view;
    }

    //Cada vez que hagamos click, iniciamos la animación
    @Override
    public void onClick(View v) {
        imageView.startAnimation(bote);
    }
}