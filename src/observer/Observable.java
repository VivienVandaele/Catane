package observer;

import java.util.ArrayList;

public abstract class Observable{
	private ArrayList<Observer> observers=new ArrayList<Observer>();
	
	public void addObserver(Observer obs){
		observers.add(obs);
		notifyObserver();
	}

	public void notifyObserver(){
		for(Observer obs : observers)
			obs.update(this);
	}
	
	public void notifyObserver(Object o){
		for(Observer obs : observers)
			obs.update(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}
}
