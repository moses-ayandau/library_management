module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens org.example.demo.views to javafx.fxml;

    opens org.example.demo.entity to javafx.base;

    exports org.example.demo;
    exports org.example.demo.views;
    exports org.example.demo.entity;
    exports org.example.demo.dao;
}
