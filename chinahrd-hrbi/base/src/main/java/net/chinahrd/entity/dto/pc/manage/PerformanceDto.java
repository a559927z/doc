package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;

/**
 * Created by dcli on 2015/12/3.
 */
public class PerformanceDto  implements Serializable{
    private static final long serialVersionUID = 1L;

    private String Content;

    private double Weight;

    private double Progress;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double getProgress() {
        return Progress;
    }

    public void setProgress(double progress) {
        Progress = progress;
    }
}
