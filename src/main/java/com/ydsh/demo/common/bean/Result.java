package com.ydsh.demo.common.bean;

import lombok.Data;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:22
 **/
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
}
