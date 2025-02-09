package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class MetalCross extends MeleeWeapon {

    {
        image = ItemSpriteSheet.HOLYANKH;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;
        tier = 1;
        bones = false;
    }

    public int proc(Char attacker, Char defender, int damage ) {
        if (defender.HP <= damage){
            hero.earnExp( 1, getClass() );
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int min(int lvl) {
        return  1 + lvl;
    }

    @Override
    public int max(int lvl) {
        return  8 + lvl * 2;
    }

}

