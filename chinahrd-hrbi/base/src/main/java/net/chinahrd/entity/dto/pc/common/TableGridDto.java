package net.chinahrd.entity.dto.pc.common;

import net.chinahrd.entity.dto.KVItemDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqcai on 16/11/12 012.
 */
public class TableGridDto<E> implements Serializable {
    private static final long serialVersionUID = 2066677355219871229L;

    private List<KVItemDto> modals;
    private List<E> data;

    public List<KVItemDto> getModals() {
        return modals;
    }

    public void setModals(List<KVItemDto> modals) {
        this.modals = modals;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
