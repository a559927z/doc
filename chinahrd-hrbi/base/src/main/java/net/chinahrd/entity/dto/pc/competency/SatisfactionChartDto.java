package net.chinahrd.entity.dto.pc.competency;

/**
 * Created by Administrator on 2016/3/19.
 */
public class SatisfactionChartDto {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurorgscore() {
        return curorgscore;
    }

    public void setCurorgscore(String curorgscore) {
        this.curorgscore = curorgscore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComscore() {
        return comscore;
    }

    public void setComscore(String comscore) {
        this.comscore = comscore;
    }

    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    private String date;
    private String curorgscore;
    private String id;
    private String name;
    private String comscore;
    private String parent;
}
