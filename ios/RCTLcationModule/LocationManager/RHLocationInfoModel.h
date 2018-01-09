//
//  RHLocationInfoModel.h
//  LocationMangar
//
//  Created by WSX on 2017/11/15.
//  Copyright © 2017年 _WSX. All rights reserved.
//

#import <Foundation/Foundation.h>

@import CoreLocation;

@interface RHLocationInfoModel : NSObject
///地址名称
@property (nonatomic, strong) NSString* address;
/// 国家
@property (nonatomic, copy) NSString* country;
///城市编码
@property (nonatomic, copy) NSString* cityCode;
/// 城市名称
@property (nonatomic, copy) NSString* city;
/// 街道号码
@property (nonatomic, copy) NSString* streetNumber;
/// 街道名称
@property (nonatomic, copy) NSString* streetName;
/// 区县名称
@property (nonatomic, copy) NSString* district;
/// 省份名称
@property (nonatomic, copy) NSString* province;
///地址纬度
@property (nonatomic, copy) NSString * latitude;
///地址经度
@property (nonatomic, copy) NSString * longitude;
/// 行政区域编码
@property (nonatomic, copy) NSString* adCode;
///定位状态码
@property (nonatomic, copy) NSString * statusCode;
///定位状态说明
@property (nonatomic, copy) NSString * statusMessage;

- (NSMutableDictionary *)createDictionayFromModelProperties;
@end
