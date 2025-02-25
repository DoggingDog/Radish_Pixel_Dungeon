package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class MetalCross extends MeleeWeapon {


    int count = 0;

    {
        count = Math.round(2.5f);
        image = ItemSpriteSheet.HOLYANKH;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;
        tier = 1;
        bones = false;
    }
//        Mob n = (Mob) defender;
//        boolean exp = Dungeon.hero.lvl <= n.maxLvl;
//
//        if (defender.HP <= damage){
//            if(exp){
//                hero.earnExp( 1, getClass() );
//                hero.sprite.showStatusWithIcon(0x00ffff, Integer.toString(1), FloatingText.EX_EXP);
//            }
//        }
//        else{
    public int proc(Char attacker, Char defender, int damage ) {

        Buff.prolong(defender, Terror.class, 1f + (float) Dungeon.depth /5);
        Buff.affect( defender, Burning.class ).reignite( defender, 50f+ (float) Dungeon.hero.HP /4 );
        Buff.affect( defender, Vertigo.class, 2 + Dungeon.hero.STR);


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

