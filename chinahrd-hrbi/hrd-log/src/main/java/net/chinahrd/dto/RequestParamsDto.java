package net.chinahrd.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jxzhang on 2017年7月19日
 * @Verdion 1.0 版本
 */
@Data
public class RequestParamsDto implements Serializable {

    private static final long serialVersionUID = 3902450654731368099L;

    private int start; // 分页开始行
    private int length; // 每页大小
    private String[] ids; // PK可作一些删除或更新操作

    private String oper;

    private int startYm;
    private int endYm;


}
