package com.mygdx.game.screen.gui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameData;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.domain.GreatHealthPotion;
import com.mygdx.game.domain.GreaterHealthPotion;
import com.mygdx.game.domain.HealthPotionEnum;
import com.mygdx.game.domain.MinorHealthPotion;
import com.mygdx.game.domain.SmallHealthPotion;
import com.mygdx.game.screen.ClickerScreen;
import com.mygdx.game.screen.HealthPotionShopScreen;
import com.mygdx.game.screen.UpgradeScreen;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;

public class ClickerScreenGUI {
    private ClickerScreen clickerScreen;

    public ClickerScreenGUI(ClickerScreen clickerScreen) {
        this.clickerScreen = clickerScreen;
    }

    public Table createGUI() {
        final GameOff2022 game = clickerScreen.getGame();
        final Stage stage = clickerScreen.getStage();

//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = game.getGameFont();

//        TextButton createAutoClicker = new TextButton("Create Auto-Clicker", textButtonStyle);

//        createAutoClicker.setPosition(0, 200);
//        createAutoClicker.setSize(500,50);

//        createAutoClicker.addListener(new ClickListener(){
//            public void clicked (InputEvent event, float x, float y) {
//                autoClickers++;
//                Timer.Task clickerTask = new Timer.Task() {
//                    @Override
//                    public void run() {
//                        Array.ArrayIterator<Actor> iterator = stage.getActors().iterator();
//                        while(iterator.hasNext()){
//                            Actor actor = iterator.next();
//                            if (actor instanceof Wizard){
//                                imageClick(actor);
//                            }
//                        }
//
//                    }
//                };
//                timer.scheduleTask(clickerTask, 1f, 1);
//                TextButton textButton = (TextButton) event.getListenerActor();
//                //textButton.setText(String.format("Create new Auto-Clicker (%s running)", autoClickers));
//                textButton.setText("Create new Auto-Clicker (" + autoClickers + " running)");
//            }
//        });

        Enemy enemy = new Enemy(game);
        enemy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getCurrentWizard().setCurrentState(WizardState.ATTACK);
            }
        });
        enemy.setPosition(500, GameData.HEIGHT/2-150);
        enemy.setSize(50, 50);
        enemy.setCurrentState(EnemyState.ATTACK);
        stage.addActor(enemy);

        Wizard wizard = game.getCurrentWizard();
        wizard.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
//                clickerScreen.imageClick(actor);
            }
        });
        wizard.setPosition(100, GameData.HEIGHT/2-150);
        wizard.setCurrentState(WizardState.IDLE);
        wizard.setUserObject(enemy);
        stage.addActor(wizard);

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(game);
        Table lemonMoneyGUITable = lemonMoneyGUI.createTable();

        Table guiTable = new Table();
        guiTable.setFillParent(true);
//        guiTable.setDebug(true);
        guiTable.left().top();
        guiTable.add(lemonMoneyGUITable).align(Align.left).width(Gdx.graphics.getWidth()).colspan(3);
        guiTable.row().height(600).pad(10,10,0,0);
