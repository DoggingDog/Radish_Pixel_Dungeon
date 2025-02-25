package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.rector;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.utils.Bundle;

public class Belief extends Buff implements ActionIndicator.Action {

    public float credibility;
    public boolean not_link = false;

    {
        type = buffType.POSITIVE;
        revivePersists = true;
    }

    @Override
    public boolean act() {
        FaithObstruction failed = Dungeon.hero.buff(FaithObstruction.class);
        if (credibility >= 5.0f && failed == null){
            ActionIndicator.setAction(this);
        }
        not_link = failed != null;
        spend(TICK);
        return true;
    }

    public void getBelief(float value) {
        credibility += value;
        hero.sprite.showStatusWithIcon(Window.TITLE_COLOR, Integer.toString(1), FloatingText.BELIEF);
    }

    public static String CREDIBILITY = "credibility";
    public static String NOT_LINK = "not_link";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CREDIBILITY, credibility);
        bundle.put(NOT_LINK, not_link);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        credibility = bundle.getFloat(CREDIBILITY);
        not_link    = bundle.getBoolean(NOT_LINK);

        if (credibility>5 && !not_link){
            ActionIndicator.setAction(this);
        }
    }

    @Override
    public String desc(){
        return Messages.get(this, "desc",credibility);
    }

    @Override
    public int icon() {
        return not_link ? BuffIndicator.NONE : BuffIndicator.BELIEF_LINK;
    }

    @Override
    public String actionName() {
        return "";
    }

    @Override
    public int indicatorColor() {
        return 0;
    }

    @Override
    public void doAction() {

    }
}
