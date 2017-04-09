package com.example.lcc.mykitchen.manager;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lcc.mykitchen.R;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.example.lcc.mykitchen.MyApp;
import com.example.lcc.mykitchen.entity.FoodFromWeb;
import com.example.lcc.mykitchen.entity.TypeFoodsFromWeb;


/**
 * Created by lcc on 2017/1/18.
 */
public class HttpRequestManager {
    public static RequestQueue queue = Volley.newRequestQueue(MyApp.context);
    private static ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
        //LRU:least recently use
        LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getRowBytes();
            }
        };

        //TODO 磁盘缓存
        @Override
        public void putBitmap(String arg0, Bitmap arg1) {
            cache.put(arg0, arg1);
        }

        @Override
        public Bitmap getBitmap(String arg0) {
            return cache.get(arg0);
        }
    });

    /**
     * 具体美食做法的网络请求
     *
     * @param foodname
     * @param listener
     */
    public static void requestFood(String foodname, final loadFoodListener listener) {
        try {
            foodname = URLEncoder.encode(foodname, "utf8");
            String url = "http://apis.juhe.cn/cook/query?menu=" +
                    foodname +
                    "&dtype=&pn=&rn=10&albums=&=&key=af876b753d435da8de2e31323098d1f9";
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    FoodFromWeb bean = gson.fromJson(s, FoodFromWeb.class);
                    List<FoodFromWeb.Detials> datas = bean.getResult().getData();
                    listener.foodShow(datas);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            queue.add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 菜品的网络请求
     */
    public static void requestFoodType(final loadFoodTypeListener listener) {
        String url = "http://apis.juhe.cn/cook/category?parentid=&dtype=&key=af876b753d435da8de2e31323098d1f9";
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               Gson gson=new Gson();
                TypeFoodsFromWeb typeFoods=gson.fromJson(s, TypeFoodsFromWeb.class);
                List<TypeFoodsFromWeb.FoodResult> datas=typeFoods.getResult();
                listener.foodTypeShow(datas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    public static void displayImage(String url, ImageView iv) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.bucket_no_picture, R.drawable.bucket_no_picture);
        imageLoader.get(url, listener);
    }

    public interface loadFoodListener {
        public void foodShow(List<FoodFromWeb.Detials> bean);
    }
    public interface loadFoodTypeListener {
        public void foodTypeShow(List<TypeFoodsFromWeb.FoodResult> bean);
    }
}
