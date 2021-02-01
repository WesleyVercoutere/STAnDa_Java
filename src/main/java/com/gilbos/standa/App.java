package com.gilbos.standa;

import com.gilbos.standa.config.AppConfig;
import com.gilbos.standa.frontend.RootLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App extends Application {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        RootLayout rootLayout = applicationContext.getBean(RootLayout.class);
        rootLayout.init(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        ((ConfigurableApplicationContext) applicationContext).close();
    }

}
