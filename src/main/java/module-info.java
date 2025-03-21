module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires spring.security.crypto;

    opens org.example to javafx.fxml;
    exports org.example;
    exports controllers;
    opens controllers to javafx.fxml;
    exports config;
    opens config to javafx.fxml;
    exports dao.custom;
    opens dao.custom to javafx.fxml;
    exports bo.custom;
    opens bo.custom to javafx.fxml;
    exports entity;
    opens entity to org.hibernate.orm.core;
    exports dto;
    opens dto to org.hibernate.orm.core;
}