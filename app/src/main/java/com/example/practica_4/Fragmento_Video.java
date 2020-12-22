package com.example.practica_4;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmento_Video#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_Video extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Fragmento_Video() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragmento_pelota.
     */
    // TODO: Rename and change types and number of parameters
    //Método para instanciar el fragmento
    public static Fragmento_Video newInstance() {
        Fragmento_Video fragment = new Fragmento_Video();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Método del ciclo de vida del fragmento que se ejecuta al crear una vista. Inflamos la vista con el inflater
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento__video, container, false);
        return view;
    }

    //Método del ciclo de vida del fragmento que se ejecuta después de una vista (se asegura de que ya ha sido creada)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Obtenemos el videoview
        final VideoView videoView = getView().findViewById (R.id.videoView);

        //Creamos la uri del vídeo tomando el recurso en la carpeta raw
        Uri video = Uri.parse("android.resource://com.example.practica_4/" + R.raw.video_willyrex);
        //Establecemos la uri del videoView con la creada anteriormente
        videoView.setVideoURI(video);

        //Creamos el media controller
        MediaController mediaController = new MediaController (getContext());
        //Le asignamos el videoView
        mediaController.setAnchorView (videoView);
        //Asignamos el mediaController al videoView
        videoView.setMediaController (mediaController);
    }
}