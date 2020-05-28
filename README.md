# OriDraw

A Crease Pattern Drawing software, currently still in development.

```
Some of the goals in mind includes:
    - Customizable setings
    - User Friendly
    - Tabs to work with multiple documents in one program
```

---

### Environment Setup

This program is implemented with JavaFx. It is pretty easy to set up the development environment.
For this section, IntelliJ was used to as the environment. First open a new project under Version Control in the file menu.

If `git.exe` is not found then make sure to go to settings and specify the git.exe binary file path before trying again.

Make sure to import the `gradle` modules as well so that dependencies for JavaFx and some other utilities would be imported nicely like loggers and more.
Next, after all has been setup, go to configuration. We are currently using Java 11 and will be running this as an `application`.

If successful, then most likely, you should be able to run the project.

---

### Codebase

There are currently two branches. 
1.  `master`
2. `test-branch`
    
`master` is where confirmed and tested features resides.
`test-branch` contains unconfirmed code and probably still relatively buggy code.



Under oridraw, there are a few packages. 
-   `Origami`
    -   contains origami related objects.
        -   `OriPoint` is basically a vector on steroids.
        -  `OriLine` is an object connected through two `OriPoint` s.
        -   `OriLineCollection` is a base class for Grid, Crease pattern and possibly more
            -   It stores the information about the lines and points collectively.
        -   `CreasePattern` and `Grid` contains information about itself which is quite self explanatory.
        -   `Document` is an object that contains everything in a creasepatterntab.
            - Contains information like grid size, document title, crease pattern and more.
        - `FoldedModel` is currently not being implemented but should be at the end after the essentials are completed.
-   `ui`
    -   literally contains information about the user interface of Oridraw. It contains things like keypresses, mouse clicks, specific renderers, tabs of the UI and more.
    - It also stores information about the theme of the UI as well. In i tthe MainWindowController contains logic of button clicks and other main logic to interact with the UI.
    
-   `util`
    - This package contains things like actions (redo and undo at the moment). It uses the memento design pattern to store and retrieve the state of the document.
    - `io` contains the logic on exporting and importing of cp files.
    - `math` contains the-you-guess-it-mathematical aspect of origami that would be used to do calculations like lines and transformation.
    - `Registry` contains things like IDs that are assigned which are necessary for the storing of data.
-   `resources`
    -   Contains information about the UI via FXML. We can use scenebuilder to open the `mainWindow.fxml` file.
        -   We can open this either internally (some magical workaround that is not stated here) or extenally.
            - Just saving the file should update the file and we can go to the fxml file to import classes wherever appropriate and create the necessary functions that were added via the fxml file.

---

