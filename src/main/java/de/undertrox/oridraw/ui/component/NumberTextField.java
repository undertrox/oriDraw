package de.undertrox.oridraw.ui.component;

import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

public class NumberTextField extends TextField {

    public NumberTextField() {
        this("");
    }

    public NumberTextField(String text) {
        super(text);
        this.setOnScroll(this::onScroll);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    public void onScroll(ScrollEvent e) {
        this.replaceText(0, getText().length(), Integer.toString(Integer.parseInt(getText()) + (int) Math.signum(e.getDeltaY())));
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }
}