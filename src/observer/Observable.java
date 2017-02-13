package observer;

import java.util.ArrayList;

public abstract class Observable{
	private ArrayList<Observer> observers=new ArrayList<Observer>();
	
	public void addObserver(Observer obs){
		observers.add(obs);
	}

	public void notifyObserver(Object o){
		for(Observer obs : observers)
			obs.update(this, o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	public ArrayList<Observer> getOberservers(){
		return observers;
	}
	
	public void setOberservers(ArrayList<Observer> o){
		observers = o;
	}
}
