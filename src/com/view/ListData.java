package com.view;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class ListData implements Serializable {
	
	public static final String List_Name = "list";

	// id is generated by the database and set on the object automatically
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField(index = true)
	String string;
	@DatabaseField(columnName = List_Name)
	String mainList;
	@DatabaseField
	private int value;
	

	ListData() {
		// needed by ormlite
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("string=").append(string);
		sb.append(", ").append(mainList);
		return sb.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	
	public void changeValue(int value) {
		this.value = value;
		//this.mainList = new ();
	}
	
	
	public String getName() {
		return mainList;
	}

	public void setName(String mainList) {
		this.mainList = mainList;
	}

}