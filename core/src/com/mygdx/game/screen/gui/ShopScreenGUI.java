package com.mygdx.game.screen.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameOff2022;
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.FoodCooldownItem;
import com.mygdx.game.domain.FoodHealthPowerItem;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GodMode;
import com.mygdx.game.domain.IceLemonTea;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.LemonCustardPie;
import com.mygdx.game.domain.LemonIceCream;
import com.mygdx.game.domain.LemonSquares;
import com.mygdx.game.domain.LightningWizardItem;
import com.mygdx.game.domain.WizardHealthPowerItem;
import com.mygdx.game.domain.WizardSpellPowerItem;
import com.mygdx.game.screen.ShopScreen;
import com.mygdx.game.screen.actor.FireWizard;
import com.mygdx.game.screen.actor.IceWizard;
import com.mygdx.game.screen.actor.LightningWizard;
import com.mygdx.game.screen.actor.WizardType;
import com.mygdx.game.util.GameFontGenerator;
import com.mygdx.game.util.GameFontSizeEnum;
import com.mygdx.game.util.HoverClickListener;

public class ShopScreenGUI implements Disposable {

    private ShopScreen screen;
    private GameFontGenerator gameFontGenerator = GameFontGenerator.getInstance();
    private Texture background = new Texture(Gdx.files.internal("resources/ShopScreen/background.jpg"));
    private Sound notEnoughLemonsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/not-enough-lemons.mp3"));
    private Sound youRequireMoreLemonsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/you-require-more-lemons.mp3"));

    public ShopScreenGUI(ShopScreen screen) {
        this.screen = screen;
    }

    public void createGUI() {
        createBackground();
        createMoneyDisplay();
        createBackButton();
        createShopHeaders();
        createShopItems();
    }

    private void createShopItems() {
        createShopConsumableItems();
        createShopUnlockableItems();
    }

    private void createShopConsumableItems() {
        LemonSquares lemonSquares = LemonSquares.getInstance();
        IceLemonTea iceLemonTea = IceLemonTea.getInstance();
        LemonIceCream lemonIceCream = LemonIceCream.getInstance();
        LemonCustardPie lemonCustardPie = LemonCustardPie.getInstance();

        ImageButton lemonSquaresButton = new ImageButton(new TextureRegionDrawable(lemonSquares.getTexture()));
        ImageButton iceLemonTeaButton = new ImageButton(new TextureRegionDrawable(iceLemonTea.getTexture()));
        ImageButton lemonIceCreamButton = new ImageButton(new TextureRegionDrawable(lemonIceCream.getTexture()));
        ImageButton lemonCustardPieButton = new ImageButton(new TextureRegionDrawable(lemonCustardPie.getTexture()));

        ClickListener clickListener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Actor actor = event.getListenerActor();
                ConsumableItem healthPotion = (ConsumableItem) actor.getUserObject();
                if (screen.getGame().getGameData().getMoney().compareTo(healthPotion.getPrice()) >= 0) {
                    screen.buyConsumable(healthPotion);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        };

        lemonSquaresButton.setUserObject(lemonSquares);
        iceLemonTeaButton.setUserObject(iceLemonTea);
        lemonIceCreamButton.setUserObject(lemonIceCream);
        lemonCustardPieButton.setUserObject(lemonCustardPie);

        lemonSquaresButton.addListener(clickListener);
        iceLemonTeaButton.addListener(clickListener);
        lemonIceCreamButton.addListener(clickListener);
        lemonCustardPieButton.addListener(clickListener);


        int posY = GameConstants.HEIGHT / 2 + 20;

        lemonSquaresButton.setSize(256, 256);
        lemonSquaresButton.setPosition(100, posY );
        lemonSquaresButton.addListener(new HoverClickListener(lemonSquares));

        iceLemonTeaButton.setSize(256, 256);
        iceLemonTeaButton.setPosition(350, posY);
        iceLemonTeaButton.addListener(new HoverClickListener(iceLemonTea));

        lemonIceCreamButton.setSize(256, 256);
        lemonIceCreamButton.setPosition(600, posY );
        lemonIceCreamButton.addListener(new HoverClickListener(lemonIceCream));

        lemonCustardPieButton.setSize(256, 256);
        lemonCustardPieButton.setPosition(850, posY );
        lemonCustardPieButton.addListener(new HoverClickListener(lemonCustardPie));

        screen.getStage().addActor(lemonSquaresButton);
        screen.getStage().addActor(iceLemonTeaButton);
        screen.getStage().addActor(lemonIceCreamButton);
        screen.getStage().addActor(lemonCustardPieButton);
    }

