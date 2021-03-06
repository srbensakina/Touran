package com.r2a.touran.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.r2a.touran.BuildConfig;
import com.r2a.touran.data.Place;
import com.r2a.touran.service.PlaceService;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BudgetViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<List<List<Place>>> listoflistsofplacesbybudget;

    public MutableLiveData<List<List<Place>>> getPlacesObject(){
        return listoflistsofplacesbybudget;
    }

    public BudgetViewModel(){
        listoflistsofplacesbybudget = new MutableLiveData<>();
    }
    public void getPlacesByBudget(int budget,String[] filter){

        Call<Map<String, List<List<Place>>>> callSync = service.getPlacesByBudget(budget,filter);

        try {
            callSync.enqueue(new Callback<Map<String, List<List<Place>>>>() {
                @Override
                public void onResponse(Call<Map<String, List<List<Place>>>> call, Response<Map<String, List<List<Place>>>> response) {
                    listoflistsofplacesbybudget.setValue(response.body().get("places"));
                }

                @Override
                public void onFailure(Call<Map<String, List<List<Place>>>> call, Throwable t) {
                    Log.e("error","error ",t.fillInStackTrace());
                }
            });
        } catch (Exception ex) {  }
    }
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();
    PlaceService service = retrofit.create(PlaceService.class);
}