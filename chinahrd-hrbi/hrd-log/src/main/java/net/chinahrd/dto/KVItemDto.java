package net.chinahrd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 键值类
 *
 * @author jxzhang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KVItemDto<K, V> implements Serializable, Comparable<KVItemDto> {
    /** */
    private static final long serialVersionUID = 5823266214178936888L;
    /**
     * 键
     */
    private K k;
    /**
     * 值
     */
    private V v;


//    private int getCompareV() {
//
//        try {
//            return Integer.parseInt(this.v);
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(KVItemDto o) {
        int thisV = (Integer)this.getV();
        int thatV = (Integer)o.getV();
        return thisV > thatV ? 1 : thisV == thatV ? 0 : -1;
    }

}
