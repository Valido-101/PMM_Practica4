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

    //Elementos necesarios
    Button btn_empezar;
    Button btn_acabar;
    GridLayout grid_layout;
    TextView textView;
    //HashMap
    HashMap<Integer, Integer> parejas;
    int[] recursos_imagen_array;
    int ultimaImagenPulsada=0;
    int contador=0;
    //SoundPool para reproducir los sonidos
    private SoundPool reproductorSonido;
    //Sonidos
    int mario_1up;
    int mario_firework;
    int mario_woohoo;
    int mario_coin;
    //Animación
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
    //Método para instanciar el Fragmento
    public static Fragmento_animales newInstance() {
        Fragmento_animales fragment = new Fragmento_animales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Al crear la vista la inflamos con el inflater
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_animales, container, false);


        return view;
    }

    //Una vez que se ha creado la vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //Obtenemos todas las vistas
        btn_empezar = getView().findViewById(R.id.btn_empezar);
        btn_acabar = getView().findViewById(R.id.btn_acabar);
        grid_layout = getView().findViewById(R.id.grid_layout);
        textView = getView().findViewById(R.id.txtview);

        //Inicializamos el soundPool
        inicializarSoundPool();

        //Establecemos los listeners
        btn_empezar.setOnClickListener(this::onClick);
        btn_acabar.setOnClickListener(this::onClick);

        //Seteamos la animación
        rotar = AnimationUtils.loadAnimation(getContext(), R.anim.rotar);
    }

    //Método onClick
    @Override
    public void onClick(View v) {
        //Si el botón pulsado es empezar
        if(v.getId()==btn_empezar.getId())
        {
            //Se llama al método empezarPartida y asignarAnimales
            empezarPartida();
            asignarAnimales();
        }
        else
        {
            //Si coincide con el botón acabar
            if(v.getId()==btn_acabar.getId())
            {
                //Se llama al método acabarPartida
                acabarPartida();
            }
            //Si no coincide con ninguno de los botones es porque es un imageview y, por
            // tanto, se llama al método comprobarImagen
            else
                {
                    comprobarImagen(v.getId());
                }
        }
    }

    public void empezarPartida()
    {
        //Habilitamos el botón de acabar
        btn_acabar.setEnabled(true);
        //Inhabilitamos el botón de empezar
        btn_empezar.setEnabled(false);
    }

    //Método que se ejecutará al pulsar el botón de acabar
    public void acabarPartida()
    {
        ImageView img;

        //Inhabilitamos el botón de acabar
        btn_acabar.setEnabled(false);
        //Habilitamos el botón de empezar
        btn_empezar.setEnabled(true);
        //Igualamos el HashMap a null para que se borren los registros anteriores
        parejas = null;

        //Recorremos las imágenes del grid y borramos el valor de las imageview y cambiamos el color de fondo
        for(int x=0;x<recursos_imagen_array.length;x++)
        {
            int id = getResources().getIdentifier("imagen"+(x+1),"id", getContext().getPackageName());
            img = getView().findViewById(id);
            img.setImageBitmap(null);
            img.setBackgroundColor(Color.LTGRAY);
        }
        //Seteamos el valor del contador a 0
        contador=0;
        //Actualizamos el textview
        textView.setText("Total de aciertos: "+contador);
    }

    //Método que se encarga de asignar las imágenes de los animales a los ImageViews y de relacionar las parejas
    public void asignarAnimales()
    {
        ImageView img_view;

        //ArrayList que usaremos para guardar el id de las imágenes
        ArrayList<Integer> recursos_imagen = new ArrayList<Integer>();

        //Añadimos las imágenes por duplicado
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

        //Desordenamos aleatoriamente
        Collections.shuffle(recursos_imagen);

        //Creamos un array de doce posiciones donde guardaremos los id de las imagenes
        recursos_imagen_array = new int[12];

        //Recorremos el arraylist y guardamos los id
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

        //HashMap para guardar los id de las parejas
        parejas = new HashMap<Integer, Integer>();

        //Para setear las imágenes del grid:

        for(int x=0;x<recursos_imagen_array.length;x++)
        {
            //usamos el getIdentifier en un bucle para ir obteniendo cada uno de los ImageView del grid
            int id = getResources().getIdentifier("imagen"+(x+1),"id", getContext().getPackageName());

            img_view = getView().findViewById(id);

            //Hacemos que sean clicables
            img_view.setClickable(true);

            //Les asignamos el OnClickListener
            img_view.setOnClickListener(this::onClick);

            //Comprobamos si la posición actual del array contiene el id de una imagen concreta
            switch(recursos_imagen_array[x])
            {
                //Si coincide con la del ciervo
                case R.drawable.ciervo:
                    //Si es la primera vez que aparece esta imagen
                        if(ciervo==0)
                        {
                            //Seteamos el valor de la variable de control ciervo
                            ciervo=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),ciervo);
                            parejas.put(ciervo,img_view.getId());
                        }
                    break;

                case R.drawable.conejo:
                    //Si es la primera vez que aparece esta imagen
                        if(conejo==0)
                        {
                            //Seteamos el valor de la variable de control conejo
                            conejo=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),conejo);
                            parejas.put(conejo,img_view.getId());
                        }
                    break;

                case R.drawable.erizo:
                    //Si es la primera vez que aparece esta imagen
                        if(erizo==0)
                        {
                            //Seteamos el valor de la variable de control erizo
                            erizo=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),erizo);
                            parejas.put(erizo,img_view.getId());
                        }
                    break;

                case R.drawable.oso:
                    //Si es la primera vez que aparece esta imagen
                        if(oso==0)
                        {
                            //Seteamos el valor de la variable de control oso
                            oso=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),oso);
                            parejas.put(oso,img_view.getId());
                        }
                    break;

                case R.drawable.pajaro:
                    //Si es la primera vez que aparece esta imagen
                        if(pajaro==0)
                        {
                            //Seteamos el valor de la variable de control pajaro
                            pajaro=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),pajaro);
                            parejas.put(pajaro,img_view.getId());
                        }
                    break;

                case R.drawable.zorro:
                    //Si es la primera vez que aparece esta imagen
                        if(zorro==0)
                        {
                            //Seteamos el valor de la variable de control zorro
                            zorro=img_view.getId();
                        }
                        else
                        {
                            //Si no es la primera vez, almacenamos las parejas en el HashMap
                            parejas.put(img_view.getId(),zorro);
                            parejas.put(zorro,img_view.getId());
                        }
                    break;
            }

            //Seteamos el valor del image view con el valor actual del array de id de imágenes, independientemente de que
            //haya aparecido ya o no
            img_view.setImageResource(recursos_imagen_array[x]);
        }
    }

    //Método que comprueba que las dos imágenes seleccionadas son la misma
    public void comprobarImagen(int identificador)
    {
        //Obtenemos el ImageView
        ImageView img = getView().findViewById(identificador);

        //Si aún no se ha pulsado una imagen desde la última comprobación
        if (ultimaImagenPulsada==0)
        {
            //Guardamos el id del ImageView en la variable de ultimaImagenPulsada
            ultimaImagenPulsada = identificador;
            //Reproducir sonido correspondiente aquí
            reproductorSonido.play(mario_coin,1,1,1,0,1);
            //Cambiar el color de fondo
            img.setBackgroundColor(Color.YELLOW);
        }
        //Si ya se ha pulsado una antes
        else
        {
            //Si coinciden las dos imágenes (usamos el hashmap para comparar id)
            if(ultimaImagenPulsada==parejas.get(identificador))
            {
                //Reproducimos el sonido correspondiente
                reproductorSonido.play(mario_woohoo,1,1,1,0,1);
                //Color de fondo en verde
                img.setBackgroundColor(Color.GREEN);
                //Inhablitamos el listener de los ImageView (setClickable(false))
                img.setClickable(false);
                //Iniciamos la animación
                img.startAnimation(rotar);
                //Obtenemos el ImageView que le corresponde a la pareja usando el id de la ultima imagen pulsada
                img = getView().findViewById(ultimaImagenPulsada);
                //Cambiamos el fondo
                img.setBackgroundColor(Color.GREEN);
                //Inhablitamos el listener de los ImageView (setClickable(false))
                img.setClickable(false);
                //Iniciamos la animación
                img.startAnimation(rotar);
                //Sumar un punto al contador
                contador++;
                //Actualizamos el textView
                textView.setText("Total de aciertos: "+contador);
                //Igualamos el valor de ultimaImagenPulsada a 0
                ultimaImagenPulsada=0;

            }
            //Si no coincide
            else
            {
                //Cambiar background de la anterior y reproducir sonido
                img=getView().findViewById(ultimaImagenPulsada);
                img.setBackgroundColor(Color.LTGRAY);
                reproductorSonido.play(mario_firework,1,1,1,0,1);
                //Igualamos el valor de ultimaImagenPulsada a 0
                ultimaImagenPulsada=0;
            }
        }
    }

    //Método necesario para inicializar el soundpool y poder reproducir archivos de audio pequeños
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