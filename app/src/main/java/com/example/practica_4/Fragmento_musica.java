package com.example.practica_4;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmento_musica#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_musica extends Fragment {

    //Vistas necesarias
    private Button b1,b2,b3,b4;
    private ImageView iv;

    //Objeto necesario para reproducir archivos de audio grandes
    private MediaPlayer mediaPlayer;

    //Variables que llevan el control del tiempo de inicio y el tiempo final
    private double startTime = 0;
    private double finalTime = 0;

    //Variable que nos ayudará a que la canción se reproduzca en el punto en el que pongamos el thumb del seekbar
    private int posicion;

    //Handler (hilo secundario)
    private Handler myHandler = new Handler();;
    //Tiempos de avance y de retroceso (5 segundos)
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    //Seekbar para controlar el punto de reproducción
    private SeekBar seekbar;

    //TextViews que muestran tiempo de reproducción actual y total de reproducción
    private TextView tx1,tx2;
    public static int oneTimeOnly = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Fragmento_musica() {
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
    public static Fragmento_musica newInstance() {
        Fragmento_musica fragment = new Fragmento_musica();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Método que se ejecuta al crear una vista. La inflamos con el inflater
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_musica, container, false);
        return view;
    }

    //Método que se ejecuta cuando se ha creado una vista (se asegura de que está creada). Comportamiento de los botones
    //y todo lo necesario para reproducir la música
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Obtenemos todos los botones y los textviews
        b1 = (Button) getView().findViewById(R.id.btn_play);
        b2 = (Button) getView().findViewById(R.id.btn_pause);
        b3 = (Button) getView().findViewById(R.id.btn_forward_5secs);
        b4 = (Button) getView().findViewById(R.id.btn_backward_5secs);
        tx1 = (TextView) getView().findViewById(R.id.txt1);
        tx2 = (TextView) getView().findViewById(R.id.txt2);

        //Inicializamos el mediaplayer con la canción deseada
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.pokemon_gym);
        //Obtenemos el seekbar
        seekbar = (SeekBar)getView().findViewById(R.id.seekbar);
        seekbar.setClickable(false);
        //Como la canción comienza en estado de pausa, inhabilitamos el botón de pausa
        b2.setEnabled(false);

        //Establecemos un OnSeekBarChangeListener al seekbar para poder reproducir la canción donde soltemos el thumb
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //Cada vez que se actualice la posición del seekbar, guardaremos esa posición, ya sea automáticamente
            //o porque el usuario lo mueva
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                posicion=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //Cuando coge el thumb, lo arrastra y lo suelta, la canción se reproduce en ese punto
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(posicion);
            }
        });

        //Botón de reproducir, asignamos el listener y su comportamiento
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostramos el toast diciendo que se ha empezado a reproducir la canción
                Toast.makeText(getContext(), "Playing sound",Toast.LENGTH_SHORT).show();

                //Empezamos a reproducirla
                mediaPlayer.start();

                //Establecemos la duración total e inicial
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                //Actualizamos el textview con los datos de tiempo total de reproducción
                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                //Actualizamos el textview con los datos de tiempo actual de reproducción
                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                //Actualizamos el progreso del seekbar con el momento actual de reproducción
                seekbar.setProgress((int)startTime);
                //LLamamos al método de actualizar tiempo de canción una vez cada segundo en un hilo secundario
                myHandler.postDelayed(UpdateSongTime,100);
                //Habilitamos el botón de pausa
                b2.setEnabled(true);
                //Inhabilitamos el de reproducir
                b1.setEnabled(false);
            }
        });

        //OnClickListener y comportamiento del botón de pausa
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Informamos de que se ha pausado
                Toast.makeText(getContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
                //Pausamos canción
                mediaPlayer.pause();
                //Inhabilitamos el botón de pausa
                b2.setEnabled(false);
                //Habilitamos el de reproducir
                b1.setEnabled(true);
            }
        });

        //OnClickListener y comportamiento del botón de adelantar 5 segundos
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos una variable con el punto actual de reproducción
                int temp = (int)startTime;

                //Si esa variable más los 5 segundos son inferiores al tiempo total de reproducción
                if((temp+forwardTime)<=finalTime){
                    //Se suma al tiempo actual los 5 segundos
                    startTime = startTime + forwardTime;
                    //Reproducimos la canción en el punto que está 5 segundos después
                    mediaPlayer.seekTo((int) startTime);
                    //Informamos
                    Toast.makeText(getContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    //Si no se puede se informa también
                    Toast.makeText(getContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //OnClickListener y comportamiento del botón de retrasar 5 segundos
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos una variable con el punto actual de reproducción
                int temp = (int)startTime;

                //Si esa variable menos los 5 segundos son superiores a 0
                if((temp-backwardTime)>0){
                    //Se resta al tiempo actual los 5 segundos
                    startTime = startTime - backwardTime;
                    //Reproducimos la canción en el punto que está 5 segundos antes
                    mediaPlayer.seekTo((int) startTime);
                    //Informamos
                    Toast.makeText(getContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    //Si no se puede se informa también
                    Toast.makeText(getContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método que sirve para actualizar el seekbar y el tiempo de reproducción actual
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            //Igualamos el tiempo de inicio a la posición actual
            startTime = mediaPlayer.getCurrentPosition();
            //Actualizamos el valor del textview con el tiempo de reproducción actual
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            //Actualizamos el progreso del seekbar
            seekbar.setProgress((int)startTime);
            //Ejecutamos el método una vez cada segundo
            myHandler.postDelayed(this, 100);
        }
    };
}