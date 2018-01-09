//
//  RHLocationManager.h
//  AmapLocationManager
//
//  Created by WSX on 2017/11/30.
//  Copyright © 2017年 _WSX. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AMapLocationKit/AMapLocationKit.h>
#import "RHLocationInfoModel.h"
typedef NS_ENUM(NSInteger, LocationError) {
    LocationErrorUnknown = 1,                    ///< 未知错误
    LocationErrorSystemClose = 2 ,//系统定位开关关闭
    LocationErrorAppClose = 3 ,//APP定位开关关闭,
    LocationErrorFailureConnection=4 //网络异常
};
@interface RHLocationManager : NSObject



//通过单例创建
+(RHLocationManager *)sharedLocationManager;


/**
 发起单次定位

 @param info 成功回调的信息
 @param failure 失败的回调信息
 */
-(void)obtainLocationInfoWithInfo:(void(^)(RHLocationInfoModel *model))info error:(void(^)(LocationError  error))failure;

@end
