package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PneumFistGloves extends MeleeWeapon {

    {
        image = ItemSpriteSheet.PNEGLOVE_FIVE;
        tier = 3;
        DLY = 0.8f;
    }

    @Override
    public int min(int lvl) {
        return 4+lvl;
    }


    @Override
    public int max(int lvl) {
        return 15 + lvl * 3;
    }
}


