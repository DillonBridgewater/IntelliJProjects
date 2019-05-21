package com_muc;

import javax.swing.*;
import java.awt.*;

public class MessagePane extends JPanel {
    private final ChatClient client;
    private final String login;

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();
    public MessagePane(ChatClient client, String login) {
        this.client = client;
        this.login = login;

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
    }

}
