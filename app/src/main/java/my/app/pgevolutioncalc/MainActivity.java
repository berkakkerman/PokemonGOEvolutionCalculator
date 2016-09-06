package my.app.pgevolutioncalc;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity {
    private InterstitialAd gecisReklam;
    private AdView adView;
    Spinner pokemonSpinner;
    EditText currentCP;
    Button evolveButton;
    ImageView evolvedImage;
    TextView cpView, nameOfEvolvedPokemon;
    ViewPager myPager;
    int adCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String pokemon[] = getResources().getStringArray(R.array.pokemonsandinfo);
        final Dictionary<String, Pokemon> pokemons = new Hashtable<>();
        pokemonSpinner = (Spinner) findViewById(R.id.spinner);
        //region resim idleri
        Integer[] imageArray = {
                R.drawable.abra,
                R.drawable.bellsprout,
                R.drawable.bulbasaur,
                R.drawable.caterpie,
                R.drawable.charmander,
                R.drawable.charmeleon,
                R.drawable.clefairy,
                R.drawable.cubone,
                R.drawable.diglett,
                R.drawable.doduo,
                R.drawable.dragonair,
                R.drawable.dratini,
                R.drawable.drowzee,
                R.drawable.eevee,
                R.drawable.ekans,
                R.drawable.exeggcute,
                R.drawable.gastly,
                R.drawable.geodude,
                R.drawable.gloom,
                R.drawable.goldeen,
                R.drawable.graveler,
                R.drawable.grimer,
                R.drawable.growlithe,
                R.drawable.haunter,
                R.drawable.horsea,
                R.drawable.ivysaur,
                R.drawable.jigglypuff,
                R.drawable.kabuto,
                R.drawable.kadabra,
                R.drawable.kakuna,
                R.drawable.koffing,
                R.drawable.krabby,
                R.drawable.machoke,
                R.drawable.machop,
                R.drawable.magikarp,
                R.drawable.magnemite,
                R.drawable.mankey,
                R.drawable.meowth,
                R.drawable.metapod,
                R.drawable.nidoranf,
                R.drawable.nidoranm,
                R.drawable.nidorina,
                R.drawable.nidorino,
                R.drawable.oddish,
                R.drawable.omanyte,
                R.drawable.paras,
                R.drawable.pidgey,
                R.drawable.pidgeotto,
                R.drawable.pikachu,
                R.drawable.poliwag,
                R.drawable.poliwhirl,
                R.drawable.ponyta,
                R.drawable.psyduck,
                R.drawable.rattata,
                R.drawable.rhyhorn,
                R.drawable.sandshrew,
                R.drawable.seel,
                R.drawable.shellder,
                R.drawable.slowpoke,
                R.drawable.spearow,
                R.drawable.squirtle,
                R.drawable.staryu,
                R.drawable.tentacool,
                R.drawable.venonat,
                R.drawable.voltorb,
                R.drawable.vulpix,
                R.drawable.wartortle,
                R.drawable.weedle,
                R.drawable.weepinbell,
                R.drawable.zubat
        };
        //endregion
        pokemonSpinner.setPrompt("Select a Pokemon");
        String temp[] = new String[70];
        for (int i = 0; i < pokemon.length; i++) {
            String[] parts = pokemon[i].split("-");
            temp[i] = parts[0];
        }
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, temp, imageArray);
        pokemonSpinner.setAdapter(adapter);
        for (int i = 0; i < pokemon.length; i++) {
            if (!pokemon[i].contains("Eevee")) {
                String[] parts = pokemon[i].split("#");
                String[] parts2 = parts[0].split("-");

                pokemons.put(parts2[0],
                        new Pokemon(parts2[0], Double.parseDouble(parts2[1]),
                                Double.parseDouble(parts2[2]), parts[1]
                        ));
            } else if (pokemon[i].contains("Eevee")) {
                String[] parts = pokemon[i].split(";");
                for (int j = 0; j < parts.length; j++
                        ) {
                    String[] parts2 = parts[j].split("#");
                    String[] parts3 = parts2[0].split("-");

                    pokemons.put("Eevee" + String.valueOf(j), new Pokemon("Eevee", Double.parseDouble(parts3[1]),
                            Double.parseDouble(parts3[2]), parts2[1]));
                }
            }
        }
        adView = (AdView) findViewById(R.id.adView);
        evolvedImage = (ImageView) findViewById(R.id.imageView2);
        evolveButton = (Button) findViewById(R.id.button);
        cpView = (TextView) findViewById(R.id.textView);
        nameOfEvolvedPokemon = (TextView) findViewById(R.id.textView2);
        currentCP = (EditText) findViewById(R.id.editText);
        myPager = (ViewPager) findViewById(R.id.myimagepager);
        evolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cpView.setVisibility(View.GONE);
                        nameOfEvolvedPokemon.setVisibility(View.GONE);
                        evolvedImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                loadGecisReklam();
                loadGecisReklam();

                adCount++;

                try {

                    String chosenPokemon = pokemonSpinner.getSelectedItem().toString();
                    if (chosenPokemon == null) {
                        Toast.makeText(MainActivity.this, "Choose a Pokemon", Toast.LENGTH_SHORT).show();
                    } else {


                        if (currentCP.getText().toString().isEmpty()) {

                            Toast.makeText(MainActivity.this, "Enter Combat Power", Toast.LENGTH_SHORT).show();
                        } else {
                            if (chosenPokemon.equals("Eevee")) {
                                Toast.makeText(MainActivity.this, "Slide to see other evolutions of Eevee", Toast.LENGTH_SHORT).show();

                                myPager.setVisibility(View.VISIBLE);
                                evolvedImage.setVisibility(View.GONE);
                                cpView.setVisibility(View.GONE);
                                nameOfEvolvedPokemon.setVisibility(View.GONE);

                                int imageArr[] = new int[3];
                                double cp = Integer.parseInt(currentCP.getText().toString());
                                String[] stringArray = new String[3];
                                for (int i = 0; i < 3; i++) {
                                    Pokemon p = pokemons.get(chosenPokemon + i);
                                    double low = p.getLowerMultiplier();
                                    double high = p.getHigherMultiplier();
                                    String prediction = Math.floor(low * cp) + " CP - " + Math.floor(high * cp) + " CP";
                                    prediction = prediction.replace(".0", "");
                                    imageArr[i] = getResources().getIdentifier(p.getEvolvedPokemon().toLowerCase(), "drawable", getPackageName());
                                    stringArray[i] = p.getEvolvedPokemon() + "\n " + prediction;
                                }

                                PagerAdapter adapter = new InfinitePagerAdapter(new ImagePagerAdapter(MainActivity.this, imageArr, stringArray));
                                myPager.setAdapter(adapter);
                                myPager.setCurrentItem(0);

                            } else {

                                myPager.setVisibility(View.GONE);
                                double cp = Integer.parseInt(currentCP.getText().toString());
                                Pokemon p = pokemons.get(chosenPokemon);
                                double low = p.getLowerMultiplier();
                                double high = p.getHigherMultiplier();
                                nameOfEvolvedPokemon.setText(p.evolvedPokemon);
                                String evolvedPokemonName = chosenPokemon.equals("Bulbasaur") ? "ivysaur" : p.getEvolvedPokemon().toLowerCase();
                                evolvedImage.setImageResource(getResources().getIdentifier(evolvedPokemonName, "drawable", getPackageName()));
                                String prediction = Math.floor(low * cp) + " CP - " + Math.floor(high * cp) + " CP";
                                prediction = prediction.replace(".0", "");
                                cpView.setText(prediction);
                                evolvedImage.setVisibility(View.VISIBLE);
                                cpView.setVisibility(View.VISIBLE);
                                nameOfEvolvedPokemon.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Unexpected Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gecisReklam = new InterstitialAd(this);

        gecisReklam.setAdUnitId("ca-app-pub-8462071283207949/8558809515");//Reklam İd miz.Admob da oluşturduğumuz geçiş reklam id si

        gecisReklam.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() { //Geçiş reklam Yüklendiğinde çalışır

                showGecisReklam();

            }


            @Override
            public void onAdFailedToLoad(int errorCode) { //Geçiş Reklam Yüklenemediğinde  Çalışır


            }

            public void onAdClosed() { //Geçiş Reklam Kapatıldığında çalışır

                loadGecisReklam();
            }
        }); //Geçiş reklama listener ekliyoruz


    }

    public void loadGecisReklam() {


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C521B8BE91B4860C229030D8E3CEA254")
                .build();

        //Reklam Yükleniyor
        gecisReklam.loadAd(adRequest);
        adView.loadAd(adRequest);
    }

    /**
     * reklamGoster butonu tıklanınca çalışacak.
     */
    public void showGecisReklam() {
        // Tekrar reklam yüklenene kadar disable edilecek

        if (gecisReklam.isLoaded()) {//Eğer reklam yüklenmişse kontrol ediliyor
            gecisReklam.show(); //Reklam yüklenmişsse gösterilecek

        } else {//reklam yüklenmemişse
            Toast.makeText(getApplicationContext(), "Reklam Gösterim İçin Hazır Değil.", Toast.LENGTH_LONG).show();
        }
    }
}
