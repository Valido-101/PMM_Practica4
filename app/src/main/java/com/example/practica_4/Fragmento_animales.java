package com.example.practica_4;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmento_animales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_animales extends Fragment implements View.OnClickListener {

    Button btn_empezar;
    Button btn_acabar;
    GridLayout grid_layout;
    TextView textView;
    HashMap<Integer, Integer> parejas;
    int[] recursos_imagen_array;
    int ultimaImagenPulsada=0;
    int contador=0;
    private SoundPool reproductorSonido;
    int mario_1up;
    int mario_firework;
    int mario_woohoo;
    int mario_coin;
    Animation rotar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Fragmento_animales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragmento_pelota.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragmento_animales newInstance() {
        Fragmento_animales fragment = new Fragmento_animales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_animales, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        btn_empezar = getView().findViewById(R.id.btn_empezar);
        btn_acabar = getView().findViewById(R.id.btn_acabar);
        grid_layout = getView().findViewById(R.id.grid_layout);
        textView = getView().findViewById(R.id.txtview);

        inicializarSoundPool();

        btn_empezar.setOnClickListener(this::onClick);
        btn_acabar.setOnClickListener(this::onClick);

        rotar = AnimationUtils.loadAnimation(getContext(), R.anim.rotar);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btn_empezar.getId())
        {
            empezarPartida();
            asignarAnimales();
        }
        else
        {
            if(v.getId()==btn_acabar.getId())
            {
                acabarPartida();
            }
            else
                {
                    comprobarImagen(v.getId());
                }
        }
    }

    public void empezarPartida()
    {
        btn_acabar.setEnabled(true);
        btn_empezar.setEnabled(false);
    }

    public void acabarPartida()
    {
        ImageView img;

        btn_acabar.setEnabled(false);
        btn_empezar.setEnabled(true);
        parejas = null;

        for(int x=0;x<recursos_imagen_array.length;x++)
        {
            int id = getResources().getIdentifier("imagen"+(x+1),"id", getContext().getPackageName());
            img = getView().findViewById(id);
            img.setImageBitmap(null);
            img.setBackgroundColor(Color.LTGRAY);
        }
        contador=0;
        textView.setText("Total de aciertos: "+contador);
    }

    public void asignarAnimales()
    {
        ImageView img_view;

        ArrayList<Integer> recursos_imagen = new ArrayList<Integer>();

        recursos_imagen.add(R.drawable.ciervo);
        recursos_imagen.add(R.drawable.ciervo);
        recursos_imagen.add(R.drawable.conejo);
        recursos_imagen.add(R.drawable.conejo);
        recursos_imagen.add(R.drawable.erizo);
        recursos_imagen.add(R.drawable.erizo);
        recursos_imagen.add(R.drawable.oso);
        recursos_imagen.add(R.drawable.oso);
        recursos_imagen.add(R.drawable.pajaro);
        recursos_imagen.add(R.drawable.pajaro);
        recursos_imagen.add(R.drawable.zorro);
        recursos_imagen.add(R.drawable.zorro);

        Collections.shuffle(recursos_imagen);

        recursos_imagen_array = new int[12];

        for(int x=0;x<recursos_imagen.size();x++)
        {
            recursos_imagen_array[x]=(int)recursos_imagen.get(x);
        }

        //Variables de control
        int ciervo = 0;
        int conejo = 0;
        int erizo = 0;
        int oso = 0;
        int pajaro = 0;
        int zorro = 0;

        parejas = new HashMap<Integer, Integer>();

        //Para setear las imágenes del grid:
        //usamos el getIdentifier en un bucle para ir obteniendo cada uno de los ImageView del grid
        //getView().findViewById(getResources().getIdentifier(imagen+(indice+1),))

        for(int x=0;x<recursos_imagen_array.length;x++)
        {
            int id = getResources().getIdentifier("imagen"+(x+1),"id", getContext().getPackageName());

            img_view = getView().findViewById(id);

            img_view.setClickable(true);

            img_view.setOnClickListener(this::onClick);

            //Comprobamos si la posición actual del array contiene el id de una imagen concreta
            switch(recursos_imagen_array[x])
            {
                case R.drawable.ciervo:
                        if(ciervo==0)
                        {
                            ciervo=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),ciervo);
                            parejas.put(ciervo,img_view.getId());
                        }
                    break;

                case R.drawable.conejo:
                        if(conejo==0)
                        {
                            conejo=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),conejo);
                            parejas.put(conejo,img_view.getId());
                        }
                    break;

                case R.drawable.erizo:
                        if(erizo==0)
                        {
                            erizo=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),erizo);
                            parejas.put(erizo,img_view.getId());
                        }
                    break;

                case R.drawable.oso:
                        if(oso==0)
                        {
                            oso=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),oso);
                            parejas.put(oso,img_view.getId());
                        }
                    break;

                case R.drawable.pajaro:
                        if(pajaro==0)
                        {
                            pajaro=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),pajaro);
                            parejas.put(pajaro,img_view.getId());
                        }
                    break;

                case R.drawable.zorro:
                        if(zorro==0)
                        {
                            zorro=img_view.getId();
                        }
                        else
                        {
                            parejas.put(img_view.getId(),zorro);
                            parejas.put(zorro,img_view.getId());
                        }
                    break;
            }

            img_view.setImageResource(recursos_imagen_array[x]);
        }
    }

    public void comprobarImagen(int identificador)
    {
        ImageView img = getView().findViewById(identificador);

        if (ultimaImagenPulsada==0)
        {
            ultimaImagenPulsada = identificador;
            //Reproducir sonido correspondiente aquí
            reproductorSonido.play(mario_coin,1,1,1,0,1);
            //Cambiar el color de fondo
            img.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            if(ultimaImagenPulsada==parejas.get(identificador))
            {
                //Reproducimos el sonido correspondiente
                reproductorSonido.play(mario_woohoo,1,1,1,0,1);
                //Color de fondo en verde
                img.setBackgroundColor(Color.GREEN);
                //Inhablitamos el listener de los ImageView (setClickable(false))
                img.setClickable(false);
                img.startAnimation(rotar);
                img = getView().findViewById(ultimaImagenPulsada);
                img.setBackgroundColor(Color.GREEN);
                img.setClickable(false);
                img.startAnimation(rotar);
                //Sumar un punto al contador
                contador++;
                textView.setText("Total de aciertos: "+contador);
                ultimaImagenPulsada=0;

            }
            else
            {
                //Cambiar background de la anterior y reproducir sonido
                img=getView().findViewById(ultimaImagenPulsada);
                img.setBackgroundColor(Color.LTGRAY);
                reproductorSonido.play(mario_firework,1,1,1,0,1);
                ultimaImagenPulsada=0;
            }
        }
    }

    public void inicializarSoundPool()
    {
        //Comprobación de versión e inicialización
        AudioAttributes audioAttributes = new AudioAttributes .Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        reproductorSonido = new SoundPool.Builder()
                .setMaxStreams(3) // número máximo de streams
                .setAudioAttributes(audioAttributes) // atributos de audio previamente definidos
                .build();
        //Cargar los archivos de audio
        mario_1up = reproductorSonido.load(getContext(), R.raw.mario_bros_1_up, 1);
        mario_firework = reproductorSonido.load(getContext(),R.raw.mario_bros_firework,1);
        mario_woohoo = reproductorSonido.load(getContext(),R.raw.mario_bros_woo_hoo,1);
        mario_coin = reproductorSonido.load(getContext(),R.raw.mario_coin,1);
    }
}