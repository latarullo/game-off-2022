package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.Item;
import com.mygdx.game.domain.UnlockableItem;
import com.mygdx.game.screen.gui.LemonMoneyFieldGUI;

import java.math.BigInteger;

public class HoverFactory {
    private static HoverFactory instance;

    public static HoverFactory getInstance() {
        if (instance == null) {
            instance = new HoverFactory();
        }

        return instance;
    }

    private static GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    private HoverFactory() {
    }

    public static Hover create(Item item, ImageButton imageButton) {
        if (item instanceof ConsumableItem) {
            ConsumableItem healthConsumableItem = (ConsumableItem) item;
            return createHealthConsumableHover(healthConsumableItem, imageButton);
        } else if (item instanceof UnlockableItem) {
            UnlockableItem unlockableItem = (UnlockableItem) item;
            return createHealthUnlockableHover(unlockableItem, imageButton);
        }

        return null;
    }

    private static Hover createHealthConsumableHover(final ConsumableItem healthConsumableItem, ImageButton imageButton) {
        Hover hover = new Hover();

        Label.LabelStyle labelStyleGreen = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.BLACK);
        Label nameLabel = new Label(healthConsumableItem.getName(), labelStyleGreen);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.DARK_GRAY);
        Label healthPowerLabel = new Label("Health Power", labelStyle);
        Label coolDownLabel = new Label("Cooldown", labelStyle);
        Label priceLabel = new Label("Price", labelStyle);
        Label ownedQuantityLabel = new Label("Owned", labelStyle);

        Label.LabelStyle labelStyleWhite = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);
        Label healthPowerValueLabel = new Label(String.valueOf(healthConsumableItem.getHealthPower()), labelStyleWhite);
        Label coolDownValueLabel = new Label(String.valueOf(healthConsumableItem.getCooldown()) + "s", labelStyleWhite);
        Label priceValueLabel = new Label(String.valueOf(healthConsumableItem.getPrice()) + "$", labelStyleWhite);
        Label ownedQuantityValueLabel = new Label(String.valueOf(healthConsumableItem.getPrice()) + "$", labelStyleWhite);

        ownedQuantityValueLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                Integer ownedQuantity = GameOff2022.getInstance().getGameData().getConsumables().get(healthConsumableItem.getType());
                label.setText(ownedQuantity.toString());
                return false;
            }
        });

        hover.add(nameLabel).fillX().colspan(2).padBottom(10).width(300).align(Align.center);
        hover.row();
        hover.add(healthPowerLabel).colspan(1).align(Align.left);
        hover.add(healthPowerValueLabel).colspan(1).align(Align.right);
        hover.row();
        hover.add(coolDownLabel).colspan(1).align(Align.left);
        hover.add(coolDownValueLabel).colspan(1).align(Align.right);
        hover.row();
        hover.add(priceLabel).colspan(1).align(Align.left);
        hover.add(priceValueLabel).colspan(1).align(Align.right);
        hover.row();
        hover.add(ownedQuantityLabel).colspan(1).align(Align.left);
        hover.add(ownedQuantityValueLabel).colspan(1).align(Align.right);

        hover.setSize(400, 200);

        float parentX = imageButton.getX();
        float parentY = imageButton.getY();
        float parentWidth = imageButton.getWidth();
        //float parentHeight = imageButton.getHeight();

        float positionX;

        if (parentX > GameConstants.WIDTH / 2) {
            positionX = parentX - 400;
        } else {
            positionX = parentX + parentWidth;
        }

        hover.setPosition(positionX, parentY);

        return hover;
    }

    private static Hover createHealthUnlockableHover(final UnlockableItem unlockableItem, ImageButton imageButton) {
        Hover hover = new Hover();

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.YELLOW);
        Label nameLabel = new Label(unlockableItem.getName(), labelStyle);
        Label priceLabel = new Label("Price", labelStyle);
        Label unlockedLabel = new Label("Unlocked", labelStyle);

        Label.LabelStyle labelStyleWhite = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);

        //String printableValue = BigNumberNotation.getPrintableValue(, false);
       // Label priceValueLabel = new Label(printableValue + "$", labelStyleWhite);

        LemonMoneyFieldGUI lemonMoneyFieldGUI = new LemonMoneyFieldGUI();
        Table priceValueLabel = lemonMoneyFieldGUI.createTable(unlockableItem.getPrice());

        Label unlockedValueLabel = new Label(String.valueOf(unlockableItem.isUnlocked()), labelStyleWhite);

//        ownedQuantityValueLabel.addAction(new Action() {
//            @Override
//            public boolean act(float delta) {
//                Label label = (Label) this.getActor();
//                Integer ownedQuantity = GameOff2022.getInstance().getGameData().getConsumables().get(unlockableItem.getType());
//                label.setText(ownedQuantity.toString());
//                return false;
//            }
//        });

        hover.add(nameLabel).colspan(3).padBottom(10);
        hover.row();
        hover.add(priceLabel).colspan(1).align(Align.left);
        hover.add(priceValueLabel).colspan(1).align(Align.right);
        hover.row();
        hover.add(unlockedLabel).colspan(1).align(Align.left);
        hover.add(unlockedValueLabel).colspan(1).align(Align.right);

        hover.setSize(400, 200);

        float parentX = imageButton.getX();
        float parentY = imageButton.getY();
        float parentWidth = imageButton.getWidth();
        float parentHeight = imageButton.getHeight();

        float positionX, positionY;

        if (parentX > GameConstants.WIDTH / 2) {
            positionX = parentX - 400;
        } else {
            positionX = parentX + parentWidth;
        }

        if (parentY > GameConstants.HEIGHT / 2) {
            positionY = parentY + 1 - 1;
        } else {
            positionY = parentY - 1 + 1;
        }

        hover.setPosition(positionX, positionY);

        return hover;
    }
}
