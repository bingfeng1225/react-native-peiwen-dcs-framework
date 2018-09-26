declare module "react-native-peiwen-dcs-framework" {
    export default class PWDCSFramework {
        static initFramework(param:object): void;

        static sendTextRequest(content:string): void;

        static releaseFramework(): void;
    }
}
