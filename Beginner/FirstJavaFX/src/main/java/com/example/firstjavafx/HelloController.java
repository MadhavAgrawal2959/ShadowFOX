package com.example.firstjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private TextField namein;

    @FXML
    private Text nameout;

    @FXML
    void namechange(MouseEvent event) {
        nameout.setText(namein.getText());

    }

}