    private void createShopUnlockableItems() {
        int posX = 100;
        int posY = 70;
        int buttonWidth = 128;
        int buttonHeight = 128;

        ImageButton wizardSpellPowerButton = createWizardSpellPowerButton();
        wizardSpellPowerButton.setSize(buttonWidth, buttonHeight);
        wizardSpellPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(wizardSpellPowerButton);

        posX+=150;
        ImageButton wizardHealthPowerButton = createWizardHealthPowerButton();
        wizardHealthPowerButton.setSize(buttonWidth, buttonHeight);
        wizardHealthPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(wizardHealthPowerButton);

        posX+=150;
        ImageButton foodHealthPowerButton = createFoodHealthPowerButton();
        foodHealthPowerButton.setSize(buttonWidth, buttonHeight);
        foodHealthPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(foodHealthPowerButton);

        posX+=150;
        ImageButton foodCooldownButton = createFoodCooldownButton();
        foodCooldownButton.setSize(buttonWidth, buttonHeight);
        foodCooldownButton.setPosition(posX, posY);
        screen.getStage().addActor(foodCooldownButton);

        if (!LightningWizardItem.getInstance().isUnlocked()) {
            posX+=150;
            ImageButton lightningWizardButton = createLightningWizardButton();
            lightningWizardButton.setSize(buttonWidth, buttonHeight);
            lightningWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(lightningWizardButton);
        }

        if (!FireWizardItem.getInstance().isUnlocked()) {
            posX+=150;
            ImageButton fireWizardButton = createFireWizardButton();
            fireWizardButton.setSize(buttonWidth, buttonHeight);
            fireWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(fireWizardButton);
        }

        if (!IceWizardItem.getInstance().isUnlocked()) {
            posX+=150;
            ImageButton iceWizardButton = createIceWizardButton();
            iceWizardButton.setSize(buttonWidth, buttonHeight);
            iceWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(iceWizardButton);
        }

        if (!GodMode.getInstance().isUnlocked()) {
            posX+=150;
            ImageButton godModeButton = createGodModeButton();
            godModeButton.setSize(buttonWidth, buttonHeight);
            godModeButton.setPosition(posX, posY);
            screen.getStage().addActor(godModeButton);
        }
    }

    private ImageButton createWizardSpellPowerButton() {
        final WizardSpellPowerItem wizardSpellPowerItem = WizardSpellPowerItem.getInstance();
        final ImageButton wizardSpellPowerButton = new ImageButton(new TextureRegionDrawable(wizardSpellPowerItem.getTexture()));

        wizardSpellPowerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getGame().getGameData().getMoney().compareTo(wizardSpellPowerItem.getPrice()) >= 0) {
                    screen.buyUpgradeable(wizardSpellPowerItem);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        wizardSpellPowerButton.addListener(new HoverClickListener(wizardSpellPowerItem));

        return wizardSpellPowerButton;
    }

    private ImageButton createWizardHealthPowerButton() {
        final WizardHealthPowerItem wizardHealthPowerItem = WizardHealthPowerItem.getInstance();
        final ImageButton wizardHealthPowerButton = new ImageButton(new TextureRegionDrawable(wizardHealthPowerItem.getTexture()));

        wizardHealthPowerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getGame().getGameData().getMoney().compareTo(wizardHealthPowerItem.getPrice()) >= 0) {
                    screen.buyUpgradeable(wizardHealthPowerItem);
                    GameOff2022.getInstance().wizardHelthUp(wizardHealthPowerItem);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        wizardHealthPowerButton.addListener(new HoverClickListener(wizardHealthPowerItem));

        return wizardHealthPowerButton;
    }

    private ImageButton createFoodHealthPowerButton() {
        final FoodHealthPowerItem foodHealthPowerItem = FoodHealthPowerItem.getInstance();
        final ImageButton foodHealthPowerButton = new ImageButton(new TextureRegionDrawable(foodHealthPowerItem.getTexture()));

        foodHealthPowerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getGame().getGameData().getMoney().compareTo(foodHealthPowerItem.getPrice()) >= 0) {
                    screen.buyUpgradeable(foodHealthPowerItem);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        foodHealthPowerButton.addListener(new HoverClickListener(foodHealthPowerItem));

        return foodHealthPowerButton;
    }

    private ImageButton createFoodCooldownButton() {
        final FoodCooldownItem foodCooldownItem = FoodCooldownItem.getInstance();
        final ImageButton foodCooldownButton = new ImageButton(new TextureRegionDrawable(foodCooldownItem.getTexture()));

        foodCooldownButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getGame().getGameData().getMoney().compareTo(foodCooldownItem.getPrice()) >= 0) {
                    screen.buyUpgradeable(foodCooldownItem);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        foodCooldownButton.addListener(new HoverClickListener(foodCooldownItem));

        return foodCooldownButton;
    }

