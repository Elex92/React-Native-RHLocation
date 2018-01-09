//
//  RHLocationManager.m
//  AmapLocationManager
//
//  Created by WSX on 2017/11/30.
//  Copyright © 2017年 _WSX. All rights reserved.
//

#import "RHLocationManager.h"
#import <AMapFoundationKit/AMapFoundationKit.h>
//定位时间
#define DefaultLocationTimeout 3
#define DefaultReGeocodeTimeout 3

@interface RHLocationManager ()<AMapLocationManagerDelegate>
/**  定位管理者  */
@property(nonatomic,strong) AMapLocationManager *locationManager;

@end

static RHLocationManager *manager = nil;

@implementation RHLocationManager

+(RHLocationManager *)sharedLocationManager
{
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        
        manager = [[RHLocationManager alloc]init];
        
    });
    return manager;
}


- (instancetype)init
{
    self = [super init];
    if (self) {
        
        
        self.locationManager = [[AMapLocationManager alloc]init];
        [self.locationManager setDelegate:self];
        
        //设置期望定位精度
        [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
        
        //设置允许在后台定位
//        [self.locationManager setAllowsBackgroundLocationUpdates:YES];

        //设置定位超时时间
        [self.locationManager setLocationTimeout:DefaultLocationTimeout];
        
        //设置逆地理超时时间
        [self.locationManager setReGeocodeTimeout:DefaultReGeocodeTimeout];
    }
    return self;
    
    
}


-(void)obtainLocationInfoWithInfo:(void (^)(RHLocationInfoModel *))info error:(void (^)(LocationError))failure
{
   
    if ([CLLocationManager locationServicesEnabled] == NO){ //手机定位总开关是否没有打开
        NSLog(@"你目前有这个设备的所有位置服务禁用");
        return failure(LocationErrorSystemClose);
    }else{
        CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
        if (kCLAuthorizationStatusDenied == status || kCLAuthorizationStatusRestricted == status) { //授权状态是否为拒绝或者为限制
            NSLog(@"请开启定位服务");
            return failure(LocationErrorAppClose);
        }
        
    }
    
    [self.locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
        
        if (error) {
            if (error.code==3) {
                failure(LocationErrorFailureConnection);
            } else {
                failure(LocationErrorUnknown);
            }
        } else {
            RHLocationInfoModel *model = [[RHLocationInfoModel alloc]init];
            model.address = regeocode.formattedAddress;
            model.country = regeocode.country;
            model.city = regeocode.city;
            model.cityCode = regeocode.citycode;
            model.streetNumber = regeocode.number;
            model.streetName = regeocode.street;
            model.district = regeocode.district;
            model.province = regeocode.province;
            model.adCode = regeocode.adcode;
            model.latitude = [NSString stringWithFormat:@"%f",location.coordinate.latitude];
            model.longitude = [NSString stringWithFormat:@"%f",location.coordinate.longitude];
            model.statusCode = @"000000";
            model.statusMessage=@"定位成功";
            return info(model);
        }
    }];
    
   
}





-(void)dealloc
{
    [self.locationManager stopUpdatingLocation];
    [self.locationManager setDelegate:nil];
}

@end
