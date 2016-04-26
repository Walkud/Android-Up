package cn.studyjams.s1.sj10.zhuliya.utils;

import java.util.ArrayList;

/**
 * Created by jan on 16/4/25.
 */
public class Urls {

    private static final ArrayList<String> bannerUrls = new ArrayList<>();

    static {
        bannerUrls.add("http://photo.l99.com/bigger/2e3/1435333868043_mx4hn2.jpg");
        bannerUrls.add("http://i1.w.hjfile.cn/doc/201208/47427d3c8cef460e9f9b846557a89260.jpg");
        bannerUrls.add("http://a1.mzstatic.com/us/r1000/094/Purple/53/17/98/mzl.fqdowxli.480x480-75.jpg");
        bannerUrls.add("http://www.exam58.com/uploads/allimg/140401/101043B18-0.jpg");
        bannerUrls.add("http://www.haiwainet.cn/HLMediaFile/20131118/79/14343975388726357191.jpg");
    }

    public static ArrayList getBannerUrls() {
        return bannerUrls;
    }
}
