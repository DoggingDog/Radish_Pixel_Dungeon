package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.rector;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.utils.Bundle;

public class Belief extends Buff implements ActionIndicator.Action {

    public float credibility;
    public boolean not_link = false;

    {
        type = buffType.POSITIVE;
        revivePersists = true;
    }

    public enum SkillList {
        CORRECT, LIGHTIMUEE, CLEAN, PRAYERS;
        public String desc(){
            switch (this){
                case CORRECT:
                case LIGHTIMUEE:
                case CLEAN:
                case PRAYERS:
                    return Messages.get(this, name()+"desc");
                default:
                    return Messages.get(this, desc());
            }
        }
        public String title(){
            return Messages.get(this, name() + ".name");
        }

        public int getImage() {
            switch (this) {
                case CORRECT:
                    return ItemSpriteSheet.CORRECT;
                case LIGHTIMUEE:
                    return ItemSpriteSheet.LIGHTIMUEE;
                case CLEAN:
                    return ItemSpriteSheet.CLEAN;
                case PRAYERS:
                    return ItemSpriteSheet.PRAYERS;
                default:
                    return 0;
            }
        }
    }

    public void useSkills(Belief.SkillList skillList){
        switch (skillList){
            case CORRECT:
                Buff.affect(hero, Healing.class).setHeal((int) (0.8f * hero.HT + 14), 0.25f, 0);
                break;
            case LIGHTIMUEE:
                Buff.affect(hero, Healing.class).setHeal((int) (0.6f * hero.HT + 14), 0.25f, 0);
                break;
            case CLEAN:
                Buff.affect(hero, Healing.class).setHeal((int) (0.4f * hero.HT + 14), 0.25f, 0);
                break;
            case PRAYERS:
                Buff.affect(hero, Healing.class).setHeal((int) (0.2f * hero.HT + 14), 0.25f, 0);
                break;
            default:
                break;
        }
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
        credibility += (float) (Math.floor(value * 100) / 100);
        hero.sprite.showStatusWithIcon(Window.TITLE_COLOR, String.valueOf(value), FloatingText.BELIEF);
    }

    @Override
    public String iconTextDisplay() {
        return String.valueOf(Math.floor(credibility * 100) / 100);
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
        return Messages.get(this, "desc",Math.floor(credibility * 100) / 100);
    }

    @Override
    public int icon() {
        return not_link ? BuffIndicator.NONE : BuffIndicator.BELIEF_LINK;
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.BLESS;
    }

    @Override
    public int indicatorColor() {
        if(credibility>=20){
            return Window.TITLE_COLOR;
        } else if(credibility>=15){
            return Window.SHPX_COLOR;
        } else if(credibility>=12){
            return 0x99ccbb;
        } else {
            return 0x55AAFF;
        }
    }

    @Override
    public void doAction() {
        GameScene.show(new WndBless(this));
    }
}
