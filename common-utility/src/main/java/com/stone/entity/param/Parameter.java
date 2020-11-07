package com.stone.entity.param;

import java.io.Serializable;
import java.util.UUID;

/**
 * 通用入参
 * todo 增加检验等
 * @author yuanxiu
 * @date 2020/11/7
 */
public class Parameter<P> implements Serializable {

    /**
     * 请求唯一编码，必传，推荐使用uuid
     */
    private String serialId;

    /**
     * 业务参数，非必传，具体看接口定义
     */
    private P data;

    public Parameter() {
        this.serialId = UUID.randomUUID().toString().replace("-", "");
    }

    public Parameter(P data) {
        this.serialId = UUID.randomUUID().toString().replace("-", "");
        this.data = data;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public P getData() {
        return data;
    }

    public void setData(P data) {
        this.data = data;
    }
}
