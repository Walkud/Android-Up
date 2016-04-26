package cn.studyjams.s1.sj10.zhuliya.net.api;

import cn.studyjams.s1.sj10.zhuliya.bean.Idiom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jan on 16/4/25.
 */
public interface IdiomApi {

    @GET("chengyu/query")
    Call<Idiom> getIdiom(@Query("key") String key, @Query("word") String word);
}
