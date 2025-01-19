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

package com.shatteredpixel.shatteredpixeldungeon.items.scrolls;

import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll.ScrollToStone.stones;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll.exoToReg;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll.regToExo;
import static com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst.scrollChances;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ItemStatusHandler;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAggression;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfClairvoyance;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfDeepSleep;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfDisarming;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfFear;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfIntuition;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfShock;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowBooks;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public abstract class Scroll extends Item {
	//affects how strongly on-scroll talents trigger from this scroll
	protected float talentFactor = 1;
	//the chance (0-1) of whether on-scroll talents trigger from this potion
	protected float talentChance = 1;
	public static final String AC_READ	= "READ";

	protected static final float TIME_TO_READ	= 1f;
	public static void clearLabels(){
		handler = null;
	}
	private static final LinkedHashMap<String, Integer> runes = new LinkedHashMap<String, Integer>() {
		{
			put("KAUNAN",ItemSpriteSheet.SCROLL_KAUNAN);
			put("SOWILO",ItemSpriteSheet.SCROLL_SOWILO);
			put("LAGUZ",ItemSpriteSheet.SCROLL_LAGUZ);
			put("YNGVI",ItemSpriteSheet.SCROLL_YNGVI);
			put("GYFU",ItemSpriteSheet.SCROLL_GYFU);
			put("RAIDO",ItemSpriteSheet.SCROLL_RAIDO);
			put("ISAZ",ItemSpriteSheet.SCROLL_ISAZ);
			put("MANNAZ",ItemSpriteSheet.SCROLL_MANNAZ);
			put("NAUDIZ",ItemSpriteSheet.SCROLL_NAUDIZ);
			put("BERKANAN",ItemSpriteSheet.SCROLL_BERKANAN);
			put("ODAL",ItemSpriteSheet.SCROLL_ODAL);
			put("TIWAZ",ItemSpriteSheet.SCROLL_TIWAZ);
		}
	};

	protected static ItemStatusHandler<Scroll> handler;

	protected String rune;

	{
		stackable = true;
		defaultAction = AC_READ;
	}

	@SuppressWarnings("unchecked")
	public static void initLabels() {
		handler = new ItemStatusHandler<>( (Class<? extends Scroll>[])Generator.Category.SCROLL.classes, runes );
	}

	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		ArrayList<Class<?extends Item>> classes = new ArrayList<>();
		for (Item i : items){
			if (i instanceof ExoticScroll){
				if (!classes.contains(exoToReg.get(i.getClass()))){
					classes.add(exoToReg.get(i.getClass()));
				}
			} else if (i instanceof Scroll){
				if (!classes.contains(i.getClass())){
					classes.add(i.getClass());
				}
			}
		}
		handler.saveClassesSelectively( bundle, classes );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<>( (Class<? extends Scroll>[])Generator.Category.SCROLL.classes, runes, bundle );
	}

	public Scroll() {
		super();
		reset();
	}

	//anonymous scrolls are always IDed, do not affect ID status,
	//and their sprite is replaced by a placeholder if they are not known,
	//useful for items that appear in UIs, or which are only spawned for their effects
	protected boolean anonymous = false;
	public void anonymize(){
		if (!isKnown()) image = ItemSpriteSheet.SCROLL_HOLDER;
		anonymous = true;
	}


	@Override
	public void reset(){
		super.reset();
		if (handler != null && handler.contains(this)) {
			image = handler.image(this);
			rune = handler.label(this);
		}
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_READ );
		return actions;
	}

	/**
	 * 重写获得更好的维护<br>
	 * Author:JDSALing<br>
	 * 奥术精炼-T4-1实现<br>
	 * @param log 消息输出
	 * @param original 原始输出
	 */
	public void MagicStone(boolean log,boolean original){
		if(original) {
			boolean isNotDouble = !(curItem instanceof ScrollOfRemoveCurse || curItem instanceof ScrollOfUpgrade || curItem instanceof ScrollOfIdentify || curItem instanceof ScrollOfTransmutation);
			if (Dungeon.hero.pointsInTalent(Talent.MAGIC_REFINING) >= 1 && isNotDouble && Random.Int(0,100)>=50) {
				Item MagicStone = Reflection.newInstance(stones.get(curItem.getClass()));
				if(log)GLog.p(Messages.get(Scroll.class,"scrollToStone",MagicStone.name()));
				Dungeon.level.drop(MagicStone, curUser.pos);
			}
		}
	}

	public void ShadowBooks(Hero hero){
		//确保是装备了 ShadowBooks
		if(hero.belongings.weapon instanceof ShadowBooks){
			ShadowBooks sos = (ShadowBooks) hero.belongings.weapon;
			//获取概率 成功进行
			// keptThoughLostInvent 检查如果未祝福十字架后是否存在 （即玩家是否保留）
			if(sos.aloneToChance() && !sos.keptThoughLostInvent){
				Scroll s = Reflection.newInstance(Random.chances(scrollChances));
				s.anonymize();
				sos.AloneChance *= 2;
				curItem = s;
				s.doRead();
			} else {
				//失败即可恢复为正常概率
				sos.AloneChance = 1;
			}
		}
	}

	public void ShadowExBooks(Hero hero){
		//确保是装备了 ShadowBooks
		if(hero.belongings.weapon instanceof ShadowBooks){
			ShadowBooks sos = (ShadowBooks) hero.belongings.weapon;
			//获取概率 成功进行
			// keptThoughLostInvent 检查如果未祝福十字架后是否存在 （即玩家是否保留）
			if(sos.aloneToChance() && !sos.keptThoughLostInvent){
				Scroll s= Reflection.newInstance(regToExo.get(this.getClass()));
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
	public void execute( Hero hero, String action ) {
		super.execute( hero, action );
		if (action.equals( AC_READ )) {
			if (hero.buff(MagicImmune.class) != null){
				GLog.w( Messages.get(this, "no_magic") );
			} else if (hero.buff( Blindness.class ) != null) {
				GLog.w( Messages.get(this, "blinded") );
			} else if (hero.buff(UnstableSpellbook.bookRecharge.class) != null
					&& hero.buff(UnstableSpellbook.bookRecharge.class).isCursed()
					&& !(this instanceof ScrollOfRemoveCurse || this instanceof ScrollOfAntiMagic)){
				GLog.n( Messages.get(this, "cursed") );
			} else {
				MagicStone(true,true);
				ExoticScrollToScroll(true,true);
				curUser = hero;
				ShadowBooks(hero);
				//curItem = detach( hero.belongings.backpack );
				doRead();
			}

		}
	}

	public abstract void doRead();

	protected void readAnimation(){
		readAnimation(false);
	}
	protected void readAnimation( boolean toDouble) {
		Invisibility.dispel();
		curUser.spend( TIME_TO_READ );
		curUser.busy();
		((HeroSprite)curUser.sprite).read();

		/*if (curUser.hasTalent(Talent.SPELL_QUEUE)){
			Buff.affect(curUser, ScrollEmpower.class).reset();
			updateQuickslot();
		}*/
		if (curUser.hasTalent(Talent.ENERGIZING_UPGRADE)){
			for (Wand.Charger c : curUser.buffs(Wand.Charger.class)){
				c.gainCharge((toDouble?2:1)*(0.34f+0.33f*curUser.pointsInTalent(Talent.ENERGIZING_UPGRADE)));
			}
		}

	}

	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( this ));
	}

	public void setKnown() {
		if (!anonymous) {
			if (!isKnown()) {
				handler.know(this);
				updateQuickslot();
			}

			if (Dungeon.hero.isAlive()) {
				Catalog.setSeen(getClass());
			}
		}
	}

	@Override
	public Item identify( boolean byHero ) {
		super.identify(byHero);

		if (!isKnown()) {
			setKnown();
		}
		return this;
	}

	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(this, rune);
	}

	@Override
	public String info() {
		//skip custom notes if anonymized and un-Ided
		return (anonymous && (handler == null || !handler.isKnown( this ))) ? desc() : super.info();
	}

	@Override
	public String desc() {
		return isKnown() ? super.desc() : Messages.get(this, "unknown_desc");
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return isKnown();
	}

	public static HashSet<Class<? extends Scroll>> getKnown() {
		return handler.known();
	}

	public static HashSet<Class<? extends Scroll>> getUnknown() {
		return handler.unknown();
	}

	public static boolean allKnown() {
		return handler.known().size() == Generator.Category.SCROLL.classes.length;
	}

	//实现法师恶魔天赋4-1 2级效果
	public void ExoticScrollToScroll(boolean log, boolean original) {
	}


	@Override
	public int value() {
		return 30 * quantity;
	}

	@Override
	public int energyVal() {
		return 6 * quantity;
	}

	public static class PlaceHolder extends Scroll {

		{
			image = ItemSpriteSheet.SCROLL_HOLDER;
		}

		@Override
		public boolean isSimilar(Item item) {
			return regToExo.containsKey(item.getClass())
					|| regToExo.containsValue(item.getClass());
		}

		@Override
		public void doRead() {}

		@Override
		public String info() {
			return "";
		}
	}

	public static class ScrollToStone extends Recipe {

		protected static HashMap<Class<?extends Scroll>, Class<?extends Runestone>> stones = new HashMap<>();
		static {
			stones.put(ScrollOfIdentify.class,      StoneOfIntuition.class);
			stones.put(ScrollOfLullaby.class,       StoneOfDeepSleep.class);
			stones.put(ScrollOfMagicMapping.class,  StoneOfClairvoyance.class);
			stones.put(ScrollOfMirrorImage.class,   StoneOfFlock.class);
			stones.put(ScrollOfRetribution.class,   StoneOfBlast.class);
			stones.put(ScrollOfRage.class,          StoneOfAggression.class);
			stones.put(ScrollOfRecharging.class,    StoneOfShock.class);
			stones.put(ScrollOfRemoveCurse.class,   StoneOfDisarming.class);
			stones.put(ScrollOfTeleportation.class, StoneOfBlink.class);
			stones.put(ScrollOfTerror.class,        StoneOfFear.class);
			stones.put(ScrollOfTransmutation.class, StoneOfAugmentation.class);
			stones.put(ScrollOfUpgrade.class,       StoneOfEnchantment.class);
		}

		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			if (ingredients.size() != 1
					|| !(ingredients.get(0) instanceof Scroll)
					|| !stones.containsKey(ingredients.get(0).getClass())){
				return false;
			}

			return true;
		}

		@Override
		public int cost(ArrayList<Item> ingredients) {
			return 0;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;

			Scroll s = (Scroll) ingredients.get(0);

			s.quantity(s.quantity() - 1);
			s.identify();

			return Reflection.newInstance(stones.get(s.getClass())).quantity(2);
		}

		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;

			Scroll s = (Scroll) ingredients.get(0);

			if (!s.isKnown()){
				return new Runestone.PlaceHolder().quantity(2);
			} else {
				return Reflection.newInstance(stones.get(s.getClass())).quantity(2);
			}
		}
	}
}
