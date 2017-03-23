package state;

import java.io.Serializable;

import vue.Fenetre;

public class NormalState extends State implements Serializable{
	
	public NormalState(Fenetre f){
		super(f);
	}
}
