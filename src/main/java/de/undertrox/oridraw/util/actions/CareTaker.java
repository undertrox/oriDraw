package de.undertrox.oridraw.util.actions;

/*
    Caretaker is where the arraylist of the states of the momentos that was saved.
    It is also where we can retrieve and store states that was saved previously.
    Also just like the mementos, I think it is better to let each tab document possess its own caretakers.
 */

import java.util.ArrayList;

public class CareTaker {

    private ArrayList<Mementos> savedDocument = new ArrayList<>();

    private Mementos memento;

    public CareTaker(){}
    public void addMementos(Mementos memento){
        savedDocument.add(memento);
    }
    public ArrayList<Mementos> getSavedDocument(){
        return this.savedDocument;
    }

    public Mementos getMemento(int currIndex){
        return savedDocument.get(currIndex);
    }
}
