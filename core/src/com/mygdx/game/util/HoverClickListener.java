package com.mygdx.game.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.domain.Item;

public class HoverClickListener extends ClickListener {
    private Item item;
    private HoverFactory hoverFactory = HoverFactory.getInstance();

    public HoverClickListener(Item item) {
        this.item = item;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);

        ImageButton imageButton = (ImageButton) event.getTarget().getParent();
        Hover hover = hoverFactory.create(item, imageButton);
        event.getStage().addActor(hover);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);

        Array<Actor> actors = event.getStage().getActors();
        int removeIndex = -1;
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            if (actor instanceof Hover) {
                removeIndex = i;
                break;
            }
        }
        actors.removeIndex(removeIndex);
    }
}
