package com.qd.peiwen.dcsframework.devices.ticket;

/**
 * Created by liudunjian on 2018/7/5.
 */

public class ApiConstants {

    public static final String NAME = "OpenUrl";
    public static final String NAMESPACE = "ai.hisense.device_interface.openurl";

    public static final class Directives {
        public static final class Train {
            public static final String NAME = Train.class.getSimpleName();
        }

        public static final class Flight {
          public static final String NAME = Flight.class.getSimpleName();
        }
    }
}
