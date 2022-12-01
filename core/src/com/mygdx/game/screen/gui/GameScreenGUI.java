package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.domain.GodModeItem;
import com.mygdx.game.domain.HealthConsumableEnum;
import com.mygdx.game.domain.IceLemonTea;
import com.mygdx.game.domain.LemonCustardPie;
import com.mygdx.game.domain.LemonIceCream;
import com.mygdx.game.domain.LemonSquares;
import com.mygdx.game.screen.AchievementsScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.ShopScreen;
import com.mygdx.game.screen.actor.Enemy;
import com.mygdx.game.screen.actor.EnemyFactory;
import com.mygdx.game.screen.actor.EnemyState;
import com.mygdx.game.screen.actor.Wizard;
import com.mygdx.game.screen.actor.WizardState;
import com.mygdx.game.util.GameFontGenerator;

import java.util.Arrays;
import java.util.List;

public class GameScreenGUI implements Disposable {
    private GameOff2022 game = GameOff2022.getInstance();
    private GameScreen gameScreen;
    private Texture background = new Texture(Gdx.files.internal("resources/GameScreen/background.png"));
    private Texture achievementsBackground = new Texture(Gdx.files.internal("resources/AchievementsScreen/background.png"));
    private WizardHealthBarGUI2 wizardHealthBarGUI = new WizardHealthBarGUI2();
    private EnemyHealthBarGUI enemyHealthBarGUI = new EnemyHealthBarGUI();
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();

    private Texture textureJuiceJar000percentTexture = new Texture(Gdx.files.internal("resources/Icons/juice-jar-000.png"));
    private Texture textureJuiceJar025percentTexture = new Texture(Gdx.files.internal("resources/Icons/juice-jar-025.png"));
    private Texture textureJuiceJar050percentTexture = new Texture(Gdx.files.internal("resources/Icons/juice-jar-050.png"));
    private Texture textureJuiceJar075percentTexture = new Texture(Gdx.files.internal("resources/Icons/juice-jar-075.png"));
    private Texture textureJuiceJar100percentTexture = new Texture(Gdx.files.internal("resources/Icons/juice-jar-100.png"));

    private final Image juiceJarImage000percent = new Image(textureJuiceJar000percentTexture);
    private final Image juiceJarImage025percent = new Image(textureJuiceJar025percentTexture);
    private final Image juiceJarImage050percent = new Image(textureJuiceJar050percentTexture);
    private final Image juiceJarImage075percent = new Image(textureJuiceJar075percentTexture);
    private final Image juiceJarImage100percent = new Image(textureJuiceJar100percentTexture);

    private List<Image> juiceJarImages = Arrays.asList(juiceJarImage000percent, juiceJarImage025percent, juiceJarImage050percent, juiceJarImage075percent, juiceJarImage100percent);

    private Table lemonJarTable;
    private Enemy enemy;

