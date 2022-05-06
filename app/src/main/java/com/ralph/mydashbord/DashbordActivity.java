package com.ralph.mydashbord;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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

public class DashbordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private PieChart pieChart;
    private TableLayout tableLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;

    private TableLayout tableLayout2;

    public static final String BASE_URL = "http://192.168.0.109:2021/";

    /*------------ Resaka vola niditra -------------*/
    ArrayList<Object[]> data3 = new ArrayList<Object[]>();
    ArrayList<String[]> data4 = new ArrayList<String[]>();
    ArrayList<String> daty2 = new ArrayList<String>();

    /*------------ piechart -------------*/
    ArrayList<PieEntry> entries = new ArrayList<>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashbord);



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

        navigationView.setCheckedItem(R.id.nav_stat);



        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();

        /*------------------------------------- Begin GET DATA FROM API ---------------------------------------*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // on requipere l'access token
        String token = String.valueOf(LoginActivity.getonToken.get(0));

        /* ----------------- Vola niditra  ------------------*/
        Call<List<ResponseServer>> call2 = apiService.getByHoraireMoney("Bearer "+token);
        call2.enqueue(new Callback<List<ResponseServer>>() {
            @Override
            public void onResponse(Call<List<ResponseServer>> call, Response<List<ResponseServer>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DashbordActivity.this, "Error (:) " + response.code(), Toast.LENGTH_LONG).show();
                }
                List<ResponseServer> resVola = response.body();

                for(ResponseServer response_server: resVola) {
                    daty2.add(response_server.getSelected_date());
                    data3.add(
                            new String[]{
                                    response_server.getSelected_date(),
                                    response_server.getHEURE(),
                                    String.valueOf(response_server.getVola())
                            });
                }


                for(int i = 0; i < data3.size(); i += 7) {
                    data4.add(new String[] {
                            (String) data3.get(i)[1],
                            (String) data3.get(i)[2],
                            (String) data3.get(i+1)[2],
                            (String) data3.get(i+2)[2],
                            (String) data3.get(i+3)[2],
                            (String) data3.get(i+4)[2],
                            (String) data3.get(i+5)[2],
                            (String) data3.get(i+6)[2]
                    });
                }

                /* ------------------------ début tableau 2 Nombre ------------------------*/


                tableLayout2 = (TableLayout)findViewById(R.id.tbl_layout2);

                /* -------------------- DEBUT HEADER -----------------------*/

                TableLayout tl = findViewById(R.id.tbl_layout2);
                TableRow tr = new TableRow(DashbordActivity.this);
                tr.setLayoutParams(getLayoutParams());
                tr.addView(getTextView(0, "-----", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1));

                tr.addView(getTextView(0, daty2.get(0), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(1), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(2), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(3), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(4), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(5), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));
                tr.addView(getTextView(0, daty2.get(6), Color.BLACK, Typeface.BOLD, R.drawable.cell_shape1 ));

                tl.addView(tr, getTblLayoutParams());

                /* -------------------- FIN HEADER -----------------------*/



                /* -------------------- Debut Row -----------------------*/


                for (int i = 0; i < 24; i++) {

                    TableRow tR = new TableRow(DashbordActivity.this);
                    tR.setLayoutParams(getLayoutParams());

                    tR.addView(getRowsTextView(0, data4.get(i)[0], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape1 ));
                    tR.addView(getRowsTextView(0, data4.get(i)[1], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[2], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[3], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[4], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[5], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[6], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
                    tR.addView(getRowsTextView(0, data4.get(i)[7], Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));

                    tableLayout2.addView(tR, getTblLayoutParams());
                }
                /* -------------------- FIN Row -----------------------*/

                /* ------------------------ FIN tableau 2 ------------------------*/

                System.out.println("------------------------------------------------------------------------");
                for (int i = 0; i < 24; i++){
                    for(int j = 0; j < 8; j++){
                        System.out.print(data4.get(i)[j] +"\t");
                    }
                    System.out.println();
                }

            }

            @Override
            public void onFailure(Call<List<ResponseServer>> call, Throwable t) {

                System.out.println(t.getStackTrace());
                Toast.makeText(DashbordActivity.this, "Error (:) "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

        /*----------------- / Vola niditra ------------------ */

        Call<List<ResponseServer>> call3 = apiService.getByHoraireMoney("Bearer "+token);


        call3.enqueue(new Callback<List<ResponseServer>>(){

            @Override
            public void onResponse(Call<List<ResponseServer>> call, Response<List<ResponseServer>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DashbordActivity.this, "Error (:) " + response.code(), Toast.LENGTH_LONG).show();
                }
                List<ResponseServer> result = response.body();


                for(ResponseServer server: result) {
                    entries.add(new PieEntry((float) server.getBoby(), server.getJour()));
                }
                for (int color: ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color);
                }

                for (int color: ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entries, "Vola");
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(pieChart));
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                pieChart.invalidate();

                pieChart.animateY(1400, Easing.EaseInOutQuad);


            }

            @Override
            public void onFailure(Call<List<ResponseServer>> call, Throwable t) {
                Toast.makeText(DashbordActivity.this, "Error (:) "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("PC be mpividy");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5f, "HP"));
        entries.add(new PieEntry(4f, "ASUS"));
        entries.add(new PieEntry(0.5f, "MSI"));
        entries.add(new PieEntry(1f, "TOSHIBA"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Vola");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @Override
    public void onClick(View v) {

    }
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
            case R.id.nav_stat:
                break;

            case R.id.nav_home:
                Intent intentC = new Intent(DashbordActivity.this, MainActivity.class);
                startActivity(intentC);

            case R.id.nav_profile:
                Intent intentProfile = new Intent(DashbordActivity.this, ProfilActivity.class);
                startActivity(intentProfile);

            case R.id.nav_logout:
                Intent intentOut = new Intent(DashbordActivity.this, LoginActivity.class);
                startActivity(intentOut);
            case R.id.nav_share:
                Toast.makeText(DashbordActivity.this, "App Shared successfully", Toast.LENGTH_LONG).show();
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
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }
    /* ------------------------ tableau ------------------------*/

    private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
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
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }
    /* ------------------------ /tableau ------------------------*/

}
