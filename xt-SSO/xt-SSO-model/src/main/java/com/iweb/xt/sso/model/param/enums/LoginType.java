package com.iweb.xt.sso.model.param.enums;

import java.util.HashMap;
import java.util.Map;

public enum LoginType {

/*    look name*/
    WX(1, "Wx 登录");

    private static final Map<Integer, LoginType> CODE_MAP = new HashMap<>(3);

    static {
        for (LoginType loginType : values()) {
            CODE_MAP.put(loginType.getCode(), loginType);
        }
    }
    private int code;
    private String msg;
//  根据code获取枚举值
    public static LoginType valueOfCode(int code) {
        return CODE_MAP.get(code);
    }
    LoginType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
