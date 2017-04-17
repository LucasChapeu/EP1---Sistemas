package rmiinterface;

import java.util.UUID;

import java.io.Serializable;

public class Part implements Serializable{

	private static final long serialVersionUID = 5389293905186800462L;
	private String name;
	private String desc;
	private String id;
	private String type;
	
	
	public Part(String id) {
		this.id = id;
	}
	
	
	public Part(String name, String desc, String id, String type) {
		this.name = name;
		this.desc = desc;
		this.id = UUID.randomUUID().toString();
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
        return this.id + " - " + this.name + " (" + this.type + ")";
    }
	
}
