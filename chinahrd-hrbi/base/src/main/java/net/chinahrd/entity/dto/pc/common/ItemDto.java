package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.chinahrd.entity.dto.pc.common.ItemDto;

/**
 * Created by wqcai on 15/6/25.
 */
public class ItemDto implements Serializable {
	private static final long serialVersionUID = -8360122743537041476L;
	private String id;
    private String name;
    private String bindData;
    private String bindObj;
    private List<ItemDto> childs;

    public ItemDto() {
    }

    public ItemDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemDto(String id, String name, List<ItemDto> childs) {
		super();
		this.id = id;
		this.name = name;
		this.childs = childs;
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

    public List<ItemDto> getChilds() {
        return childs;
    }

    public void setChilds(List<ItemDto> childs) {
        this.childs = childs;
    }

    public void addChild(ItemDto dto) {
        if (this.childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(dto);
    }


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBindData() {
		return bindData;
	}

	public void setBindData(String bindData) {
		this.bindData = bindData;
	}

	public String getBindObj() {
		return bindObj;
	}

	public void setBindObj(String bindObj) {
		this.bindObj = bindObj;
	}

    public String getText(){
        return name;
    }
    
}
