package cn.studyjams.s1.sj10.zhuliya.net;

import com.google.gson.Gson;

import cn.studyjams.s1.sj10.zhuliya.utils.GsonUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jan on 16/4/25.
 */
public class IdiomRetrofit {

    private static IdiomRetrofit idiomRetrofit = null;
    private Retrofit retrofit;

    private IdiomRetrofit() {
        if (retrofit == null) {
            Gson gson = GsonUtil.buildGson();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/")//聚合数据成语查询接口
                    .addConverterFactory(GsonConverterFactory.create(gson))//添加Gson数据转换
                    .build();
        }
    }

    /**
     * 获取对应Api接口
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> cls) {
        return retrofit.create(cls);
    }

    public static IdiomRetrofit getInstance() {
        if (idiomRetrofit == null) {
            synchronized (IdiomRetrofit.class) {
                if (idiomRetrofit == null) {
                    idiomRetrofit = new IdiomRetrofit();
                }
            }
        }
        return idiomRetrofit;
    }

}