    private ImageButton createLightningWizardButton() {
        final LightningWizardItem lightningWizardItem = LightningWizardItem.getInstance();

        final ImageButton lightningWizardButton = new ImageButton(new TextureRegionDrawable(LightningWizard.getInstance().getIdleSprite()));

        lightningWizardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (lightningWizardItem.isUnlocked()) {
                    return;
                }

                if (screen.getGame().getGameData().getMoney().compareTo(lightningWizardItem.getPrice()) >= 0) {
                    screen.buyUnlockable(lightningWizardItem);
                    GameUpgrades.getInstance().setLightningWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.LIGHTNING);
                    lightningWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        lightningWizardButton.addListener(new HoverClickListener(lightningWizardItem));

        return lightningWizardButton;
    }

    private ImageButton createFireWizardButton() {
        final FireWizardItem fireWizardItem = FireWizardItem.getInstance();

        final ImageButton fireWizardButton = new ImageButton(new TextureRegionDrawable(FireWizard.getInstance().getIdleSprite()));

        fireWizardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (fireWizardItem.isUnlocked()) {
                    return;
                }

                if (screen.getGame().getGameData().getMoney().compareTo(fireWizardItem.getPrice()) >= 0) {
                    screen.buyUnlockable(fireWizardItem);
                    GameUpgrades.getInstance().setFireWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.FIRE);
                    fireWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        fireWizardButton.addListener(new HoverClickListener(fireWizardItem));

        return fireWizardButton;
    }

    private ImageButton createIceWizardButton() {
        final IceWizardItem iceWizardItem = IceWizardItem.getInstance();

        final ImageButton iceWizardButton = new ImageButton(new TextureRegionDrawable(IceWizard.getInstance().getIdleSprite()));

        iceWizardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (iceWizardItem.isUnlocked()) {
                    return;
                }

                if (screen.getGame().getGameData().getMoney().compareTo(iceWizardItem.getPrice()) >= 0) {
                    screen.buyUnlockable(iceWizardItem);
                    GameUpgrades.getInstance().setIceWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.ICE);
                    iceWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        iceWizardButton.addListener(new HoverClickListener(iceWizardItem));

        return iceWizardButton;
    }

    private ImageButton createGodModeButton() {
        final GodMode godMode = GodMode.getInstance();

        final ImageButton godModeButton = new ImageButton(new TextureRegionDrawable(godMode.getTexture()));
        godModeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (godMode.isUnlocked()) {
                    return;
                }

                if (screen.getGame().getGameData().getMoney().compareTo(godMode.getPrice()) >= 0) {
                    screen.buyUnlockable(godMode);
                    GameUpgrades.getInstance().setGodMode(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockGodMode();
                    godModeButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });


        godModeButton.addListener(new HoverClickListener(godMode));
        return godModeButton;
    }

    private void createShopHeaders() {
        Label.LabelStyle labelHeaderStyle = gameFontGenerator.generateLabelStyle(GameFontSizeEnum.NORMAL, Color.WHITE);
        Label consumablesLabel = new Label("Consumables", labelHeaderStyle);
        consumablesLabel.setPosition(GameConstants.WIDTH / 2 - consumablesLabel.getWidth() / 2, GameConstants.HEIGHT - consumablesLabel.getHeight() - 10);
        screen.getStage().addActor(consumablesLabel);

        Label learnablesLabel = new Label("Learnables/Unlockables", labelHeaderStyle);
        learnablesLabel.setPosition(GameConstants.WIDTH / 2 - learnablesLabel.getWidth() / 2, GameConstants.HEIGHT / 2 - learnablesLabel.getHeight() / 2 - 10);
        screen.getStage().addActor(learnablesLabel);
    }

    private void createBackground() {
        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getStage().addActor(backgroundImage);
    }

    private void createMoneyDisplay() {
        LemonMoneyGUI lemonMoneyGUI = new LemonMoneyGUI(screen.getGame());
        Table lemonMoneyGUITable = lemonMoneyGUI.createTable();
        lemonMoneyGUITable.setFillParent(true);
        lemonMoneyGUITable.right().top();
        screen.getStage().addActor(lemonMoneyGUITable);
    }

    private void createBackButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/Icons/back.png"))));
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().changePreviousScreen();
            }
        });
        backButton.setPosition(10, GameConstants.HEIGHT - backButton.getHeight());
        screen.getStage().addActor(backButton);
    }

    @Override
    public void dispose() {
        background.dispose();
        notEnoughLemonsSound.dispose();
        youRequireMoreLemonsSound.dispose();
    }
}
