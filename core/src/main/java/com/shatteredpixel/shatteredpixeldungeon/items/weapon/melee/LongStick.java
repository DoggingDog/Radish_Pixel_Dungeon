package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LongStick extends MeleeWeapon {

    {
        image = ItemSpriteSheet.LONG_STARK;
        tier = 3;
        ACC = 1 + Dungeon.hero.defenseSkill(Dungeon.hero);
        DLY = 1 / ((float) Dungeon.hero.defenseSkill(Dungeon.hero)/5);
    }

    @Override
    public int min(int lvl) {
        return 4+lvl;
    }

    @Override
    public int max(int lvl) {
        return 20 + lvl * 4;
    }

}


