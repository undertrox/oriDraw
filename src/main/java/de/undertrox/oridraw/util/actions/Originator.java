package de.undertrox.oridraw.util.actions;

/*
    Originator is the  class that sets and gets value from the currently targeted memento.
    It is also responsible the creation of new mementos and wjat assigns the current values to
    them.
 */

import de.undertrox.oridraw.origami.Document;

public class Originator {

    //CreasePattern creasePattern;
    Document document;
    public Originator(){}

    public void set(Document document){
        this.document = document;
    }

    public Mementos createMementos(){
        return new Mementos(this.document);
    }

    public Document restoreMemento(Mementos memento){
        return memento.getDocument();
    }

}
