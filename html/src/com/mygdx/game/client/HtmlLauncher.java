package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.FreetypeInjector;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.inject.OnCompletion;
import com.mygdx.game.domain.GameConstants;
import com.mygdx.game.domain.GameData;
import com.mygdx.game.GameOff2022;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
//                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                GwtApplicationConfiguration gwtApplicationConfiguration = new GwtApplicationConfiguration(GameConstants.WIDTH, GameConstants.HEIGHT, true);
                gwtApplicationConfiguration.padHorizontal = 0;
                gwtApplicationConfiguration.padVertical = 0;
                return gwtApplicationConfiguration;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return GameOff2022.getInstance();
        }

        @Override
        public void onModuleLoad () {
                FreetypeInjector.inject(new OnCompletion() {
                        public void run () {
                                // Replace HtmlLauncher with the class name
                                // If your class is called FooBar.java than the line should be FooBar.super.onModuleLoad();
                                HtmlLauncher.super.onModuleLoad();
                        }
                });
        }
}