    public GameScreenGUI(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void createGUI() {
        createBackground();

        final GameOff2022 game = gameScreen.getGame();
        final Stage stage = gameScreen.getStage();

        enemy = EnemyFactory.createEnemy();
        stage.addActor(enemy);

        Wizard wizard = game.getGameData().getCurrentWizard();
        wizard.setCurrentState(WizardState.IDLE);
        stage.addActor(wizard);

        createInvisibleJuiceJar();

        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(game);
        Table lemonMoneyGUITable = lemonMoneyGUI.createTable();

        createHealthBarsGUI();
        createActionsGUI();
        createConsumablesGUI();

        lemonMoneyGUITable.setFillParent(true);
        lemonMoneyGUITable.right().top();
        gameScreen.getStage().addActor(lemonMoneyGUITable);
    }

    private void createHealthBarsGUI() {
        createWizardHealthBar();

        createEnemyHealthBar();
    }

    private void createWizardHealthBar() {
        Table wizardHealthBar = wizardHealthBarGUI.createGUI();
        wizardHealthBar.setFillParent(true);
        wizardHealthBar.left().bottom();
        gameScreen.getStage().addActor(wizardHealthBar);
    }

    private void createEnemyHealthBar() {
        Table enemyHealthBar = enemyHealthBarGUI.createGUI();
        enemyHealthBar.setFillParent(true);
        enemyHealthBar.right().bottom();
        gameScreen.getStage().addActor(enemyHealthBar);
    }

    private void createActionsGUI() {
        Table actions = new Table();

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameSoundPlayer.stop(gameScreen.getMusic());
                game.changeScreen(MainMenuScreen.getInstance());
            }
        });

        ImageButton shopButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/shop.png"))));
        shopButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new ShopScreen());
            }
        });

        ImageButton achievementsButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/achievements.png"))));
        achievementsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new AchievementsScreen());
            }
        });

        actions.setFillParent(true);
        actions.top().left();
        actions.add(backButton);
        actions.add(shopButton);
        actions.add(achievementsButton);
        gameScreen.getStage().addActor(actions);
    }

    private void createConsumablesGUI() {
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
        consumables.add(lemonSquaresButton).size(80, 80);
        consumables.add(iceLemonTeaButton).size(80, 80);
        consumables.add(lemonIceCreamButton).size(80, 80);
        consumables.add(lemonCustardPieButton).size(80, 80);
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
    }

    private void createInvisibleJuiceJar() {
        for (Image juiceJarImage : juiceJarImages) {
            juiceJarImage.setPosition(50, 400);
            juiceJarImage.setSize(128, 128);
            juiceJarImage.setVisible(false);
            gameScreen.getStage().addActor(juiceJarImage);
        }

        Label.LabelStyle juiceJarLabelStyle = gameFontGenerator.generateLabelStyle(15, Color.BLUE);

        final Label juiceJarLabel = new Label("Lemons to go", juiceJarLabelStyle);
        juiceJarLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (GodModeItem.getInstance().isUnlocked()) {
                    float percentComplete = GameData.getInstance().getEnemyKilledCounter() / (float) GameData.getInstance().getNeedLemonsForLemonade();
                    if (percentComplete >= 0 && percentComplete <= 0.25) {
                        enableJuiceJar(juiceJarImage000percent);
                    } else if (percentComplete > 0.25 && percentComplete <= 0.50) {
                        enableJuiceJar(juiceJarImage025percent);
                    } else if (percentComplete > 0.50 && percentComplete <= 0.75) {
                        enableJuiceJar(juiceJarImage050percent);
                    } else if (percentComplete > 0.75 && percentComplete <= 0.95) {
                        enableJuiceJar(juiceJarImage075percent);
                    } else {
                        enableJuiceJar(juiceJarImage100percent);
                    }
                }

                juiceJarLabel.setText(GameData.getInstance().getLemonsToFinishLemonade() + " lemons remaining");
                if (GodModeItem.getInstance().isUnlocked() && GameData.getInstance().isLemonadeDone()) {
                    GameAchievements.getInstance().lemonadeMade();
                    return true;
                }
                return false;
            }

            private void enableJuiceJar(Image juiceJarImage) {
                for (Image image : juiceJarImages) {
                    image.setVisible(image == juiceJarImage);
                }
            }
        });

        lemonJarTable = new Table();
        lemonJarTable.row();
        lemonJarTable.add(juiceJarLabel);

        lemonJarTable.setPosition(100, 400);
        lemonJarTable.setVisible(false);

        gameScreen.getStage().addActor(lemonJarTable);
    }

    private void createBackground() {
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameScreen.getStage().addActor(backgroundImage);
    }

