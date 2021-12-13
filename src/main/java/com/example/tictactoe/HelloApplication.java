package com.example.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Boolean turnCross = true;

    Label log = new Label("x");

    int[][] winMatrix = new int[3][3];

    private Button createButton(GridPane gridPane, int i, int j) {
        Button btn = new Button("");
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (turnCross) {
                    btn.setText("x");
                    log.setText("0");
                    btn.setDisable(true);
                    winMatrix[i][j] = 1;
                    if (isVictory(gridPane, i, j)) {
                        log.setText("x Win!");
                    }

                } else {
                    btn.setText("0");
                    log.setText("x");
                    btn.setDisable(true);
                    winMatrix[i][j] = 2;
                    if (isVictory(gridPane, i, j)) {
                        log.setText("0 Win!");
                    }
                }
                turnCross = !turnCross;
            }
        });

        return btn;
    }

    private Boolean isVictory(GridPane gridPane, int i, int j) {


        if (winMatrix[i][0] == winMatrix[i][1] && winMatrix[i][0] == winMatrix[i][2]) {
            disableButtons(gridPane);
            return true;
        }

        if (winMatrix[0][j] == winMatrix[1][j] && winMatrix[0][j] == winMatrix[2][j]) {
            disableButtons(gridPane);
            return true;
        }

        if (winMatrix[0][0] != 0 && winMatrix[1][1] != 0 && winMatrix[2][2] != 0 && winMatrix[0][0] == winMatrix[1][1] && winMatrix[0][0] == winMatrix[2][2]) {
            disableButtons(gridPane);
            return true;
        }

        if (winMatrix[0][2] != 0 && winMatrix[1][1] != 0 && winMatrix[2][0] != 0 && winMatrix[0][2] == winMatrix[1][1] && winMatrix[0][2] == winMatrix[2][0]) {
            disableButtons(gridPane);
            return true;
        }


        return false;
    }

    private void disableButtons(GridPane gridPane) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                for (Node node : gridPane.getChildren()) {
                    if (GridPane.getColumnIndex(node) == k && GridPane.getRowIndex(node) == l) {
                        node.setDisable(true);
                    }
                }
            }
        }
    }


    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                root.add(createButton(root, i, j), i, j);
                winMatrix[i][j] = 0;
            }
        }

        log.setPrefWidth(100);
        log.setPrefHeight(100);

        Button start = new Button("ReStart");
        start.setPrefWidth(100);
        start.setPrefHeight(100);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int finalI = i;
                        int finalJ = j;
                        root.getChildren().removeIf(node -> GridPane.getRowIndex(node) == finalJ && GridPane.getColumnIndex(node) == finalI);
                        root.add(createButton(root, i, j), i, j);
                        winMatrix[i][j] = 0;
                    }
                }
                turnCross = true;
                log.setText("x");
            }
        });

        ;

        root.add(new Label("Ход:"), 0, 3);
        root.add(log, 1, 3);
        root.add(start, 3, 3);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);

        stage.setTitle("TicTacToe");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}