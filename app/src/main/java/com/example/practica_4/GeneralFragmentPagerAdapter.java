package com.example.practica_4;

import android.content.Context;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GeneralFragmentPagerAdapter extends FragmentPagerAdapter {

    //Atributos necesarios para el Adaptador
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Animales", "Musica", "Vídeo", "Pelota" };
    private Context context;

    //Método para inicializarlo
    public GeneralFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //Este método se llama cada vez que se introduzca un elemento en el ViewPager
    @Override
    public Fragment getItem(int position) {

        //Instanciamos cada uno de los fragmentos
        Fragmento_pelota f_p = Fragmento_pelota.newInstance();
        Fragmento_animales f_a = Fragmento_animales.newInstance();
        Fragmento_musica f_m = Fragmento_musica.newInstance();
        Fragmento_Video f_v = Fragmento_Video.newInstance();

        //Dependiendo de la posición, asignamos un Fragmento u otro
        switch(position)
        {
            default:
                return null;
            case 0:
                return f_a;
            case 1:
                return f_m;
            case 2:
                return f_v;
            case 3:
                return f_p;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
