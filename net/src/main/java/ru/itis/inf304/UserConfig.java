package ru.itis.inf304;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.itis.inf304.chat.ChatBotApplication;
import ru.itis.inf304.view.BaseView;

public class UserConfig extends BaseView {
    private AnchorPane pane;
    private VBox box;
    private TextField username;

    public UserConfig(ChatBotApplication application) {
        super(application);
    }

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    private void createView() {
        pane = new AnchorPane();
        box = new VBox(10);
        Label usernameLabel = new Label("Username:");
        username = new TextField();

        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {

            application.setUsername(username.getText());
            application.setView(application.chatView);
        });

        box.getChildren().addAll(usernameLabel, username, start);
        pane.getChildren().addAll(box);
    }
}
