package rmiinterface;

import java.io.Serializable;
//import java.util.UUID;
import java.util.HashMap;

public class Part implements Serializable{
	
	private static final long serialVersionUID = 5389293905186800462L;
	
	private String name;					//Nome da peça criada
	private String desc;					//Descrição da peça
	private String id;						//ID única e gerada automaticamente
	private String type;					//Tipo da peça: composta ou primitiva
	private HashMap<Part, Integer> subPart; //Lista de Sub-peças
	
	public Part(String id) {
		this.id = id;
	}	
		
	public Part(String name, String desc, String id, String type) {
		this.name = name;
		this.desc = desc;
		this.id = id;
		this.type = type;	
		this.subPart = new HashMap<Part, Integer>();		
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
	
	public HashMap<Part, Integer> getSubList() {
		return this.subPart;
	}
	
	//Gera a lista de sub-peças a partir da lista local do cliente
	public boolean addSubList(HashMap<Part, Integer> subTemp) {		
		this.subPart = subTemp;
		return true;
	}			
			
	public String toString() {
        return this.id + " - " + this.name + " (" + this.type + ")";
    }
	
}
