/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qd.peiwen.dcsframework.devices.system.message.event;


import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;


public class UserInactivityReportPayload extends BasePayload {
    @SerializedName("inactiveTimeInSeconds")
    private long inactiveTimeInSeconds;

    public UserInactivityReportPayload() {
    }

    public UserInactivityReportPayload(long inactiveTimeInSeconds) {
        this.inactiveTimeInSeconds = inactiveTimeInSeconds;
    }

    public long getInactiveTimeInSeconds() {
        return inactiveTimeInSeconds;
    }

    public void setInactiveTimeInSeconds(long inactiveTimeInSeconds) {
        this.inactiveTimeInSeconds = inactiveTimeInSeconds;
    }
}