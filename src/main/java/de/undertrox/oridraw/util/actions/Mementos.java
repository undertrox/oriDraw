package de.undertrox.oridraw.util.actions;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Mementos is the object whose state would be stored.
    In this case, the crease pattern is the one that we want to save.
    Ideally, I think it should be better that each tab document should get its own Mementos.
*/

public class Mementos {
    private Logger logger = LogManager.getLogger(Mementos.class);
    private Document document = null;

    // Construct a new Mementos to store crease patterns
    public Mementos(Document document){

        this.document = new Document(document);
        logger.debug("Creating new document... Hash : " + document.hashCode());
    };

    public Document getDocument(){
        return this.document;
    }
}
