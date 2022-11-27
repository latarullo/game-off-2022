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
import com.mygdx.game.controller.GameAchievements;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GodMode;
import com.mygdx.game.domain.IceLemonTea;
import com.mygdx.game.domain.IceWizardItem;
import com.mygdx.game.domain.LemonCustardPie;
import com.mygdx.game.domain.LemonIceCream;
import com.mygdx.game.domain.LemonSquares;
import com.mygdx.game.domain.LightningWizardItem;
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
                    screen.getGame().getGameData().subtractMoney(healthPotion.getPrice());
                    screen.buyHealthPotion(healthPotion);
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
        Table learnableItems = new Table();
        ImageButton spellbook = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/spellbook.png"))));
        learnableItems.add(spellbook).size(128, 128);
        learnableItems.add().width(10);
        ImageButton healthup = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/health-up.png"))));
        learnableItems.add(healthup).size(128, 128);
        learnableItems.add().width(10);
        ImageButton foodPower = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/food-power.png"))));
        learnableItems.add(foodPower).size(128, 128);
        learnableItems.add().width(10);
        ImageButton foodCooldown = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("resources/food-cooldown.png"))));
        learnableItems.add(foodCooldown).size(128, 128);
        learnableItems.add().width(10);

        if (!LightningWizardItem.getInstance().isUnlocked()) {
            ImageButton lightningWizardButton = createLightningWizardButton();
            screen.getStage().addActor(lightningWizardButton);
        }

        if (!FireWizardItem.getInstance().isUnlocked()) {
            ImageButton fireWizardButton = createFireWizardButton();
            screen.getStage().addActor(fireWizardButton);
        }

        if (!IceWizardItem.getInstance().isUnlocked()) {
            ImageButton iceWizardButton = createIceWizardButton();
            screen.getStage().addActor(iceWizardButton);
        }

        if (!GodMode.getInstance().isUnlocked()) {
            ImageButton godModeButton = createGodModeButton();
            screen.getStage().addActor(godModeButton);
        }
    }

    private ImageButton createLightningWizardButton() {
        final LightningWizardItem lightningWizardItem = LightningWizardItem.getInstance();

        final ImageButton lightningWizardButton = new ImageButton(new TextureRegionDrawable(LightningWizard.getInstance().getIdleSprite()));

        lightningWizardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (lightningWizardItem.isUnlocked()) {
                    return;
                }

                Actor actor = event.getListenerActor();
                ConsumableItem healthPotion = (ConsumableItem) actor.getUserObject();
                if (screen.getGame().getGameData().getMoney().compareTo(lightningWizardItem.getPrice()) >= 0) {
                    GameUpgrades.getInstance().setLightningWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.LIGHTNING);
                    lightningWizardItem.setUnlocked(true);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        lightningWizardButton.setSize(128, 128);
        lightningWizardButton.setPosition(600, 50);
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

                Actor actor = event.getListenerActor();
                ConsumableItem healthPotion = (ConsumableItem) actor.getUserObject();
                if (screen.getGame().getGameData().getMoney().compareTo(fireWizardItem.getPrice()) >= 0) {
                    GameUpgrades.getInstance().setFireWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.FIRE);
                    fireWizardItem.setUnlocked(true);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        fireWizardButton.setSize(128, 128);
        fireWizardButton.setPosition(700, 50);
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

                Actor actor = event.getListenerActor();
                ConsumableItem healthPotion = (ConsumableItem) actor.getUserObject();
                if (screen.getGame().getGameData().getMoney().compareTo(iceWizardItem.getPrice()) >= 0) {
                    GameUpgrades.getInstance().setIceWizardAvailable(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.ICE);
                    iceWizardItem.setUnlocked(true);
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        iceWizardButton.setSize(128, 128);
        iceWizardButton.setPosition(850, 50);
        iceWizardButton.addListener(new HoverClickListener(iceWizardItem));

        return iceWizardButton;
    }

    private ImageButton createGodModeButton() {
        final GodMode godMode = GodMode.getInstance();

        ImageButton godModeButton = new ImageButton(new TextureRegionDrawable(godMode.getTexture()));
        godModeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (godMode.isUnlocked()) {
                    return;
                }

                Actor actor = event.getListenerActor();
                ConsumableItem healthPotion = (ConsumableItem) actor.getUserObject();
                if (screen.getGame().getGameData().getMoney().compareTo(godMode.getPrice()) >= 0) {
                    GameUpgrades.getInstance().setGodMode(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockGodMode();
                } else {
                    if (Math.random() < 0.5f) {
                        notEnoughLemonsSound.play();
                    } else {
                        youRequireMoreLemonsSound.play();
                    }
                }
            }
        });

        godModeButton.setSize(128, 128);
        godModeButton.setPosition(1000, 50);
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
