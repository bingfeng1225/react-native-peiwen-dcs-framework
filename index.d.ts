declare module "react-native-peiwen-dcs-framework" {
    export default class PWDCSFramework {
        static initFramework(deviceid:string,token:string): void;

        static sendTextRequest(content:string): void;

        static releaseFramework(): void;
    }
}
