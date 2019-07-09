package net.chinahrd.constants;

/**
 * Title: 是/非 <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 13:53
 * @Verdion 1.0 版本
 * ${tags}
 */
public enum YesOrOnEnum {


    lock() {
        @Override
        public Integer enable() {
            return 1;
        }

        @Override
        public Integer disable() {
            return 0;
        }
    };

    public abstract Integer enable();

    public abstract Integer disable();


}
