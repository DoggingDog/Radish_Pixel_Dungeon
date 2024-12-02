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

package com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowBooks;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExoticScroll extends Scroll {


	public static final HashMap<Class<?extends Scroll>, Class<?extends ExoticScroll>> regToExo = new HashMap<>();
	public static final HashMap<Class<?extends ExoticScroll>, Class<?extends Scroll>> exoToReg = new HashMap<>();
	static{
		regToExo.put(ScrollOfIdentify.class, ScrollOfDivination.class);
		exoToReg.put(ScrollOfDivination.class, ScrollOfIdentify.class);

		regToExo.put(ScrollOfUpgrade.class, ScrollOfEnchantment.class);
		exoToReg.put(ScrollOfEnchantment.class, ScrollOfUpgrade.class);

		regToExo.put(ScrollOfRemoveCurse.class, ScrollOfAntiMagic.class);
		exoToReg.put(ScrollOfAntiMagic.class, ScrollOfRemoveCurse.class);

		regToExo.put(ScrollOfLullaby.class, ScrollOfSirensSong.class);
		exoToReg.put(ScrollOfSirensSong.class, ScrollOfLullaby.class);

		regToExo.put(ScrollOfRage.class, ScrollOfChallenge.class);
		exoToReg.put(ScrollOfChallenge.class, ScrollOfRage.class);

		regToExo.put(ScrollOfTerror.class, ScrollOfDread.class);
		exoToReg.put(ScrollOfDread.class, ScrollOfTerror.class);

		regToExo.put(ScrollOfRecharging.class, ScrollOfMysticalEnergy.class);
		exoToReg.put(ScrollOfMysticalEnergy.class, ScrollOfRecharging.class);

		regToExo.put(ScrollOfMagicMapping.class, ScrollOfForesight.class);
		exoToReg.put(ScrollOfForesight.class, ScrollOfMagicMapping.class);

		regToExo.put(ScrollOfTeleportation.class, ScrollOfPassage.class);
		exoToReg.put(ScrollOfPassage.class, ScrollOfTeleportation.class);

		regToExo.put(ScrollOfRetribution.class, ScrollOfPsionicBlast.class);
		exoToReg.put(ScrollOfPsionicBlast.class, ScrollOfRetribution.class);

		regToExo.put(ScrollOfMirrorImage.class, ScrollOfPrismaticImage.class);
		exoToReg.put(ScrollOfPrismaticImage.class, ScrollOfMirrorImage.class);

		regToExo.put(ScrollOfTransmutation.class, ScrollOfMetamorphosis.class);
		exoToReg.put(ScrollOfMetamorphosis.class, ScrollOfTransmutation.class);
	}

	public static HashMap<Class<? extends ExoticScroll>, Float> scrollChances = new HashMap<>();
	static{
		scrollChances.put( ScrollOfDivination.class,      3f );
		scrollChances.put( ScrollOfEnchantment.class,   2f );
		scrollChances.put( ScrollOfAntiMagic.class,  2f );
		scrollChances.put( ScrollOfSirensSong.class,   2f );
		scrollChances.put( ScrollOfChallenge.class,    2f );
		scrollChances.put( ScrollOfDread.class,       2f );
		scrollChances.put( ScrollOfMysticalEnergy.class,   2f );
		scrollChances.put( ScrollOfForesight.class,          2f );
		scrollChances.put( ScrollOfPassage.class, 2f );
		scrollChances.put( ScrollOfPsionicBlast.class,        2f );
		scrollChances.put( ScrollOfPrismaticImage.class, 1f );
		scrollChances.put( ScrollOfMetamorphosis.class, 1f );
	}

	@Override
	public void ShadowBooks(Hero hero){
		//确保是装备了 ShadowBooks
		if(hero.belongings.weapon instanceof ShadowBooks){
			ShadowBooks sos = (ShadowBooks) hero.belongings.weapon;
			//获取概率 成功进行
			// keptThoughLostInvent 检查如果未祝福十字架后是否存在 （即玩家是否保留）
			if(sos.aloneDoubleChance() && !sos.keptThoughLostInvent){
				ExoticScroll s = Reflection.newInstance(Random.chances(scrollChances));
				s.anonymize();
				curItem = s;
				sos.AloneChance *= 2;
				s.doRead();
			} else {
				//失败即可恢复为正常概率
				sos.AloneChance = 1;
			}
		}
	}

	@Override
	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( exoToReg.get(this.getClass()) ));
	}

	@Override
	public void setKnown() {
		if (!isKnown()) {
			handler.know(exoToReg.get(this.getClass()));
			updateQuickslot();
		}
	}

	@Override
	public void reset() {
		super.reset();
		if (handler != null && handler.contains(exoToReg.get(this.getClass()))) {
			image = handler.image(exoToReg.get(this.getClass())) + 16;
			rune = handler.label(exoToReg.get(this.getClass()));
		}
	}
	@Override
	public void MagicStone(boolean log,boolean original){
	}

	//实现法师恶魔天赋4-1 2级效果
	@Override
	public void ExoticScrollToScroll(boolean log,boolean original){
		if(original) {
			if (Dungeon.hero.pointsInTalent(Talent.MAGIC_REFINING) >= 2 && Random.Int(0,100)>=75) {
				Item scroll = Reflection.newInstance(exoToReg.get(curItem.getClass()));
				if(log) GLog.p(Messages.get(Scroll.class,"exscrolltoscroll",scroll.name()));
				Dungeon.level.drop(scroll, curUser.pos);
			}
		}
	}

	@Override
	//20 gold more than its none-exotic equivalent
	public int value() {
		return (Reflection.newInstance(exoToReg.get(getClass())).value() + 30) * quantity;
	}

	@Override
	//6 more energy than its none-exotic equivalent
	public int energyVal() {
		return (Reflection.newInstance(exoToReg.get(getClass())).energyVal() + 6) * quantity;
	}

	public static class ScrollToExotic extends Recipe {

		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			if (ingredients.size() == 1 && regToExo.containsKey(ingredients.get(0).getClass())){
				return true;
			}

			return false;
		}

		@Override
		public int cost(ArrayList<Item> ingredients) {
			return 6;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			for (Item i : ingredients){
				i.quantity(i.quantity()-1);
			}

			return Reflection.newInstance(regToExo.get(ingredients.get(0).getClass()));
		}

		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			return Reflection.newInstance(regToExo.get(ingredients.get(0).getClass()));
		}
	}
}
