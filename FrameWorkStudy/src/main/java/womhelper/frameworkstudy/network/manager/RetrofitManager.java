package womhelper.frameworkstudy.network.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import womhelper.frameworkstudy.App;
import womhelper.frameworkstudy.network.service.ApiService;
import womhelper.frameworkstudy.utils.NetUtil;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {

    public static final String BASE_URL = "http://123.147.164.50:28000/";
    //超时时间(秒)
    public static final int OUT_TIME_SECONDS = 15;
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    //缓存大小50Mb
    public static final int CACHE_SIZE = 1024 * 1024 * 50;
    //缓存事件控制Header
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "public, only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static RetrofitManager mRetrofitManager;
    private final ApiService mApiService;

    public static RetrofitManager builder() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 指定缓存路径
        Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                CACHE_SIZE);

        return new OkHttpClient.Builder()
//                .cache(cache)
                //屏蔽掉缓存策略
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(logInterceptor)
//                .addInterceptor(mBodyInterceptor)
                .connectTimeout(OUT_TIME_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    // 云端响应头拦截器，用于配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            String cacheControl = CACHE_CONTROL_CACHE;
            if (NetUtil.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                cacheControl = request.cacheControl().toString();
            }
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        }
    };

    //Body加密拦截器,用于处理参数加密
    private Interceptor mBodyInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();

            //获取Body 字符串
            String body = getBodyString(request);
            //转换Body为Map对象
            Map<String, String> bodyMap = converFormBody(body);

            return handleResponse(chain.proceed(builder.build()));
        }
    };

    /**
     * 获取BodyString
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getBodyString(Request request) throws IOException {
        String body = null;
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer sink = new Buffer();
            requestBody.writeTo(sink);
            body = sink.readUtf8();

        }

        return body;
    }

    /**
     * 转换Body参数
     *
     * @param body
     * @return
     */
    private Map<String, String> converFormBody(String body) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject json = new JSONObject(body);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = json.getString(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 处理响应
     *
     * @param response
     * @return
     */
    private Response handleResponse(Response response) {


        return response;
    }

    public ApiService getApiService() {
        return mApiService;
    }


}
