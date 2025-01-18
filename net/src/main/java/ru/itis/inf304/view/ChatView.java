package ru.itis.inf304.view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ru.itis.inf304.chat.ChatBotApplication;

public class ChatView extends BaseView {
    private AnchorPane pane;
    private TextArea conversation;
    private TextArea input;

    public ChatView(ChatBotApplication application) {
        super(application);
    }

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    public void appendMessage(String message) {
        if (message != null) {
            conversation.appendText(message + "\n");
        }
    }

    private void createView() {
        pane = new AnchorPane();
        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);
        AnchorPane.setLeftAnchor(conversation, 10.0);
        AnchorPane.setRightAnchor(conversation, 10.0);
        AnchorPane.setTopAnchor(conversation, 10.0);

        input = new TextArea();
        input.setMaxHeight(50);
        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(input, 10.0);
        AnchorPane.setBottomAnchor(input, 10.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler<KeyEvent>) keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String message = input.getText().trim();
                application.processCommand(message);
                input.clear();
                keyEvent.consume();
            }
        });

        pane.getChildren().addAll(conversation, input);
    }
}
