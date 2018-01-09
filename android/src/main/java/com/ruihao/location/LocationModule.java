package com.ruihao.location;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationQualityReport;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
/**
 * Created by elex on 2017/11/28.
 */

public class LocationModule extends ReactContextBaseJavaModule {
    private AMapLocationClient client = null;
    private Object  objLock = new Object();
    private AMapLocationClientOption mLocationOption = null;
    protected ReactApplicationContext context;
    private Promise promise;
    /***
     * 实例化
     * @param
     */

    public LocationModule(ReactApplicationContext reactContext){
        super(reactContext);
        context = reactContext;

        synchronized (objLock) {
            if(client == null){

                client = new AMapLocationClient(context.getApplicationContext());

                client.setLocationListener(mLocationListener);
                setOption();


            }
        }
    }



    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {

            if (null != location) {
                WritableMap params = Arguments.createMap();
                params.putString("address", location.getAddress());
                params.putString("city", location.getCity());
                params.putString("cityCode", location.getCityCode());
                params.putString("country", location.getCountry());
                params.putString("streetName", location.getStreet());
                params.putString("streetNumber", location.getStreetNum());
                params.putString("district", location.getDistrict());
                params.putDouble("latitude", location.getLatitude());
                params.putDouble("longitude", location.getLongitude());
                params.putString("province", location.getProvince());
                params.putInt("locationType", location.getLocationType());
                params.putString("gpsStatus",getGPSStatusString(location.getLocationQualityReport().getGPSStatus()));
                promise.resolve(params);
            } else {
               
                promise.reject( Integer.toString(location.getErrorCode()),location.getErrorInfo());
            }
        }
    };
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /***
     * 设置定位默认参数
     *
     */
    public void setOption(){
        if (mLocationOption ==null) {
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            mLocationOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            mLocationOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            mLocationOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
            mLocationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
            mLocationOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
//            mLocationOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            mLocationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            mLocationOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        }
       client.setLocationOption(mLocationOption);

    }

    @Override
    public String getName() {
        return "LocationModule";
    }
    /***
     *开始定位
     */
    @ReactMethod
    public void startLocation(Promise promise1){
        setOption();
        client.startLocation();

        promise = promise1;

    }
    /***
     *结束定位
     */
    @ReactMethod
    public void stopLocation(){

        client.stopLocation();

    }

}