//        guiTable.add(wizard);
//        guiTable.add();
//        guiTable.add(enemy);
        guiTable.add(createFooterGUI()).bottom().colspan(3);

        return guiTable;
    }

    public Table createFooterGUI() {
        Wizard lightningWizard = new LightningWizard();
        Wizard fireWizard = new FireWizard();
        Wizard iceWizard = new IceWizard();

        TextureRegion lightningIdleSprite = lightningWizard.getIdleSprite();
        TextureRegion fireIdleSprite = fireWizard.getIdleSprite();
        TextureRegion iceIdleSprite = iceWizard.getIdleSprite();

        Image lightningWizardImage = new Image(lightningIdleSprite);
        Image fireWizardImage = new Image(fireIdleSprite);
        Image iceWizardImage = new Image(iceIdleSprite);

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        final GameOff2022 game = clickerScreen.getGame();
        final Stage stage = clickerScreen.getStage();

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();
                Group table = game.getCurrentWizard().getParent();

                Wizard currentWizard = game.getCurrentWizard();
                wizard.setX(currentWizard.getX());
                wizard.setY(currentWizard.getY());
//                wizard.setWidth(currentWizard.getWidth());
//                wizard.setHeight(currentWizard.getHeight());
                wizard.addListener(currentWizard.getListeners().get(0));
                wizard.setUserObject(currentWizard.getUserObject());

                table.removeActor(currentWizard);
                game.setCurrentWizard(wizard);
                wizard.setCurrentState(WizardState.IDLE);
                table.addActor(wizard);

//                table.addActor(wizard);

//                Array<Actor> actors = stage.getActors();
//                int changeIndex = -1;
//                for (int i = 0; i < actors.size; i++) {
//                    Actor actor = actors.get(i);
//                    if (actor instanceof Wizard) {
//                        changeIndex = i;
//                    }
//                }
//                float actorX = actors.get(changeIndex).getX();
//                float actorY = actors.get(changeIndex).getY();
//                wizard.setPosition(actorX, actorY);
                wizard.setCurrentState(WizardState.IDLE);

//                actors.set(changeIndex, wizard);
            }
        };

        lightningWizardImage.addListener(clickListener);
        fireWizardImage.addListener(clickListener);
        iceWizardImage.addListener(clickListener);

        lightningWizardImage.setBounds(0, 10, 64, 64);
        fireWizardImage.setBounds(70, 10, 64, 64);
        iceWizardImage.setBounds(140, 10, 64, 64);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getGameFontSmall();

        Label chooseWizardLabel = new Label("Change Current Wizard", labelStyle);
        Label healthPotionLabel = new Label("Use Health Potion", labelStyle);
        Label actionsLabel = new Label("Other Actions", labelStyle);

        final Image minorHealthPotionButton = new MinorHealthPotion().getImage();
        final Image smallHealthPotionButton = new SmallHealthPotion().getImage();
        final Image greatHealthPotionButton = new GreatHealthPotion().getImage();
        final Image greaterHealthPotionButton = new GreaterHealthPotion().getImage();

        minorHealthPotionButton.setUserObject(HealthPotionEnum.MinorHealthPotion);
        smallHealthPotionButton.setUserObject(HealthPotionEnum.SmallHealthPotion);
        greatHealthPotionButton.setUserObject(HealthPotionEnum.GreatHealthPotion);
        greaterHealthPotionButton.setUserObject(HealthPotionEnum.GreaterHealthPotion);

        final Label minorHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label smallHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label greatHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label greaterHealthPotionQuantityLabel = new Label("0", labelStyle);

        minorHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.MinorHealthPotion);
        smallHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.SmallHealthPotion);
        greatHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.GreatHealthPotion);
        greaterHealthPotionQuantityLabel.setUserObject(HealthPotionEnum.GreaterHealthPotion);

        minorHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        smallHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        greatHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        greaterHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) label.getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        minorHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                if (integer > 0) {
                    game.useHealthPotion(healthPotionEnum);
                    game.getCurrentWizard().useHealthPotion(new MinorHealthPotion());
                }
            }
        });

        smallHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                if (integer > 0) {
                    game.useHealthPotion(healthPotionEnum);
                    game.getCurrentWizard().useHealthPotion(new SmallHealthPotion());
                }
            }
        });

        greatHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                if (integer > 0) {
                    game.useHealthPotion(healthPotionEnum);
                    game.getCurrentWizard().useHealthPotion(new GreatHealthPotion());
                }
            }
        });

        greaterHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthPotionEnum healthPotionEnum = (HealthPotionEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getPotions().get(healthPotionEnum);
                if (integer > 0) {
                    game.useHealthPotion(healthPotionEnum);
                    game.getCurrentWizard().useHealthPotion(new GreaterHealthPotion());
                }
            }
        });

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getGameFontSmall();
        textButtonStyle.fontColor = Color.BLUE;

        TextButton shopButton = new TextButton("Shop", textButtonStyle);
        shopButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new HealthPotionShopScreen(game));
            }
        });

        TextButton upgradeButton = new TextButton("Upgrade", textButtonStyle);
        upgradeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new UpgradeScreen(game));
            }
        });

        TextButton achievementsButton = new TextButton("Achievements", textButtonStyle);

        Table actionsTable = new Table();
        actionsTable.add(shopButton);
        actionsTable.row();
        actionsTable.add(upgradeButton);
        actionsTable.row();
        actionsTable.add(achievementsButton);
        actionsTable.row();

        Table footerTable = new Table();
        footerTable.left().bottom().pad(10,10,0,0);
//        footerTable.setBounds(0, 0, Gdx.graphics.getWidth(), 300);
        footerTable.add(chooseWizardLabel).colspan(3);
        footerTable.add(healthPotionLabel).colspan(4);
        footerTable.add(actionsLabel).colspan(1);
        footerTable.row().height(64);
        footerTable.add(lightningWizardImage).width(64);
        footerTable.add(fireWizardImage).width(64);
        footerTable.add(iceWizardImage).width(64);
        footerTable.add(minorHealthPotionButton).width(64);
        footerTable.add(smallHealthPotionButton).width(64);
        footerTable.add(greatHealthPotionButton).width(64);
        footerTable.add(greaterHealthPotionButton).width(64);
        footerTable.add(actionsTable);
        footerTable.row().height(10);
        footerTable.add().colspan(3);
        footerTable.add(minorHealthPotionQuantityLabel).align(Align.right);
        footerTable.add(smallHealthPotionQuantityLabel).align(Align.right);
        footerTable.add(greatHealthPotionQuantityLabel).align(Align.right);
        footerTable.add(greaterHealthPotionQuantityLabel).align(Align.right);

        return footerTable;
    }
}