//    public Table createFooterGUI() {
//        Wizard lightningWizard = LightningWizard.getInstance();
//        Wizard fireWizard = FireWizard.getInstance();
//        Wizard iceWizard = IceWizard.getInstance();
//
//        TextureRegion lightningIdleSprite = lightningWizard.getIdleSprite();
//        TextureRegion fireIdleSprite = fireWizard.getIdleSprite();
//        TextureRegion iceIdleSprite = iceWizard.getIdleSprite();
//
//        Image lightningWizardImage = new Image(lightningIdleSprite);
//        Image fireWizardImage = new Image(fireIdleSprite);
//        Image iceWizardImage = new Image(iceIdleSprite);
//
//        lightningWizardImage.setUserObject(lightningWizard);
//        fireWizardImage.setUserObject(fireWizard);
//        iceWizardImage.setUserObject(iceWizard);
//
//        final GameOff2022 game = gameScreen.getGame();
//        final Stage stage = gameScreen.getStage();
//
//        ClickListener clickListener = new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                Wizard currentWizard = game.getGameData().getCurrentWizard();
//                Wizard wizard = (Wizard) event.getListenerActor().getUserObject();
//
//                if (currentWizard.getWizardType().equals(wizard.getWizardType())) {
//                    System.out.println("Wizard " + wizard.getWizardType() + " already chosen");
//                    return;
//                }
//
//                if (wizard.getWizardType() == WizardType.LIGHTNING && !GameUpgrades.getInstance().isLightningWizardAvailable() ||
//                        wizard.getWizardType() == WizardType.FIRE && !GameUpgrades.getInstance().isFireWizardAvailable() ||
//                        wizard.getWizardType() == WizardType.ICE && !GameUpgrades.getInstance().isIceWizardAvailable()) {
//                    System.out.println("Wizard " + wizard.getWizardType() + " must be unlocked");
//                    return;
//                }
//
//                Group group = GameScreen.getInstance().getStage().getRoot();
//
//                wizard.setX(currentWizard.getX());
//                wizard.setY(currentWizard.getY());
//                wizard.setUserObject(currentWizard.getUserObject());
//
//                group.removeActor(currentWizard);
//                game.getGameData().setCurrentWizard(wizard);
//                wizard.setCurrentState(WizardState.IDLE);
//                group.addActor(wizard);
//                currentWizard.wizardSwitched();
//            }
//        };
//
//        lightningWizardImage.addListener(clickListener);
//        fireWizardImage.addListener(clickListener);
//        iceWizardImage.addListener(clickListener);
//
//        lightningWizardImage.setBounds(0, 10, 64, 64);
//        fireWizardImage.setBounds(70, 10, 64, 64);
//        iceWizardImage.setBounds(140, 10, 64, 64);
//
//        Label.LabelStyle labelStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.SMALL, Color.WHITE);
//
//        Label chooseWizardLabel = new Label("Change Current Wizard", labelStyle);
//        Label healthPotionLabel = new Label("Use Health Potion", labelStyle);
//        Label actionsLabel = new Label("Other Actions", labelStyle);
//
//        final Image minorHealthPotionButton = LemonSquares.getInstance().getImage();
//        final Image smallHealthPotionButton = IceLemonTea.getInstance().getImage();
//        final Image greatHealthPotionButton = LemonIceCream.getInstance().getImage();
//        final Image greaterHealthPotionButton = LemonCustardPie.getInstance().getImage();
//
//        minorHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
//        smallHealthPotionButton.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
//        greatHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
//        greaterHealthPotionButton.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);
//
//        final Label minorHealthPotionQuantityLabel = new Label("0", labelStyle);
//        Label smallHealthPotionQuantityLabel = new Label("0", labelStyle);
//        Label greatHealthPotionQuantityLabel = new Label("0", labelStyle);
//        Label greaterHealthPotionQuantityLabel = new Label("0", labelStyle);
//
//        minorHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_SQUARES);
//        smallHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.ICE_LEMON_TEA);
//        greatHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_ICE_CREAM);
//        greaterHealthPotionQuantityLabel.setUserObject(HealthConsumableEnum.LEMON_CUSTARD_PIE);
//
//        minorHealthPotionQuantityLabel.addAction(new Action() {
//            @Override
//            public boolean act(float delta) {
//                Label label = (Label) this.getActor();
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                label.setText(integer.toString());
//                return false;
//            }
//        });
//
//        smallHealthPotionQuantityLabel.addAction(new Action() {
//            @Override
//            public boolean act(float delta) {
//                Label label = (Label) this.getActor();
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                label.setText(integer.toString());
//                return false;
//            }
//        });
//
//        greatHealthPotionQuantityLabel.addAction(new Action() {
//            @Override
//            public boolean act(float delta) {
//                Label label = (Label) this.getActor();
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                label.setText(integer.toString());
//                return false;
//            }
//        });
//
//        greaterHealthPotionQuantityLabel.addAction(new Action() {
//            @Override
//            public boolean act(float delta) {
//                Label label = (Label) this.getActor();
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) label.getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                label.setText(integer.toString());
//                return false;
//            }
//        });
//
//        minorHealthPotionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                if (integer > 0) {
//                    if (game.getGameData().getCurrentWizard().canHeal()) {
//                        game.getGameData().getCurrentWizard().useConsumable(LemonSquares.getInstance());
//                        game.getGameData().useConsumable(healthConsumableEnum);
//                    }
//                }
//            }
//        });
//
//        smallHealthPotionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                if (integer > 0) {
//                    if (game.getGameData().getCurrentWizard().canHeal()) {
//                        game.getGameData().getCurrentWizard().useConsumable(IceLemonTea.getInstance());
//                        game.getGameData().useConsumable(healthConsumableEnum);
//                    }
//                }
//            }
//        });
//
//        greatHealthPotionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                if (integer > 0) {
//                    if (game.getGameData().getCurrentWizard().canHeal()) {
//                        game.getGameData().getCurrentWizard().useConsumable(LemonIceCream.getInstance());
//                        game.getGameData().useConsumable(healthConsumableEnum);
//                    }
//                }
//            }
//        });
//
//        greaterHealthPotionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                HealthConsumableEnum healthConsumableEnum = (HealthConsumableEnum) event.getListenerActor().getUserObject();
//                Integer integer = game.getGameData().getConsumables().get(healthConsumableEnum);
//                if (integer > 0) {
//                    if (game.getGameData().getCurrentWizard().canHeal()) {
//                        game.getGameData().getCurrentWizard().useConsumable(LemonCustardPie.getInstance());
//                        game.getGameData().useConsumable(healthConsumableEnum);
//                    }
//                }
//            }
//        });
//
//        TextButton.TextButtonStyle textButtonStyle = gameFontGenerator.generateTextButtonStyle(GameFontSizeEnum.NORMAL, GameConstants.GUI_ACTION_COLOR);
//
//        TextButton shopButton = new TextButton("Shop", textButtonStyle);
//        shopButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                game.changeScreen(new ShopScreen());
//            }
//        });
//
//        TextButton upgradeButton = new TextButton("Upgrade", textButtonStyle);
//        upgradeButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                game.changeScreen(new UpgradeScreen(game));
//            }
//        });
//
//        TextButton achievementsButton = new TextButton("Achievements", textButtonStyle);
//        achievementsButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                game.changeScreen(new AchievementsScreen());
//            }
//        });
//
//
//        Table actionsTable = new Table();
//        actionsTable.add(shopButton);
//        actionsTable.row();
//        actionsTable.add(upgradeButton);
//        actionsTable.row();
//        actionsTable.add(achievementsButton);
//        actionsTable.row();
//
//        Table footerTable = new Table();
//        footerTable.left().bottom().pad(10, 10, 0, 0);
//        footerTable.add(chooseWizardLabel).colspan(3);
//        footerTable.add(healthPotionLabel).colspan(4);
//        footerTable.add(actionsLabel).colspan(1);
//        footerTable.row().height(64);
//        footerTable.add(lightningWizardImage).width(64);
//        footerTable.add(fireWizardImage).width(64);
//        footerTable.add(iceWizardImage).width(64);
//        footerTable.add(minorHealthPotionButton).width(64);
//        footerTable.add(smallHealthPotionButton).width(64);
//        footerTable.add(greatHealthPotionButton).width(64);
//        footerTable.add(greaterHealthPotionButton).width(64);
//        footerTable.add(actionsTable);
//        footerTable.row().height(10);
//        footerTable.add().colspan(3);
//        footerTable.add(minorHealthPotionQuantityLabel).align(Align.right);
//        footerTable.add(smallHealthPotionQuantityLabel).align(Align.right);
//        footerTable.add(greatHealthPotionQuantityLabel).align(Align.right);
//        footerTable.add(greaterHealthPotionQuantityLabel).align(Align.right);
//
//        return footerTable;
//    }

    public void recreateWizard() {
        Wizard newWizard = game.getGameData().getCurrentWizard();
        Array<Actor> actors = gameScreen.getStage().getActors();

        int removeIndex = -1;
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            if (actor instanceof Wizard) {
                Wizard wizard = (Wizard) actor;
                newWizard.setX(wizard.getX());
                newWizard.setY(wizard.getY());
                newWizard.setUserObject(wizard.getUserObject());
                if (wizard.getWizardType() != newWizard.getWizardType()) {
                    removeIndex = i;
                    break;
                }
            }
        }

        if (removeIndex == -1) {
            return;
        }

        actors.removeIndex(removeIndex);

        game.getGameData().setCurrentWizard(newWizard);
        newWizard.setCurrentState(WizardState.IDLE);
        actors.add(newWizard);

        newWizard.setCurrentState(WizardState.IDLE);

        wizardHealthBarGUI.makeWizarwizardSwitcherGUIInvisible();
    }

    @Override
    public void dispose() {
        background.dispose();
    }

    public List<Image> getJuiceJarImages() {
        return juiceJarImages;
    }

    public void makeLemonJarTableVisible() {
        lemonJarTable.setVisible(true);
    }

    public void makeLemonJarTableInvisible() {
        lemonJarTable.setVisible(false);
    }

    public void recreateEnemy() {
        Enemy newEnemy = EnemyFactory.createEnemy();

        Array<Actor> actors = gameScreen.getStage().getActors();

        int removeIndex = -1;
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            if (actor instanceof Enemy) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) {
            return;
        }

        actors.removeIndex(removeIndex);

        game.getGameData().setCurrentEnemy(newEnemy);
        newEnemy.setCurrentState(EnemyState.ATTACK);
        actors.add(newEnemy);
    }
}