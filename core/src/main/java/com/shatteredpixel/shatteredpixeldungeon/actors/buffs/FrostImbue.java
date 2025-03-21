/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SnowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfBenediction;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class FrostImbue extends FlavourBuff {
	
	{
		type = buffType.POSITIVE;
		announced = true;
	}
	
	public static final float DURATION	= 50f;

	public void proc(Char enemy){
		float chill_time=2f;
		if (target == Dungeon.hero){
			Buff ben=Dungeon.hero.buff(RingOfBenediction.Benediction.class);
			if (ben!=null){
				chill_time*=RingOfBenediction.periodMultiplier(target);
			}
		}
		Buff.affect(enemy, Chill.class, chill_time);
		enemy.sprite.emitter().burst( SnowParticle.FACTORY, 2 );
	}
	
	@Override
	public int icon() {
		return BuffIndicator.IMBUE;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0, 2f, 3f);
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}
	
	{
		immunities.add( Frost.class );
		immunities.add( Chill.class );
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)){
			Buff.detach(target, Frost.class);
			Buff.detach(target, Chill.class);
			return true;
		} else {
			return false;
		}
	}
}
