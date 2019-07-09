package net.chinahrd.common;

/**
 * <p>
 *  业务类基础实体，含租户id
 * </p>
 *
 * @author bright
 * @since 2019/3/8 13:49
 */
public class BusinessEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 租户
     */
    protected String tenantId;

    public void setCreateInfo(String userId, String tenantId) {
        super.setCreateInfo(userId);
        setTenantId(tenantId);
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
