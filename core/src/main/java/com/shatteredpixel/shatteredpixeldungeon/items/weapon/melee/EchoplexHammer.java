package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class EchoplexHammer extends MeleeWeapon {

    {
        image = ItemSpriteSheet.CALLHAMR;
        tier = 5;
    }

    @Override
    public int STRReq(int lvl) {
        return (9 + tier * 2) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
    }

    @Override
    public int min(int lvl) {
        return 10 + lvl * 2;
    }
    @Override
    public int max(int lvl) {
        return 30 + lvl * 5;
    }

    public int proc(Char attacker, Char defender, int damage ) {
        if (defender.HP <= damage){
            KillEffect(this);
        }
        return super.proc(attacker, defender, damage);
    }

    public static void KillEffect( Weapon weapon ){
        Sample.INSTANCE.play( Assets.Sounds.DEGRADE );

        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment == Char.Alignment.ENEMY && Dungeon.level.heroFOV[mob.pos]) {
                CellEmitter.center( mob.pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
                mob.damage(10 + 2 * weapon.level() , weapon );
            }
        }
    }

    @Override
    public String desc() {

        String desc;

        if(isIdentified()){
            desc = Messages.get(this, "desc",10 + 2 * level());
        } else {
            desc = Messages.get(this, "normal_desc",10);
        }

        return desc;
    }

}
