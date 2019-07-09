package net.chinahrd.bean;

import java.io.Serializable;

/**
 * @author guanjian
 *
 */
public class CheckErrorResultBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -1173454035593764104L;
    private String name;
    private String message;

    public CheckErrorResultBean(String name, String message) {
        super();
        this.name = name;
        this.message = message;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CheckErrorResultBean [" + (name != null ? "name=" + name + ", " : "")
                + (message != null ? "message=" + message : "") + "]";
    }

}
