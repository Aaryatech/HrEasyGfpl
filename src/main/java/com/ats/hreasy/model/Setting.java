package com.ats.hreasy.model;

public class Setting {
	
	private int settingId;

	private String group;

	private String key;

	private String value;

	private int serialized;

	private int editable;

	private String labels;
	
	
	private String defaultValue;
	
	
	private String explaination;
	
	
	private int exInt1;	
	
	private String exVar1;	
	
	public int getSettingId() {
		return settingId;
	}


	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public int getSerialized() {
		return serialized;
	}


	public void setSerialized(int serialized) {
		this.serialized = serialized;
	}


	public int getEditable() {
		return editable;
	}


	public void setEditable(int editable) {
		this.editable = editable;
	}


	public String getLabels() {
		return labels;
	}


	public void setLabels(String labels) {
		this.labels = labels;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	public String getExplaination() {
		return explaination;
	}


	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}


	public int getExInt1() {
		return exInt1;
	}


	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}


	public String getExVar1() {
		return exVar1;
	}


	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}


	@Override
	public String toString() {
		return "Setting [settingId=" + settingId + ", group=" + group + ", key=" + key + ", value=" + value
				+ ", serialized=" + serialized + ", editable=" + editable + ", labels=" + labels + ", defaultValue="
				+ defaultValue + ", explaination=" + explaination + ", exInt1=" + exInt1 + ", exVar1=" + exVar1 + "]";
	}

}
