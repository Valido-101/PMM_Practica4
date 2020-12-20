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
    public static Fragmento_Video newInstance() {
        Fragmento_Video fragment = new Fragmento_Video();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento__video, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final VideoView videoView = getView().findViewById (R.id.videoView2);

        Uri video = Uri.parse("android.resource://com.example.practica_4/" + R.raw.video_willyrex);
        videoView.setVideoURI(video);

        MediaController mediaController = new MediaController (getContext());
        mediaController.setAnchorView (videoView);
        videoView.setMediaController (mediaController);
        videoView.start ();
    }
}