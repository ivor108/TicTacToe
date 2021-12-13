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

    //Флаг для определения хода. true - крестики
    Boolean turnCross = true;

    //Лейбл в который будет выводиться информация о ходе и о победе
    Label log = new Label("x");

    //Матрица дублируещее расположение х и 0 на поле. Нужа для опрделения победы
    int[][] winMatrix = new int[3][3];

    //Метод для создания кнопки, передаём сеточную панель и ряд, столбец кнопки
    private Button createButton(GridPane gridPane, int i, int j) {
        //Создаём новую кнопку с пустым текстом
        Button btn = new Button("");
        //Задаём размеры
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        //Прописываем действие при нажатии
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //если ход крестиков
                if (turnCross) {
                    //Меняем текст
                    btn.setText("x");
                    //Меняем текст лейбла показывающий очерёдность хода
                    log.setText("0");
                    //Отключаем нажатую кнопку
                    btn.setDisable(true);
                    //В матрице меняем значение на 1 (0 - ещё не нажата, 1 - х, 2 - 0)
                    winMatrix[i][j] = 1;
                    //Проверяем не закончилась ли игра
                    if (isVictory(gridPane, i, j)) {
                        log.setText("x Win!");
                    }

                    //Если нолик делаем тоже самое только для ноликов
                } else {
                    btn.setText("0");
                    log.setText("x");
                    btn.setDisable(true);
                    // 2 - нолики
                    winMatrix[i][j] = 2;
                    if (isVictory(gridPane, i, j)) {
                        log.setText("0 Win!");
                    }
                }
                //Меняем значение флага на противоположное
                turnCross = !turnCross;
            }
        });

        //Возвращаем кнопку
        return btn;
    }

    //Проверка окончании игры т.е. победы. Передаём сеточную панель и строку, столбец нажатой только что кнопки
    private Boolean isVictory(GridPane gridPane, int i, int j) {

        //Условия победы:
        // если в строке нажатой только что кнопки все значение равны
        if (winMatrix[i][0] == winMatrix[i][1] && winMatrix[i][0] == winMatrix[i][2]) {
            //Отключаем все кнопки
            disableButtons(gridPane);
            return true;
        }

        // если в столбце нажатой только что кнопки все значение равны
        if (winMatrix[0][j] == winMatrix[1][j] && winMatrix[0][j] == winMatrix[2][j]) {
            disableButtons(gridPane);
            return true;
        }

        //Если на главной диагонали все значение не равны 0 и равны между собой
        if (winMatrix[0][0] != 0 && winMatrix[1][1] != 0 && winMatrix[2][2] != 0 && winMatrix[0][0] == winMatrix[1][1] && winMatrix[0][0] == winMatrix[2][2]) {
            disableButtons(gridPane);
            return true;
        }

        //Если на второстепенной диагонали все значение не равны 0 и равны между собой
        if (winMatrix[0][2] != 0 && winMatrix[1][1] != 0 && winMatrix[2][0] != 0 && winMatrix[0][2] == winMatrix[1][1] && winMatrix[0][2] == winMatrix[2][0]) {
            disableButtons(gridPane);
            return true;
        }

        //Если не сработало ни одно из условий
        return false;
    }

    //Метод для отключения кнопок, передаём сеточную панель
    private void disableButtons(GridPane gridPane) {
        //Проходим двойным циклом по всем кнопкам
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                //Берём у панели всех детей и проходим по ним циклом
                for (Node node : gridPane.getChildren()) {
                    //Без полезный иф, можешь его удалить. Оставил что бы не менять код.
                    if (GridPane.getColumnIndex(node) == k && GridPane.getRowIndex(node) == l) {
                        //Отключает ребёнка
                        node.setDisable(true);
                    }
                }
            }
        }
    }


    //Главный метод проги
    @Override
    public void start(Stage stage) {

        //Создаём сеточную панель
        GridPane root = new GridPane();
        //Проходим двойным циклом
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //Вызываем метод создания копки и кладём получивщуеся кнопку в сетку по координатам i j
                root.add(createButton(root, i, j), i, j);
                //Заполняем матрицу 0
                winMatrix[i][j] = 0;
            }
        }

        //Задаём размеры лейбла log который создали вначале
        log.setPrefWidth(100);
        log.setPrefHeight(100);

        //Создаём кнопку для обновления игры
        Button start = new Button("ReStart");
        start.setPrefWidth(100);
        start.setPrefHeight(100);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Проходим двойным циклом по всем кнопкам
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        //Создаём, зачем то, новые переменные в который кладём i и j) Так сказала делать idea
                        int finalI = i;
                        int finalJ = j;
                        //Удаляем все кнопки
                        //Берём у сетки (которая называется root) всех детей и удаляем их если они проходя по условию.
                        // А условие это то что координаты ребёнка i и j
                        root.getChildren().removeIf(node -> GridPane.getRowIndex(node) == finalJ && GridPane.getColumnIndex(node) == finalI);
                        //Создаём кнопки заново
                        root.add(createButton(root, i, j), i, j);
                        //Заполняем матрицу 0 заново
                        winMatrix[i][j] = 0;
                    }
                }
                //Сдрасываем флаг ходов на х
                turnCross = true;
                log.setText("x");
            }
        });

        //Лейьл с текстом Ход:
        root.add(new Label("Ход:"), 0, 3);
        root.add(log, 1, 3);
        root.add(start, 3, 3);

        //Стандартные строчки для проги javafx. Создаём сцену, передём её на stage
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);

        //Заголовок
        stage.setTitle("TicTacToe");

        //Gjrfpfnm cwtye
        stage.show();
    }

    //Запускаем игру
    public static void main(String[] args) {
        launch();
    }
}