//
//  LocationModule.m
//  LocationModule
//
//  Created by 沈丰元 on 2018/1/8.
//  Copyright © 2018年 沈丰元. All rights reserved.
//

#import "LocationModule.h"
#import "RHLocationManager.h"
#import "RHLocationInfoModel.h"
@implementation LocationModule
RCT_EXPORT_MODULE();


RCT_REMAP_METHOD(startLocation,
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{

    RHLocationManager * manager = [RHLocationManager sharedLocationManager];
    [manager obtainLocationInfoWithInfo:^(RHLocationInfoModel *model) {

        resolve([model createDictionayFromModelProperties]);

    } error:^(LocationError error) {

        RHLocationInfoModel *model = [[RHLocationInfoModel alloc]init];
        switch (error) {
            case LocationErrorUnknown:
            {
                model.statusCode = @"100000";
                model.statusMessage=@"未知错误";
                resolve([model createDictionayFromModelProperties]);
                
            }
                break;

            case LocationErrorSystemClose:
            {
                model.statusCode = @"200000";
                model.statusMessage=@"系统定位开关关闭";
                resolve([model createDictionayFromModelProperties]);
            }
                break;
            case LocationErrorAppClose:
            {
                model.statusCode = @"300000";
                model.statusMessage=@"APP定位开关关闭";
                resolve([model createDictionayFromModelProperties]);
            }
                break;
            case LocationErrorFailureConnection:
            {
                model.statusCode = @"400000";
                model.statusMessage=@"网络异常";
                resolve([model createDictionayFromModelProperties]);
            }
                break;

            default:
                break;
        }

        
    }];

}

@end
