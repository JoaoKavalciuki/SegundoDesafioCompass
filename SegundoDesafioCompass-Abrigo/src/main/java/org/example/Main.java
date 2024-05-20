package org.example;

import org.example.service.AbrigoService;
import org.example.ui.AbrigoMenuHandler;

public class Main {
    public static void main(String[] args) {
        AbrigoService abrigoService = new AbrigoService();
        AbrigoMenuHandler abrigoMenuHandler = new AbrigoMenuHandler(abrigoService);
        abrigoMenuHandler.displayMenu();
        abrigoService.close();
    }
}
