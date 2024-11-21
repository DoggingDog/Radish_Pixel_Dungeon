package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LongStick extends MeleeWeapon {

    //TODO 待添加
    {
        image = ItemSpriteSheet.LONG_STARK;
        tier = 3;
        ACC = 1;
        DLY = 1;
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


