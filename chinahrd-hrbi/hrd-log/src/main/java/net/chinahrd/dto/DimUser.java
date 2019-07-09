package net.chinahrd.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DimUser implements Serializable {
    private String userId;

    private String customerId;

    private String empId;

    private String userKey;

    private String userName;

    private String userNameCh;

    private String password;

    private String email;

    private String note;

    private Integer sysDeploy;

    private Integer showIndex;

    private String createUserId;

    private String modifyUserId;

    private Date createTime;

    private Date modifyTime;

    private Boolean isLock;

    private static final long serialVersionUID = 1L;

}