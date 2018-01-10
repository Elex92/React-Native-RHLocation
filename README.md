# React-Native-RHLocation [![npm version](https://img.shields.io/npm/v/react-native-rhlocation.svg?style=flat)](https://www.npmjs.com/package/react-native-rhlocation)
## **瑞昊RN项目定位组件**


基于高德定位 React Native 模块，支持react native 0.40+


## Install 安装

* npm install react-native-rhlocation --save

## Import 导入

### 自动导入

* react-native link react-native-rhlocation

### 手动导入

#### Android Studio

1. 配置 settings.gradle 

		include ':react-native-rhlocation'

		project(':react-native-rhlocation').projectDir = new File(settingsDir, '../node_modules/react-native-rhlocation/android')


2. 配置 build.gradle 

	
		dependencies {


		compile project(':react-native-rhlocation')
	
		}
3. 配置AndroidMainifest.xml 
#####   配置高德Key
	 	<meta-data android:name="com.amap.api.v2.apikey" android:value="高德Key"> 
##### 	  添加权限（根据需求而定，添加自己想要的权限）
		<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
	    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
	    <!--用于获取运营商信息，用于支持提供运营商信息相关的接口-->
	    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
	    <!--用于访问wifi网络信息，wifi信息会用于进行网络定位-->
	    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
	    <!--用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
	    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
	    <!--用于访问网络，网络定位需要上网-->
	    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
	    <!--用于读取手机当前的状态-->
	    <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
	    <!--用于写入缓存数据到扩展存储卡-->
	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
	    <!--用于申请调用A-GPS模块-->
	    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"></uses-permission>
	    <!--用于申请获取蓝牙信息进行室内定位-->
	    <uses-permission android:name="android.permission.BLUETOOTH"></uses-permission>
	    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"></uses-permission>

4. MainApplication

		new LocationPackage()


#### Xcode

*  react-native-baidu-map/ios/lib下的AMapLocationKit.framework与AMapFoundationKit.framework 文件copy或拖拽到工程文件夹中，左侧目录选中工程名，在 TARGETS->Build Phases-> Link Binary With Libaries 中点击“+”按钮，在弹出的窗口中点击“Add Other”按钮，选择工程目录下的 AMapLocationKit.framework文件添加到工程中

* 需要引入的系统库文件


| 库名称                    | SDK 版本  |iOS 系统版本 
| ----------------------- |:-----:| :-------:
| JavaScriptcore.framework     | 基础库 1.3.0版本、定位2.1.1版本之后必需  | －     
| SystemConfiguration.framework         | －   | －     
| CoreTelephony.framework     | －   | －   
| CoreLocation.framework                | － | －        
| zoom                    | － | －      
| libz.dylib                 | － | iOS 9之前     
| libc++.dylib                 | － | iOS 9之前    
| libstdc++.6.0.9.dylib               | －  | iOS 9之前     
| libz.tbd  | －   | iOS 9之后
| libc++.tbd      | －   | iOS 9之后
| libstdc++.6.0.9.tbd | －   | iOS 9之后
| Security.framework            | －   | －

* 在 TARGETS->General->Linked Frameworks and Libraries 中点击“+”，依次查找上述文件，添加到工程中，如下如所示：


![](http://a.amap.com/lbs/static/img/ios_location_sdk_libs.png)

* 定位权限


在项目的 Info.plist 添加定位权限申请，根据您的业务需求，选择下列方式设置。

iOS 8 - iOS 10 版本： 
 
NSLocationWhenInUseUsageDescription 表示应用在前台的时候可以搜到更新的位置信息。
NSLocationAlwaysUsageDescription 申请Always权限，以便应用在前台和后台（suspend 或 terminated）都可以获取到更新的位置数据。

![](http://a.amap.com/lbs/static/img/ios_location_sdk_permission.png)

iOS 11 版本：

NSLocationAlwaysAndWhenInUseUsageDescription 申请Always权限，以便应用在前台和后台（suspend 或 terminated）都可以获取到更新的位置数据（NSLocationWhenInUseUsageDescription 也必须有）。

![](https://a.amap.com/lbs/static/img/iOS11%E6%9D%83%E9%99%90.png)

* AppDelegate.m init 初始化

		#import <AMapFoundationKit/AMapFoundationKit.h>
		
		- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
		{
  		  	...
    		[AMapServices sharedServices].apiKey =@"高德Key";
    		...
		}

## React Native使用

### 定位方法使用

1. 引用

	`import LocationModule from 'react-native-rhlocation';`


2. 同步使用

		async componentDidMount(){


  		let locationModel= await  LocationModule.startLocation();

		}


3. 异步使用

		LocationModule.startLocation().then((locationModel)=>{

   
  		});

### LcationModel属性说明

| option                  | description  |iOS |Android
| ----------------------- |:-----:| :-------:| :-------:
| address    | 地址详情  | OK|OK     
| country         | 国家   | OK|OK     
| cityCode     | 城市编码  | OK|OK   
| city                | 城市名称 | OK|OK        
| streetNumber                    | 街道编码 | OK|OK      
| streetName                 | 街道名称 | OK|OK       
| district                | 区县名称 | OK|OK     
| province               | 省份名称  | OK|OK      
| latitude  | 地址纬度   | OK|OK  
| longitude      | 地址经度   | OK|OK  
| adCode | 行政区域编码  | OK|OK  
| statusCode           | 定位状态码   | OK|－ 
| statusMessage           | 定位状态说明   | OK| －
| locationType           | 定位状态类型   | －| OK
| gpsStatus           | GPS状态   | －| OK





