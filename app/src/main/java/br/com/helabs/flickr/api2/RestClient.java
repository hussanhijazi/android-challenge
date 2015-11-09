package br.com.helabs.flickr.api2;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import br.com.helabs.flickr.utils.Utils;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by hussan on 09/11/15.
 */
public class RestClient {

    public static final String API_BASE_URL = Utils.FLICKR_API;

    private static OkHttpClient httpClient = new OkHttpClient();
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private static Retrofit.Builder builder =
                    new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

}
