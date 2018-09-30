declare module "react-native-peiwen-dcs-framework" {
    export default class PWDCSFramework {
        static initFramework(param:object): void;

        static enterBackground(): void;
        static becomeForeground(): void;
        static audioRecordStarted() : void;
        static audioRecordFinished() : void;

        static sendTextRequest(param:object): void;

        static releaseFramework(): void;
    }
}
