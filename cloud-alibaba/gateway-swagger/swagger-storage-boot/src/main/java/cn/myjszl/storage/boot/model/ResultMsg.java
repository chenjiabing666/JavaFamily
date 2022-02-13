package cn.myjszl.storage.boot.model;

import lombok.Data;

@Data
public class ResultMsg<T> {
    private Integer code;

    private String msg;

    private T data;
}
