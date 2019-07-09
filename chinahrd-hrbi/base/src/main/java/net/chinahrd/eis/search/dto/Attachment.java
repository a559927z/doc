package net.chinahrd.eis.search.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "files", type = "info")
public class Attachment {

	@Id
	private String id;
	@Field(type = FieldType.String, index=FieldIndex.analyzed, store = true)
	private String fileName;
	@Field(type = FieldType.String, index=FieldIndex.no, store = true)
	private String filePath;
	@Field(type = FieldType.String, analyzer="ik_max_word", searchAnalyzer="ik_max_word", store = false)
	private String fileContent;
	//@Field(type = FieldType.String, index=FieldIndex.no, store = true)
	//private String fragment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	/*public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}*/

}