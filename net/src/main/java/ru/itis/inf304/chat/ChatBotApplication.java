package ru.itis.inf304.chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.itis.inf304.ExchangeRateCommand;
import ru.itis.inf304.UserConfig;
import ru.itis.inf304.view.BaseView;
import ru.itis.inf304.view.ChatView;
import ru.itis.inf304.WeatherCommand;


public class ChatBotApplication extends Application {

    private UserConfig userConfig;
    public ChatView chatView;
    private BorderPane root;
    private String username;

    private String apiKeyW = "bd5e378503939ddaee76f12ad7a97608";
    private String apiKeyE = "3aa3ba732336407aa939b167dc42db70";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Bot");
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        userConfig = new UserConfig(this);
        chatView = new ChatView(this);

        root = new BorderPane();
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        setView(userConfig);
    }

    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }

    public void appendMessage(String message) {
        chatView.appendMessage(message);
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void processCommand(String command) {
        String response;
        switch (command.toLowerCase()) {
            case "list":
                response = "Available commands: list, weather <city>, exchange <currency>, quit";
                break;
            case "quit":
                response = "Goodbye!";
                setView(userConfig);
                break;
            default:
                if (command.startsWith("weather")) {
                    response = new WeatherCommand(apiKeyW).execute(command);
                } else if (command.startsWith("exchange")) {
                    response = new ExchangeRateCommand(apiKeyE).execute(command);
                } else {
                    response = "Unknown command. Type 'list' for available commands.";
                }
                break;
        }
        appendMessage(response);
    }
}
