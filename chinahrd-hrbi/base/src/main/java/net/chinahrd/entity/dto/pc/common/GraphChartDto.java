package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.util.List;

/**
 * 关系网图数据dto
 * Created by wqcai on 16/12/20 020.
 */
public class GraphChartDto implements Serializable {
    private static final long serialVersionUID = 39132839800362521L;

    private List<GraphNodesDto> nodes;
    private List<GraphLinksDto> links;

    public List<GraphNodesDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNodesDto> nodes) {
        this.nodes = nodes;
    }

    public List<GraphLinksDto> getLinks() {
        return links;
    }

    public void setLinks(List<GraphLinksDto> links) {
        this.links = links;
    }
}
