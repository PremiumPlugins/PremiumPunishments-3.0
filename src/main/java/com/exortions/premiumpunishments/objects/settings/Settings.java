package com.exortions.premiumpunishments.objects.settings;

import java.util.List;

public class Settings {

    public static boolean BAN_IP_ADDRESSES = false;

    public static boolean FREEZE_DISABLE_MOVEMENT = false;
    public static boolean FREEZE_DISABLE_INTERACTIONS = false;
    public static boolean FREEZE_DISABLE_CHATTING = false;
    public static List<String> FREEZE_DISABLED_COMMANDS = null;

    public static boolean FREEZE_SPAM_MESSAGE = false;
    public static int FREEZE_SPAM_MESSAGE_DELAY = -1;

    public Settings(boolean BAN_IP_ADDRESSES, boolean FREEZE_DISABLE_MOVEMENT, boolean FREEZE_DISABLE_INTERACTIONS, boolean FREEZE_DISABLE_CHATTING, List<String> FREEZE_DISABLED_COMMANDS, boolean FREEZE_SPAM_MESSAGE, int FREEZE_SPAM_MESSAGE_DELAY) {
        Settings.BAN_IP_ADDRESSES = BAN_IP_ADDRESSES;
        Settings.FREEZE_DISABLE_MOVEMENT = FREEZE_DISABLE_MOVEMENT;
        Settings.FREEZE_DISABLE_INTERACTIONS = FREEZE_DISABLE_INTERACTIONS;
        Settings.FREEZE_DISABLE_CHATTING = FREEZE_DISABLE_CHATTING;
        Settings.FREEZE_DISABLED_COMMANDS = FREEZE_DISABLED_COMMANDS;
        Settings.FREEZE_SPAM_MESSAGE = FREEZE_SPAM_MESSAGE;
        Settings.FREEZE_SPAM_MESSAGE_DELAY = FREEZE_SPAM_MESSAGE_DELAY;
    }
}
