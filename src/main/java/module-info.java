module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires io.github.cdimascio.dotenv.java;

    opens org.example.demo.controller to javafx.fxml;


    opens org.example.demo.entity to javafx.base;
    opens org.example.demo.dto to javafx.base;


    exports org.example.demo;
    exports org.example.demo.controller;
    exports org.example.demo.entity;
    exports org.example.demo.dao;
    exports org.example.demo.dto;
}
