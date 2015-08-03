# Demo_Volley
>教程：

>http://my.oschina.net/u/559701/blog/150124

>http://blog.csdn.net/xyz_lmn/article/details/12063561

>http://www.inferjay.com/blog/2013/08/03/google-i-o-2013-volley-image-cache-tutorial/

#Volley基本使用
1）get请求
```
mQueue = Volley.newRequestQueue(getApplicationContext());
mQueue.add(new JsonObjectRequest(Method.GET, url, null,
            new Listener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "response : " + response.toString());
                }
            }, null));
mQueue.start();
```

2）给ImageView设置图片源
```
// imageView是一个ImageView实例
// ImageLoader.getImageListener的第二个参数是默认的图片resource id
// 第三个参数是请求失败时候的资源id，可以指定为0
ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
mImageLoader.get(url, listener);
```

3）NetworkImageView
Volley提供了一个新的控件NetworkImageView来代替传统的ImageView
```
mImageView.setImageUrl(url, imageLoader)
```
这个控件在被从父控件detach的时候，会自动取消网络请求的，即完全不用我们担心相关网络请求的生命周期问题。 
示例代码如下：
```
NetworkImageView view = (NetworkImageView) findViewById(R.id.network_image_view);
view.setImageUrl(url, new ImageLoader(mQueue, new BitmapCache()));
```

4) 使用ImageLoader
```
mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
... ...

if(holder.imageRequest != null) {
    holder.imageRequest.cancel();
}
holder.imageRequest = mImageLoader.get(BASE_UR + item.image_url, holder.imageView, R.drawable.loading, R.drawable.error);
```
>注意，这里使用的不是ImageView控件，而是Volley新提供的com.android.volley.NetworkImageView。

>另外，注意这里：
```
mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
```

ImageLoader构造函数的第二个参数是一个ImageCache的实例（严格来说，是实现ImageCache接口的某具体类的实例） 
ImageCache的定义如下（在ImageLoader.java里）：
```
/**
 * Simple cache adapter interface. If provided to the ImageLoader, it
 * will be used as an L1 cache before dispatch to Volley. Implementations
 * must not block. Implementation with an LruCache is recommended.
 */
public interface ImageCache {
    public Bitmap getBitmap(String url);
    public void putBitmap(String url, Bitmap bitmap);
}
```
