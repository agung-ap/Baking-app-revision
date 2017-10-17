package id.developer.agungaprian.bakingapprevisi2.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import id.developer.agungaprian.bakingapprevisi2.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by agungaprian on 27/09/17.
 */

public class RetrofitBuilder {
    private static RetrofitInterface retrofitInterface;

    public static RetrofitInterface retrieveData(){
        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        retrofitInterface = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpBuilder.build())
                .build()
                .create(RetrofitInterface.class);

        return retrofitInterface;
    }
}
