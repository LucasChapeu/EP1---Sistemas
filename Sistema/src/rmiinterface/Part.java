package rmiinterface;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Part implements Serializable{

	private static final long serialVersionUID = 5389293905186800462L;
	private String name;
	private String desc;
	private String id;
	private String type;
	
	//Criar uma forma de utilizar as duas listas ao mesmo tempo (par subpeça e quantidade)
	private List<Part> subPart;
	private List<Integer> subPartQnt;
	
	public Part(String id) {
		this.id = id;
	}	
	
	public Part(String name, String desc, String id, String type) {
		this.name = name;
		this.desc = desc;
		this.id = UUID.randomUUID().toString();
		this.type = type;
		
		if (type == "Composta") {
			this.subPart = new ArrayList<>();
			this.subPartQnt = new ArrayList<>();
		}
		/*else {
			this.subPart = null;
			this.subPartQnt = null;
		}*/
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
	
	public List<Part> getSubList() {
		return this.subPart;
	}
	
	public List<Integer> getSubListQnt() {
		return this.subPartQnt;
	}
	
	public String toString() {
        return this.id + " - " + this.name + " (" + this.type + ")";
    }
	
}
