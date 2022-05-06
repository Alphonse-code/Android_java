package com.ralph.mydashbord.service;

import com.ralph.mydashbord.model.ResponseServer;
import com.ralph.mydashbord.model.Vente;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ApiService {

    // get all from table vente
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getAllVente")
    Call<List<Vente>> getVenteList(@Header("Authorization") String auth);
    /*----------------------------  Begin STATISTIC -----------------------------*/
    // Statistic : ISANY PRODUIT LAFO par heure dans [J-7,J]
    @Headers({"Content-Type: application/json; charset=UTF-8"})
    @GET("parHeure")
    Call<List<ResponseServer>> getByHoraireNumber(@Header("Authorization") String auth);

    // Statistic : VOLA NIDITRA par Heure dans [J-7,J]
    @Headers({"Content-Type: application/json; charset=UTF-8"})
    @GET("parHeureVola")
    Call<List<ResponseServer>> getByHoraireMoney(@Header("Authorization") String auth);

    // Statistic : VOLA NIDITRA par jour dans [J-7,J]
    @Headers({"Content-Type: application/json; charset=UTF-8"})
    @GET("parJourVola")
    Call<List<ResponseServer>> getByDayMoney(@Header("Authorization") String auth);

    /*----------------------------  End STATISTIC -----------------------------*/

}