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
package com.qd.peiwen.dcsframework.entity.header;

import com.google.gson.annotations.SerializedName;

/**
 * 带请求会话Id头部
 * <p>
 * Created by wuruisheng@baidu.com on 2017/5/31.
 */
public class DialogIdHeader extends MessageIdHeader {

    @SerializedName("dialogRequestId")
    private String dialogId;

    public DialogIdHeader() {

    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }
}