package rmiinterface;

import java.io.Serializable;
//import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part implements Serializable{

	private static final long serialVersionUID = 5389293905186800462L;
	private String name;
	private String desc;
	private String id;
	private String type;
			
	Map<Part, Integer> subPart;
	
	public Part(String id) {
		this.id = id;
	}	
	
	public Part(String name, String desc, String id, String type) {
		this.name = name;
		this.desc = desc;
		//this.id = UUID.randomUUID().toString();
		this.id = id;
		this.type = type;	
		if(type == ""){
			 this.subPart = new HashMap();
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public boolean setName(String name) {
		this.name = name;
		return true;
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
	
	public boolean addSubPart(Part part, Integer qnt) {
		System.out.println("3");
		this.subPart.put(part,qnt);
		System.out.println("4");
		return true;
	}
		
	public Map<Part, Integer> getSubList() {
		return this.subPart;
	}
			
	public String toString() {
        return this.id + " - " + this.name + " (" + this.type + ")";
    }
	
}
