import {
    Platform,
    NativeModules,
    Promise
} from 'react-native';

let ios = Platform.OS === 'ios';
let android = Platform.OS === 'android';
let location = NativeModules.LocationModule;

export default class Lcation{
   static start(){
    if(android){
        return new Promise((resolve, reject) =>{
                NativeModules.LocationModule.startLocation().then(
                (location)=>{
                  resolve(location);
                }).catch(
                    (error)=>{
                         reject(error);
                    }
                );
    
              });
        }
    }
   

}