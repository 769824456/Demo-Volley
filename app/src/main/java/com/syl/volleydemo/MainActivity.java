package com.syl.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * PACKAGE_NAME :com.syl.volleydemo
 * VERSION :[V 1.0.0]
 * AUTHOR :  yulongsun
 * CREATE AT : 7/28/2015 11:12 AM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :Volley
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.btn_get)
    Button btnGet;
    @Bind(R.id.btn_set_image_view)
    Button btnSetImageView;
    @Bind(R.id.iv_item)
    ImageView ivItem;
    @Bind(R.id.niv_item)
    NetworkImageView nivItem;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private String url;
    private ImageLoader.ImageListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        setNetworkImageView();
    }

    private void init() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    /**
     * 1.普通Get请求
     */
    @OnClick(R.id.btn_get)
    void doGet() {
        url = "http://my.oschina.net/u/559701/blog/150124";
        mQueue.add(new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response:" + response.toString());
            }
        }, null));
        mQueue.start();
    }

    /**
     * 2.给ImageView设置图片源
     */
    @OnClick(R.id.btn_set_image_view)
    void setImageView() {
        //第一个参数：ImageView实例
        //第二个参数：加载前图片
        //第三个参数：加载失败参数
        mListener = ImageLoader.getImageListener(ivItem, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        url="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2686363424,2160144628&fm=58";
        //设置图片
        mImageLoader.get(url, mListener);

    }


    /**
     * 3.为NetworkImageView设置ImageView
     */
    void setNetworkImageView(){
        url="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2686363424,2160144628&fm=58";
        //设置图片
        nivItem.setImageUrl(url,mImageLoader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
