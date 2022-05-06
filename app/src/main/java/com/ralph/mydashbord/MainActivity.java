package com.ralph.mydashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.google.android.material.navigation.NavigationView;
import com.ralph.mydashbord.model.ResponseServer;
import com.ralph.mydashbord.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BarChart bar;
    TableLayout tableLayout;
    Toolbar toolbar;
    DrawerLayout drawerlayout;
    NavigationView navigationView;
    public static final String BASE_URL = "http://192.168.0.109:2021/";

    /*------------ Resaka nombre produit lafo -------------*/
    ArrayList<String> daty = new ArrayList<String>();
    ArrayList<Object[]> data1 = new ArrayList<Object[]>();
    ArrayList<String[]> data2 = new ArrayList<String[]>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*--------------- Hooks -----------------*/
        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        /*------------- toolbar ------------*/
        setSupportActionBar(toolbar);
        /*--------------- Navigation Drawer menu ---------------*/



        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerlayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        /*------------------------------------- Begin GET DATA FROM API ---------------------------------------*/


        bar=(BarChart)findViewById(R.id.barchart);


        ArrayList<BarEntry> information=new ArrayList<>();

        information.add(new BarEntry(2014,1300));
        information.add(new BarEntry(2015,1100));
        information.add(new BarEntry(2016,1000));
        information.add(new BarEntry(2017,1700));
        information.add(new BarEntry(2018,1900));
        information.add(new BarEntry(2019,2200));

        BarDataSet dataset=new BarDataSet(information,"Report");
        dataset.setColors(ColorTemplate.MATERIAL_COLORS);
        dataset.setValueTextColor(Color.BLACK);
        dataset.setValueTextSize(20f);

        BarData barData=new BarData(dataset);

        bar.setFitBars(true);
        bar.setData(barData);
        bar.getDescription().setText("Bar Report Demo");
        bar.animateY(2000);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // on requipere l'access token
        String token = String.valueOf(LoginActivity.getonToken.get(0));

        /* ----------------- nombre produit lafo ------------------ */

        Call<List<ResponseServer>> call = apiService.getByHoraireNumber("Bearer "+token);
        call.enqueue(new Callback<List<ResponseServer>>() {
            @Override
            public void onResponse(Call<List<ResponseServer>> call, Response<List<ResponseServer>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error (:) "+ response.code(), Toast.LENGTH_LONG).show();
                }
                List<ResponseServer> resNb = response.body();
                for(ResponseServer response_server: resNb) {
                    daty.add(response_server.getSelected_date());
                    data1.add(
                            new String[]{
                                    response_server.getSelected_date(),
                                    response_server.getHEURE(),
                                    String.valueOf(response_server.getNombreVendu())
                            }
                    );
                }
                for(int i = 0; i < data1.size(); i += 7) {
                    data2.add(new String[] {
                            (String) data1.get(i)[1],
                            (String) data1.get(i)[2],
                            (String) data1.get(i+1)[2],
                            (String) data1.get(i+2)[2],
                            (String) data1.get(i+3)[2],
                            (String) data1.get(i+4)[2],
                            (String) data1.get(i+5)[2],
                            (String) data1.get(i+6)[2]
                    });
                }
                /* ------------------------ début tableau 1 Nombre ------------------------*/
                tableLayout = (TableLayout)findViewById(R.id.tbl_layout);
                /* -------------------- DEBUT HEADER -----------------------*/
                TableLayout tl = findViewById(R.id.tbl_layout);
                TableRow tr = new TableRow(MainActivity.this);
                tr.setLayoutParams(getLayoutParams());
                tr.addView(getTextView(0, "--|--", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1));
                tr.addView(getTextView(0, daty.get(0), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(1), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(2), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(3), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(4), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(5), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty.get(6), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tl.addView(tr, getTblLayoutParams());

                /* -------------------- FIN HEADER -----------------------*/
                /* -------------------- Debut Row -----------------------*/
                for (int i = 0; i < 24; i++) {
                    TableRow tR = new TableRow(MainActivity.this);
                    tR.setLayoutParams(getLayoutParams());
                    tR.addView(getRowsTextView(0, data2.get(i)[0], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape1 ));
                    tR.addView(getRowsTextView(0, data2.get(i)[1], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[2], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[3], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[4], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[5], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[6], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data2.get(i)[7], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));

                    tableLayout.addView(tR, getTblLayoutParams());
                }
                /* -------------------- FIN Row -----------------------*/


                /* ------------------------ FIN tableau 1 ------------------------*/


                /* affichage console
                System.out.println("------------------------------------------------------------------------");
                for (int i = 0; i < 24; i++){
                    for(int j = 0; j < 8; j++){
                        System.out.print(data2.get(i)[j] +"\t");
                    }
                    System.out.println();
                }*/
            }

            @Override
            public void onFailure(Call<List<ResponseServer>> call, Throwable t) {

                System.out.println("---------------onFailure -------------\n"+ t.getMessage());
                System.out.println("---------------onFailure cause ------------- : "+ t.getCause());
                Toast.makeText(MainActivity.this, "Error (:) "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        /* ----------------- / nombre produit lafo ------------------ */



        /*------------------------------------- End GET DATA FROM API ---------------------------------------*/



    }

    /* ------------------------ tableau ------------------------*/

    private TextView getRowsTextView(int id, String title, int color, int typeface,int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(30, 40, 30, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(30, 40, 30, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }
    /* ------------------------ /tableau ------------------------*/

    @Override
    public void onBackPressed() {
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_stat:
                Intent intentC = new Intent(MainActivity.this, DashbordActivity.class);
                startActivity(intentC);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intentProfile);
            case R.id.nav_logout:
                Intent intentOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentOut);
            case R.id.nav_share:
               Toast.makeText(MainActivity.this, "App Shared successfully", Toast.LENGTH_LONG).show();
                break;
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
       // params.setMargins(1, 1, 1, 1);
        params.weight = 0.5f;
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onClick(View v) {

    }


}