package br.com.helabs.flickr.api;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import br.com.helabs.flickr.utils.Utils;

@Rest(rootUrl = Utils.FLICKR_API, converters = {GsonHttpMessageConverter.class})
public interface FlickApi {


    @Get("/?method=" + Utils.GET_RECENT + "&format="+Utils.FORMAT+"&nojsoncallback=1&extras=" + Utils.EXTRAS+"&api_key={apiKey}&per_page={perPage}&page={page}")
    GetRecentPhotos getRecentPhotos(String apiKey, Integer perPage, Integer page);

    @Get("/?method=" + Utils.GET_INFO + "&format="+Utils.FORMAT+"&nojsoncallback=1&api_key={apiKey}&photo_id={photoId}")
    GetPhotoInfo getPhotoInfo(String apiKey, Long photoId);

    @Get("/?method=" + Utils.GET_PHOTO + "&format="+Utils.FORMAT+"&nojsoncallback=1&api_key={apiKey}&photo_id={photoId}")
    GetPhotoSizes getPhotoSizes(String apiKey, Long photoId);

    @Get("/?method=" + Utils.GET_COMMENTS + "&format="+Utils.FORMAT+"&nojsoncallback=1&extras=" + Utils.EXTRAS+"&api_key={apiKey}&photo_id={photoId}")
    GetPhotoComments getPhotoComments(String apiKey, Long photoId);

}