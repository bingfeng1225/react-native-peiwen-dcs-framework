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
import com.qd.peiwen.dcsframework.devices.system.message.entity.ErrorMessager;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * ExceptionEncountered事件对应的payload结构
 * <p>
 * Created by wuruisheng on 2017/6/3.
 */
public class ExceptionEncounteredPayload extends BasePayload {
    @SerializedName("error")
    private ErrorMessager error;
    @SerializedName("unparsedDirective")
    private String unparsedDirective;

    public ExceptionEncounteredPayload() {
    }

    public ErrorMessager getError() {
        return error;
    }

    public void setError(ErrorMessager error) {
        this.error = error;
    }

    public String getUnparsedDirective() {
        return unparsedDirective;
    }

    public void setUnparsedDirective(String unparsedDirective) {
        this.unparsedDirective = unparsedDirective;
    }

}