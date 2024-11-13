package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class Seeking extends Weapon.Enchantment {
    private static ItemSprite.Glowing SEEKING = new ItemSprite.Glowing( 0xF0FFF0 );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        Buff.affect(defender, SeekingBuff.class);
        return damage;
    }
    @Override
    public ItemSprite.Glowing glowing() {
        return SEEKING;
    }

    public static class SeekingBuff extends Buff{
        {
            type = buffType.NEGATIVE;
            announced = true;
        }
        @Override
        public int icon() {
            return BuffIndicator.FORESIGHT;
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

    }
}
