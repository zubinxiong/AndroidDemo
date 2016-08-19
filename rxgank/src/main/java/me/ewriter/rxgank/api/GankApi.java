package me.ewriter.rxgank.api;

import me.ewriter.rxgank.api.entity.GankData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Zubin on 2016/8/16.
 */
public interface GankApi {

//    http://gank.io/api/data/Android/10/1
//    http://gank.io/api/data/福利/10/1
//    http://gank.io/api/data/iOS/20/2
//    http://gank.io/api/data/all/20/2

    int MAX_ONEPAGE = 20;

    /**
     *
     * @param category 可以为 Android ，福利，iOS 和 all 四个参数
     * @param page
     * @return
     */
    @GET("data/{category}/" + MAX_ONEPAGE + "/{page}")
    Observable<GankData> getGankData(@Path("category") String category, @Path("page") int page);

}
