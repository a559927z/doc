package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

/**
 * 关系网图links数据dto
 * Created by wqcai on 16/12/20 020.
 */
public class GraphLinksDto implements Serializable {
    private static final long serialVersionUID = -7068582055253737850L;

    private String id;              //主键ID
    private String source;          //元数据ID
    private String target;          //关联数据ID

    public GraphLinksDto() {
    }

    public GraphLinksDto(String id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
