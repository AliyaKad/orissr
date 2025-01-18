package ru.itis.inf304.view;

import javafx.scene.Parent;
import ru.itis.inf304.chat.ChatBotApplication;

public abstract class BaseView {
    protected ChatBotApplication application;

    public BaseView(ChatBotApplication application) {
        this.application = application;
    }

    public abstract Parent getView();
}

