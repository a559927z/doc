package net.chinahrd.entity.dto.pc;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.talentContrast.ContrastEmpDto;

/**
 * 存储对比员工返回对象
 * Created by wqcai on 2016/1/20 020.
 */
public class StoreContrastDto implements Serializable {
    private static final long serialVersionUID = 5503261207419213460L;
    /** 0：操作成功 1：超过4个 2：人员信息存在 */
    private int type;
    /** 存储对象里员工集合 */
    private List<ContrastEmpDto> emps;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ContrastEmpDto> getEmps() {
        return emps;
    }

    public void setEmps(List<ContrastEmpDto> emps) {
        this.emps = emps;
    }
}
