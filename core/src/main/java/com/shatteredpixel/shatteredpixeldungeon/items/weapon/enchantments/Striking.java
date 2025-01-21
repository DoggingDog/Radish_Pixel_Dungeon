package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

/*
    Class Name  : Striking
    Description : Weapon enchantment 4 Radish Dungeon
    Created by DoggingDog on 2024_10_05
*/
public class Striking extends Weapon.Enchantment {

    private static ItemSprite.Glowing STRINGKING = new ItemSprite.Glowing( 0xB8860B );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {

        int level = Math.max( 0, weapon.buffedLvl() );
        float procChance = 0.15f + level * 0.03f * procChanceMultiplier(attacker);
        float paralysisDuration = (4f + level) * Math.max(procChance,1f);

        if(Random.Float() < procChance){
            Buff.prolong(defender, Paralysis.class,paralysisDuration);
            defender.sprite.emitter().burst(Speck.factory(Speck.STAR), Random.Int(10,20));
        }

        return damage;
    }
    @Override
    public ItemSprite.Glowing glowing() {
        return STRINGKING;
    }

}
