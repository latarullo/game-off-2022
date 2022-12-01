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
import com.mygdx.game.domain.GameAutoClickerItem;
import com.mygdx.game.controller.GameSoundPlayer;
import com.mygdx.game.controller.GameUpgrades;
import com.mygdx.game.domain.ConsumableItem;
import com.mygdx.game.domain.FireWizardItem;
import com.mygdx.game.domain.FoodCooldownItem;
import com.mygdx.game.domain.FoodHealthPowerItem;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GodModeItem;
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
    private Sound notEnoughLemonsSound = Gdx.audio.newSound(Gdx.files.internal("resources/ShopScreen/not-enough-lemons.mp3"));
    private Sound youRequireMoreLemonsSound = Gdx.audio.newSound(Gdx.files.internal("resources/ShopScreen/you-require-more-lemons.mp3"));

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
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
        lemonSquaresButton.setPosition(100, posY);
        lemonSquaresButton.addListener(new HoverClickListener(lemonSquares));

        iceLemonTeaButton.setSize(256, 256);
        iceLemonTeaButton.setPosition(350, posY);
        iceLemonTeaButton.addListener(new HoverClickListener(iceLemonTea));

        lemonIceCreamButton.setSize(256, 256);
        lemonIceCreamButton.setPosition(600, posY);
        lemonIceCreamButton.addListener(new HoverClickListener(lemonIceCream));

        lemonCustardPieButton.setSize(256, 256);
        lemonCustardPieButton.setPosition(850, posY);
        lemonCustardPieButton.addListener(new HoverClickListener(lemonCustardPie));

        screen.getStage().addActor(lemonSquaresButton);
        screen.getStage().addActor(iceLemonTeaButton);
        screen.getStage().addActor(lemonIceCreamButton);
        screen.getStage().addActor(lemonCustardPieButton);
    }

    private void createShopUnlockableItems() {
        int stepX = 190;
        int posX = 70;
        int posY = 70;
        int buttonWidth = 200;
        int buttonHeight = 200;

        ImageButton wizardSpellPowerButton = createWizardSpellPowerButton();
        wizardSpellPowerButton.setSize(200, 200);
        wizardSpellPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(wizardSpellPowerButton);

        posX += stepX;
        ImageButton wizardHealthPowerButton = createWizardHealthPowerButton();
        wizardHealthPowerButton.setSize(200, 200);
        wizardHealthPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(wizardHealthPowerButton);

        posX += stepX;
        ImageButton foodHealthPowerButton = createFoodHealthPowerButton();
        foodHealthPowerButton.setSize(200, 200);
        foodHealthPowerButton.setPosition(posX, posY);
        screen.getStage().addActor(foodHealthPowerButton);

        posX += stepX;
        ImageButton foodCooldownButton = createFoodCooldownButton();
        foodCooldownButton.setSize(200, 200);
        foodCooldownButton.setPosition(posX, posY);
        screen.getStage().addActor(foodCooldownButton);

        if (!GameAutoClickerItem.getInstance().isUnlocked()) {
            posX += stepX;
            ImageButton autoClickButton = createAutoClickButton();
            autoClickButton.setSize(200, 200);
            autoClickButton.setPosition(posX, posY);
            screen.getStage().addActor(autoClickButton);
        }

        if (GameAutoClickerItem.getInstance().isUnlocked() &&
                !LightningWizardItem.getInstance().isUnlocked()) {
            posX += stepX;
            ImageButton lightningWizardButton = createLightningWizardButton();
            lightningWizardButton.setSize(buttonWidth, buttonHeight);
            lightningWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(lightningWizardButton);
        }

        if (GameAutoClickerItem.getInstance().isUnlocked() &&
                !FireWizardItem.getInstance().isUnlocked()) {
            posX += stepX;
            ImageButton fireWizardButton = createFireWizardButton();
            fireWizardButton.setSize(buttonWidth, buttonHeight);
            fireWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(fireWizardButton);
        }

        if (GameAutoClickerItem.getInstance().isUnlocked() &&
                !IceWizardItem.getInstance().isUnlocked()) {
            posX += stepX;
            ImageButton iceWizardButton = createIceWizardButton();
            iceWizardButton.setSize(buttonWidth, buttonHeight);
            iceWizardButton.setPosition(posX, posY);
            screen.getStage().addActor(iceWizardButton);
        }

        if (!GodModeItem.getInstance().isUnlocked() &&
                LightningWizardItem.getInstance().isUnlocked() &&
                FireWizardItem.getInstance().isUnlocked() &&
                IceWizardItem.getInstance().isUnlocked()) {
            posX += stepX;
            ImageButton godModeButton = createGodModeButton();
            godModeButton.setSize(buttonWidth, buttonHeight);
            godModeButton.setPosition(posX, posY);
            screen.getStage().addActor(godModeButton);
        }
    }

    private ImageButton createAutoClickButton() {
        final GameAutoClickerItem autoClickerItem = GameAutoClickerItem.getInstance();
        final ImageButton autoClickerButton = new ImageButton(new TextureRegionDrawable(autoClickerItem.getTexture()));

        autoClickerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getGame().getGameData().getMoney().compareTo(autoClickerItem.getPrice()) >= 0) {
                    screen.buyUnlockable(autoClickerItem);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockAutoClick();
                    autoClickerButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
                    }
                }
            }
        });

        autoClickerButton.addListener(new HoverClickListener(autoClickerItem));

        return autoClickerButton;
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
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                    GameOff2022.getInstance().allWizardsHealthUp(wizardHealthPowerItem);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                    GameOff2022.getInstance().getGameData().addAvailableWizard(LightningWizard.getInstance());
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.LIGHTNING);
                    lightningWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                    GameOff2022.getInstance().getGameData().addAvailableWizard(FireWizard.getInstance());
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.FIRE);
                    fireWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
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
                    GameOff2022.getInstance().getGameData().addAvailableWizard(IceWizard.getInstance());
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockWizard(WizardType.ICE);
                    iceWizardButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
                    }
                }
            }
        });

        iceWizardButton.addListener(new HoverClickListener(iceWizardItem));

        return iceWizardButton;
    }

    private ImageButton createGodModeButton() {
        final GodModeItem godModeItem = GodModeItem.getInstance();

        final ImageButton godModeButton = new ImageButton(new TextureRegionDrawable(godModeItem.getTexture()));
        godModeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (godModeItem.isUnlocked()) {
                    return;
                }

                if (screen.getGame().getGameData().getMoney().compareTo(godModeItem.getPrice()) >= 0) {
                    screen.buyUnlockable(godModeItem);
                    GameUpgrades.getInstance().unlockGodMode(true);
                    GameAchievements.getInstance().setCurrentStage(screen.getStage());
                    GameAchievements.getInstance().unlockGodMode();
                    godModeButton.setVisible(false);
                } else {
                    if (Math.random() < 0.5f) {
                        GameSoundPlayer.playSound(notEnoughLemonsSound);
                    } else {
                        GameSoundPlayer.playSound(youRequireMoreLemonsSound);
                    }
                }
            }
        });


        godModeButton.addListener(new HoverClickListener(godModeItem));
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
