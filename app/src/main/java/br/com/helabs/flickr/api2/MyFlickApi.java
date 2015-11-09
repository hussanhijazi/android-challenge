package br.com.helabs.flickr.api2;

import br.com.helabs.flickr.api.GetRecentPhotos;
import br.com.helabs.flickr.utils.Utils;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hussan on 09/11/15.
 */
public interface MyFlickApi {

    //@Get("/?method=" + Utils.GET_RECENT + "&format="+Utils.FORMAT+"&nojsoncallback=1&extras=" + Utils.EXTRAS+"&api_key={apiKey}&per_page={perPage}&page={page}")

    @GET("?method=" + Utils.GET_RECENT + "&api_key="+Utils.FLICKR_KEY+"&format="+Utils.FORMAT+"&nojsoncallback=1&extras=" + Utils.EXTRAS)
    @Headers("Cache-Control: max-age=640000")
    Observable<GetRecentPhotos> getPhotos(@Query("per_page") int perPage, @Query("page") int page);
}
