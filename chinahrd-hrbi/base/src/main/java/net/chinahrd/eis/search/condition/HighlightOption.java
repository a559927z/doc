package net.chinahrd.eis.search.condition;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.utils.CollectionKit;

public class HighlightOption implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5653038650415952005L;
	private List<String> fields = CollectionKit.newList();
	
	private final static String[] DEFAULT_PRE_TAGS = new String[] { "<span style=\"color:red\">" };
	private final static String[] DEFAULT_POST_TAGS = new String[] { "</span>" };

	private String[] preTags = DEFAULT_PRE_TAGS;
	private String[] postTags = DEFAULT_POST_TAGS;

	public static HighlightOption of() {
		return new HighlightOption();
	}

	private HighlightOption() {
	}

	public String[] getPreTags() {
		return preTags;
	}

	public HighlightOption setPreTags(String[] preTags) {
		this.preTags = preTags;
		return this;
	}

	public String[] getPostTags() {
		return postTags;
	}

	public HighlightOption setPostTags(String[] postTags) {
		this.postTags = postTags;
		return this;
	}
	
	public List<String> getFields() {
		return fields;
	}

	public HighlightOption addField(String field) {
		this.fields.add(field);
		return this;
	}
	
	public HighlightOption addFields(String... fields) {
		java.util.Collections.addAll(this.fields, fields);
		return this;
	}
}