package com.mygdx.game.domain;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;

public class GameConstants {
    public static final String TITLE = "Wizards vs Lemons";
    public static final Integer WIDTH = 1280;
    public static final Integer HEIGHT = 720;
    public static final int REFRESH_RATE = 60;
    public static final int BITS_PER_PIXEL = 1;

    //STANDARD COLORS
    public static final Color GUI_DISABLED_ACTION_COLOR = Color.GRAY;
    public static final Color GUI_DISABLED_DISPLAY_COLOR = Color.GRAY;
    public static final Color GUI_ACTION_COLOR = Color.CYAN;
    public static final Color GUI_MONEY_COLOR = Color.GREEN;
    public static final Color GUI_DISPLAY_COLOR = Color.WHITE;
    public static final Color GUI_DISPLAY_HEADER_COLOR = Color.RED;

    //WIZARD COLORS
    public static final Color GUI_WIZARD_LIGHTNING_COLOR = Color.PURPLE;
    public static final Color GUI_WIZARD_FIRE_COLOR = Color.RED;
    public static final Color GUI_WIZARD_ICE_COLOR = Color.BLUE;

    //MAIN MENU
    public static final Color GUI_MAIN_MENU_ACTION_COLOR = Color.BLUE;
    public static final Color GUI_MAIN_MENU_DISABLED_ACTION_COLOR = Color.GRAY;

    //ACHIEVEMENT
    public static final Color GUI_ACHIEVEMENT_HEADER_COLOR = Color.GREEN;
    public static final Color GUI_ACHIEVEMENT_DESCRIPTION_COLOR = Color.WHITE;

    //UPGRADE SCREEN
    public static final Color GUI_UPGRADE_HEADER_2_COLOR = Color.WHITE;

    //RESTART SCREEN
    public static final Color GUI_DISPLAY_RESTART_COLOR = Color.GREEN;

    //CREDITS
    public static final Color GUI_CREDITS_TYPED_COLOR = Color.LIGHT_GRAY;

    //CONSUMABLE COST
    public static final BigInteger LEMON_SQUARES_COST = new BigInteger("100");
    public static final BigInteger ICE_LEMON_TEA_COST = new BigInteger("500");
    public static final BigInteger LEMON_ICE_CREAM_COST = new BigInteger("1000");
    public static final BigInteger LEMON_CUSTARD_PIE_COST = new BigInteger("5000");

    //UPGRADEABLE COST
    public static final BigInteger WIZARD_SPELL_COST = new BigInteger("1000");
    public static final BigInteger WIZARD_MAX_LIFE_COST = new BigInteger("1000");
    public static final BigInteger CONSUMABLE_HEALTH_COST = new BigInteger("1000");
    public static final BigInteger CONSUMABLE_COOLDOWN_COST = new BigInteger("1000");

    //UNLOCKABLE COST
    public static final BigInteger WIZARD_COST = new BigInteger("50000");
    public static final BigInteger GOD_MODE_COST = new BigInteger("1000000");

    //LEMON
    public static final BigInteger LEMON_KILLING_POINTS = new BigInteger("500");
    public static final BigInteger LEMON_BASE_DAMAGE = new BigInteger("1");

    //WIZARD
    public static final float WIZARD_INITIAL_MAX_LIFE = 100;

    //AUTO CLICK
    public static final BigInteger AUTO_CLICK_COST = new BigInteger("10000");
}
