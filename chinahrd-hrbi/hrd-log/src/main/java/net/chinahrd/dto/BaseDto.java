package net.chinahrd.dto;

import lombok.Data;

import java.util.Date;

/**
 * Title: hrd-log <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年07月23日 21:48
 * @Verdion 1.0 版本
 * ${tags}
 */
@Data
public class BaseDto {
    private String customerId;
    private Date refresh;
    private String z;
}
