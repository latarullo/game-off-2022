package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.HealthConsumableEnum;
import com.mygdx.game.domain.IceLemonTea;
import com.mygdx.game.domain.LemonCustardPie;
import com.mygdx.game.domain.LemonIceCream;
import com.mygdx.game.domain.LemonSquares;
import com.mygdx.game.screen.AchievementsScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.ShopScreen;
import com.mygdx.game.screen.UpgradeScreen;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyFactory;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;

public class GameScreenGUI implements Disposable {
    private GameScreen gameScreen;
    private Sound switchWizardSound = Gdx.audio.newSound(Gdx.files.internal("sounds/switch-wizard.wav"));
    private Texture background = new Texture(Gdx.files.internal("resources/GameScreen/background.png"));
    private Texture achievementsBackground = new Texture(Gdx.files.internal("resources/AchievementsScreen/background.png"));
    private WizardHealthBarGUI2 wizardHealthBarGUI = new WizardHealthBarGUI2();
    private EnemyHealthBarGUI enemyHealthBarGUI = new EnemyHealthBarGUI();
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    public GameScreenGUI(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Table createGUI() {
        createBackground();

        final GameOff2022 game = gameScreen.getGame();
        final Stage stage = gameScreen.getStage();

        Enemy enemy = EnemyFactory.create(game);
        stage.addActor(enemy);

        Wizard wizard = game.getGameData().getCurrentWizard();
        wizard.setPosition(100, 100);
        wizard.setCurrentState(WizardState.IDLE);
        wizard.setUserObject(enemy);
        stage.addActor(wizard);

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(game);
        Table lemonMoneyGUITable = lemonMoneyGUI.createTable();

        Table guiTable = new Table();
        guiTable.setFillParent(true);
        guiTable.left().top();

        Table wizardHealthBar = wizardHealthBarGUI.createGUI();
        wizardHealthBar.setFillParent(true);
        wizardHealthBar.left().bottom();
        gameScreen.getStage().addActor(wizardHealthBar);

        Table enemyHealthBar = enemyHealthBarGUI.createGUI();
        enemyHealthBar.setFillParent(true);
        enemyHealthBar.right().bottom();
        gameScreen.getStage().addActor(enemyHealthBar);

        Table actions = new Table();

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new MainMenuScreen(game));
            }
        });

        ImageButton shopButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/shop.png"))));
        shopButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new ShopScreen(game));
            }
        });

        ImageButton achievementsButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/achievements.png"))));
        achievementsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new AchievementsScreen(game));
            }
        });

        actions.setFillParent(true);
        actions.top().left();
        actions.add(backButton);
        actions.add(shopButton);
        actions.add(achievementsButton);
        gameScreen.getStage().addActor(actions);

        LemonSquares lemonSquares = LemonSquares.getInstance();
        IceLemonTea iceLemonTea = IceLemonTea.getInstance();
        LemonIceCream lemonIceCream = LemonIceCream.getInstance();
        LemonCustardPie lemonCustardPie = LemonCustardPie.getInstance();

        ImageButton lemonSquaresButton = new ImageButton(new TextureRegionDrawable(lemonSquares.getTexture()));
        ImageButton iceLemonTeaButton = new ImageButton(new TextureRegionDrawable(iceLemonTea.getTexture()));
        ImageButton lemonIceCreamButton = new ImageButton(new TextureRegionDrawable(lemonIceCream.getTexture()));
        ImageButton lemonCustardPieButton = new ImageButton(new TextureRegionDrawable(lemonCustardPie.getTexture()));

        Table consumables = new Table();
        consumables.setFillParent(true);
        consumables.center().bottom();
        consumables.add(lemonSquaresButton).size(80,80);
        consumables.add(iceLemonTeaButton).size(80,80);
        consumables.add(lemonIceCreamButton).size(80,80);
        consumables.add(lemonCustardPieButton).size(80,80);
        consumables.row();

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(15, Color.WHITE);

        Label lemonSquaresQuantityLabel = new Label("0", labelStyle);
        Label iceLemonTeaButtonQuantityLabel = new Label("0", labelStyle);
        Label lemonIceCreamButtonQuantityLabel = new Label("0", labelStyle);
        Label lemonCustardPieButtonQuantityLabel = new Label("0", labelStyle);

        lemonSquaresQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
        iceLemonTeaButtonQuantityLabel.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
        lemonIceCreamButtonQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
        lemonCustardPieButtonQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);

        lemonSquaresButton.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
        iceLemonTeaButton.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
        lemonIceCreamButton.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
        lemonCustardPieButton.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);

        lemonSquaresQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        iceLemonTeaButtonQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        lemonIceCreamButtonQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        lemonCustardPieButtonQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        lemonSquaresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonSquares.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        iceLemonTeaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(IceLemonTea.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        lemonIceCreamButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonIceCream.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        lemonCustardPieButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonCustardPie.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        consumables.add(lemonSquaresQuantityLabel).align(Align.topRight);
        consumables.add(iceLemonTeaButtonQuantityLabel).align(Align.topRight);
        consumables.add(lemonIceCreamButtonQuantityLabel).align(Align.topRight);
        consumables.add(lemonCustardPieButtonQuantityLabel).align(Align.topRight);
        gameScreen.getStage().addActor(consumables);

        //lemonMoneyGUITable.setPosition(GameConstants.WIDTH / 2 - lemonMoneyGUITable.getWidth() / 2, GameConstants.HEIGHT - 50);
        lemonMoneyGUITable.setFillParent(true);
        lemonMoneyGUITable.right().top();
        gameScreen.getStage().addActor(lemonMoneyGUITable);

        //guiTable.add(wizardHealthBarGUI.createGUI()).width(500).height(150).align(Align.left);
        //guiTable.add(lemonMoneyGUITable).align(Align.topRight).expandX();
        guiTable.row().height(600).pad(10, 10, 0, 0);
        //guiTable.add(createFooterGUI()).bottom().colspan(3);

//        Table footerGUI = createFooterGUI();
//        footerGUI.setFillParent(true);
//        footerGUI.top().left();
//        gameScreen.getStage().addActor(footerGUI);

        return guiTable;
    }

    private void createBackground() {
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameScreen.getStage().addActor(backgroundImage);
    }

    public Table createFooterGUI() {
        Wizard lightningWizard = LightningWizard.getInstance();
        Wizard fireWizard = FireWizard.getInstance();
        Wizard iceWizard = IceWizard.getInstance();

        TextureRegion lightningIdleSprite = lightningWizard.getIdleSprite();
        TextureRegion fireIdleSprite = fireWizard.getIdleSprite();
        TextureRegion iceIdleSprite = iceWizard.getIdleSprite();

        Image lightningWizardImage = new Image(lightningIdleSprite);
        Image fireWizardImage = new Image(fireIdleSprite);
        Image iceWizardImage = new Image(iceIdleSprite);

        lightningWizardImage.setUserObject(lightningWizard);
        fireWizardImage.setUserObject(fireWizard);
        iceWizardImage.setUserObject(iceWizard);

        final GameOff2022 game = gameScreen.getGame();
        final Stage stage = gameScreen.getStage();

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Wizard currentWizard = game.getGameData().getCurrentWizard();
                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();

                if (currentWizard.getWizardType().equals(wizard.getWizardType())) {
                    System.out.println("Wizard " + wizard.getWizardType() + " already chosen");
                    return;
                }

                if (wizard.getWizardType() == WizardType.LIGHTNING && !GameUpgrades.getInstance().isLightningWizardAvailable() ||
                        wizard.getWizardType() == WizardType.FIRE && !GameUpgrades.getInstance().isFireWizardAvailable() ||
                        wizard.getWizardType() == WizardType.ICE && !GameUpgrades.getInstance().isIceWizardAvailable()) {
                    System.out.println("Wizard " + wizard.getWizardType() + " must be unlocked");
                    return;
                }

                Group table = game.getGameData().getCurrentWizard().getParent();

                wizard.setX(currentWizard.getX());
                wizard.setY(currentWizard.getY());
                wizard.setUserObject(currentWizard.getUserObject());

                table.removeActor(currentWizard);
                game.getGameData().setCurrentWizard(wizard);
                wizard.setCurrentState(WizardState.IDLE);
                table.addActor(wizard);
                switchWizardSound.play();

                wizard.setCurrentState(WizardState.IDLE);
            }
        };

        lightningWizardImage.addListener(clickListener);
        fireWizardImage.addListener(clickListener);
        iceWizardImage.addListener(clickListener);

        lightningWizardImage.setBounds(0, 10, 64, 64);
        fireWizardImage.setBounds(70, 10, 64, 64);
        iceWizardImage.setBounds(140, 10, 64, 64);

        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);

        Label chooseWizardLabel = new Label("Change Current Wizard", labelStyle);
        Label healthPotionLabel = new Label("Use Health Potion", labelStyle);
        Label actionsLabel = new Label("Other Actions", labelStyle);

        final Image minorHealthPotionButton = LemonSquares.getInstance().getImage();
        final Image smallHealthPotionButton = IceLemonTea.getInstance().getImage();
        final Image greatHealthPotionButton = LemonIceCream.getInstance().getImage();
        final Image greaterHealthPotionButton = LemonCustardPie.getInstance().getImage();

        minorHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
        smallHealthPotionButton.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
        greatHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
        greaterHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);

        final Label minorHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label smallHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label greatHealthPotionQuantityLabel = new Label("0", labelStyle);
        Label greaterHealthPotionQuantityLabel = new Label("0", labelStyle);

        minorHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
        smallHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
        greatHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
        greaterHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);

        minorHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        smallHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        greatHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        greaterHealthPotionQuantityLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label label = (Label) this.getActor();
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                label.setText(integer.toString());
                return false;
            }
        });

        minorHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonSquares.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        smallHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(IceLemonTea.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        greatHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonIceCream.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        greaterHealthPotionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
                if (integer > 0) {
                    if (game.getGameData().getCurrentWizard().canHeal()) {
                        game.getGameData().getCurrentWizard().useConsumable(LemonCustardPie.getInstance());
                        game.getGameData().useConsumable(healthConsumableEnum);
                    }
                }
            }
        });

        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);

        TextButton shopButton = new TextButton("Shop", textButtonStyle);
        shopButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new ShopScreen(game));
            }
        });

        TextButton upgradeButton = new TextButton("Upgrade", textButtonStyle);
        upgradeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new UpgradeScreen(game));
            }
        });

        TextButton achievementsButton = new TextButton("Achievements", textButtonStyle);
        achievementsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new AchievementsScreen(game));
            }
        });


        Table actionsTable = new Table();
        actionsTable.add(shopButton);
        actionsTable.row();
        actionsTable.add(upgradeButton);
        actionsTable.row();
        actionsTable.add(achievementsButton);
        actionsTable.row();

        Table footerTable = new Table();
        footerTable.left().bottom().pad(10, 10, 0, 0);
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

    @Override
    public void dispose() {
        switchWizardSound.dispose();
        background.dispose();
    }
}