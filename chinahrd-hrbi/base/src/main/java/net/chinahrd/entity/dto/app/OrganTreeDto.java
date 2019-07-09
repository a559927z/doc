package net.chinahrd.entity.dto.app;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.utils.CollectionKit;

public class OrganTreeDto implements Serializable {

	private static final long serialVersionUID = -7715350923278809559L;

	private String organizationId;
	private String organizationKey;
	private String organizationParentId;
	private String organizationName;
	private int hasChildren;
	private boolean queryChildren=true;  //有子节点时  默认点击查询子节点
	List<OrganTreeDto> childrens=CollectionKit.newList();
	/**
	 * @param dto
	 */
	public OrganTreeDto(OrganDto dto) {
		this.setOrganizationId(dto.getOrganizationId());
		this.setOrganizationKey(dto.getOrganizationKey());
		this.setOrganizationParentId(dto.getOrganizationParentId());
		this.setOrganizationName(dto.getOrganizationName());
		this.setHasChildren(dto.getHasChildren());
	}

	public List<OrganTreeDto> getChildrens() {
		return childrens;
	}

	public void setChildrens(OrganDto dto) {
		OrganTreeDto children=new OrganTreeDto(dto);
		this.childrens.add(children) ;
	}
	public void setChildrens(OrganTreeDto organTreeDto) {
		organTreeDto.setQueryChildren(false);
		this.childrens.add(organTreeDto) ;
	}
	private String organizationParentName;
	public boolean getQueryChildren() {
		return queryChildren;
	}

	public void setQueryChildren(boolean queryChildren) {
		this.queryChildren = queryChildren;
	}

	
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	public String getOrganizationKey() {
		return organizationKey;
	}

	public void setOrganizationKey(String organizationKey) {
		this.organizationKey = organizationKey;
	}

	public String getOrganizationParentId() {
		return organizationParentId;
	}

	public void setOrganizationParentId(String organizationParentId) {
		this.organizationParentId = organizationParentId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getOrganizationParentName() {
		return organizationParentName;
	}

	public void setOrganizationParentName(String organizationParentName) {
		this.organizationParentName = organizationParentName;
	}

}
