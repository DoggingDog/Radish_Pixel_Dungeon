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

package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicStick;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PinCushion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ScrollEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.rector.FaithObstruction;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.talentitem.SpellQueue;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.WondrousResin;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.EndGuard;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Morello;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class Wand extends Item {

	public static final String AC_ZAP	= "ZAP";
	public String customName = "";

	public String name() {
		return this.customName.equals("") ? super.name() : this.customName;
	}

	private static final float TIME_TO_ZAP	= 1f;

	public int maxCharges = initialCharges();
	public int curCharges = maxCharges;
	public float partialCharge = 0f;

	protected Charger charger;

	public boolean curChargeKnown = false;

	//public boolean curseInfusionBonus = false;
	public int resinBonus = 0;

	private static final int USES_TO_ID = 10;
	private float usesLeftToID = USES_TO_ID;
	private float availableUsesToID = USES_TO_ID/2f;

	protected int collisionProperties = Ballistica.MAGIC_BOLT;
	public boolean isMagesStaff = false;

	public int spellSelected = 0;

	{
		defaultAction = AC_ZAP;
		usesTargeting = true;
		bones = true;
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (curCharges > 0 || !curChargeKnown) {
			actions.add( AC_ZAP );
		}

		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_ZAP )) {

			curUser = hero;
			curItem = this;
			GameScene.selectCell( zapper );

		}
	}

	@Override
	public int targetingPos(Hero user, int dst) {
		return new Ballistica( user.pos, dst, collisionProperties ).collisionPos;
	}

	public abstract void onZap(Ballistica attack);

	public abstract void onHit(MagesStaff staff, Char attacker, Char defender, int damage);

	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage , boolean isMagesStaff) {
		if(isMagesStaff){
			this.isMagesStaff = true;
		}
		onHit(staff, attacker,defender,damage);
	}

	//not affected by enchantment proc chance changers
	public static float procChanceMultiplier( Char attacker ){
		if (attacker.buff(Talent.EmpoweredStrikeTracker.class) != null){
			return 1f + ((Hero)attacker).pointsInTalent(Talent.EMPOWERED_STRIKE)/2f;
		}
		return 1f;
	}

	public boolean tryToZap( Hero owner, int target ){

		if (owner.buff(MagicImmune.class) != null){
			GLog.w( Messages.get(this, "no_magic") );
			return false;
		}

		if ( curCharges >= (cursed ? 1 : chargesPerCast())){
			return true;
		} else {
			GLog.w(Messages.get(this, "fizzles"));
			return false;
		}
	}

	@Override
	public boolean collect( Bag container ) {
		if (super.collect( container )) {
			if (container.owner != null) {
				if (container instanceof MagicalHolster)
					charge( container.owner, ((MagicalHolster) container).HOLSTER_SCALE_FACTOR );
				else
					charge( container.owner );
			}
			return true;
		} else {
			return false;
		}
	}

	public void gainCharge( float amt ){
		gainCharge( amt, false );
	}

	public void gainCharge( float amt, boolean overcharge ){
		partialCharge += amt;
		while (partialCharge >= 1) {
			if (overcharge) curCharges = Math.min(maxCharges+(int)amt, curCharges+1);
			else curCharges = Math.min(maxCharges, curCharges+1);
			partialCharge--;
			updateQuickslot();
		}
	}

	public void charge( Char owner ) {
		if (charger == null) charger = new Charger();
		charger.attachTo( owner );
	}

	public void charge( Char owner, float chargeScaleFactor ){
		charge( owner );
		charger.setScaleFactor( chargeScaleFactor );
	}

	protected void wandProc(Char target, int chargesUsed){
		wandProc(target, buffedLvl(), chargesUsed);
	}

	//TODO Consider externalizing char awareness buff
	protected static void wandProc(Char target, int wandLevel, int chargesUsed){
		if (hero.hasTalent(Talent.ARCANE_VISION)) {
			int dur = 5 + 5* hero.pointsInTalent(Talent.ARCANE_VISION);
			Buff.append(hero, TalismanOfForesight.CharAwareness.class, dur).charID = target.id();
		}

		if (target != hero &&
				hero.subClass == HeroSubClass.WARLOCK &&
				//standard 1 - 0.92^x chance, plus 7%. Starts at 15%
				Random.Float() > (Math.pow(0.92f, (wandLevel*chargesUsed)+1) - 0.07f)){
			SoulMark.prolong(target, SoulMark.class, SoulMark.DURATION + wandLevel);
		}
		
		if (hero.pointsInTalent(Talent.PHASE_FILLING)>2){
			if (target.buff(PinCushion.class) != null){

				Item item = target.buff(PinCushion.class).grabOne();

				if (item.doPickUpInstantly(hero, target.pos)) {
					GLog.i( Messages.capitalize(Messages.get(hero, "you_now_have", item.name())) );
				} else {
					GLog.w(Messages.get(TelekineticGrab.class, "cant_grab"));
					Dungeon.level.drop(item, target.pos).sprite.drop();
				}
			}
		}
	}

	@Override
	public void onDetach( ) {
		stopCharging();
	}

	public void stopCharging() {
		if (charger != null) {
			charger.detach();
			charger = null;
		}
	}

	public void level( int value) {
		super.level( value );
		updateLevel();
	}

	@Override
	public Item identify( boolean byHero ) {

		curChargeKnown = true;
		super.identify(byHero);

		updateQuickslot();

		return this;
	}

	public void onHeroGainExp( float levelPercent, Hero hero ){
		levelPercent *= Talent.itemIDSpeedFactor(hero, this);
		if (!isIdentified() && availableUsesToID <= USES_TO_ID/2f) {
			//gains enough uses to ID over 1 level
			availableUsesToID = Math.min(USES_TO_ID/2f, availableUsesToID + levelPercent * USES_TO_ID/2f);
		}
	}

	@Override
	public String info() {
		String desc = desc();

		desc += "\n\n" + statsDesc();

		if (resinBonus == 1){
			desc += "\n\n" + Messages.get(Wand.class, "resin_one");
		} else if (resinBonus > 1){
			desc += "\n\n" + Messages.get(Wand.class, "resin_many", resinBonus);
		}

		if (cursed && cursedKnown) {
			desc += "\n\n" + Messages.get(Wand.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			desc += "\n\n" + Messages.get(Wand.class, "not_cursed");
		}

		if (hero.subClass == HeroSubClass.BATTLEMAGE){
			desc += "\n\n" + Messages.get(this, "bmage_desc");
		}

		return desc;
	}

	public String statsDesc(){
		return Messages.get(this, "stats_desc");
	}

	@Override
	public boolean isIdentified() {
		return super.isIdentified() && curChargeKnown;
	}

	@Override
	public String status() {
		if (levelKnown) {
			return (curChargeKnown ? curCharges : "?") + "/" + maxCharges;
		} else {
			return null;
		}
	}

	@Override
	public int level() {
		if (!cursed && curseInfusionBonus){
			curseInfusionBonus = false;
			updateLevel();
		}
		int level = super.level();
		if (curseInfusionBonus) level += 1 + level/6;
		level += resinBonus;
		return level;
	}

	@Override
	public Item upgrade() {

		super.upgrade();

		if (Random.Int(3) == 0) {
			cursed = false;
		}

		if (resinBonus > 0){
			resinBonus--;
		}

		updateLevel();
		curCharges = Math.min( curCharges + 1, maxCharges );
		updateQuickslot();

		return this;
	}

	@Override
	public Item degrade() {
		super.degrade();

		updateLevel();
		updateQuickslot();

		return this;
	}

	@Override
	public int buffedLvl() {
		int lvl = super.buffedLvl();

		if(Dungeon.hero.buff(Berserk.class) != null){
			lvl += Dungeon.hero.buff(Berserk.class).WandBuffedLvl();
			updateQuickslot();
		}

		if (charger != null && charger.target != null) {
			if (charger.target.buff(WildMagic.WildMagicTracker.class) != null){
				int bonus = 4 + ((Hero)charger.target).pointsInTalent(Talent.WILD_POWER);
				if (Random.Int(2) == 0) bonus++;
				bonus /= 2; // +2/+2.5/+3/+3.5/+4 at 0/1/2/3/4 talent points

				int maxBonusLevel = 3 + ((Hero)charger.target).pointsInTalent(Talent.WILD_POWER);
				if (lvl < maxBonusLevel) {
					lvl = Math.min(lvl + bonus, maxBonusLevel);
				}
			}

			//inside staff, still need to apply degradation
			if (charger.target == Dungeon.hero
					&& !Dungeon.hero.belongings.contains(this)
					&& Dungeon.hero.buff( Degrade.class ) != null){
				lvl = Degrade.reduceLevel(lvl);
			}

			if (charger.target.buff(ScrollEmpower.class) != null){
				lvl += 3;
			}

			// Morello func
			// Date : 2024-07-08
			// By	: DoggingDog
			if(hero != null){
				Morello morello = hero.belongings.getItem(Morello.class);
				if(morello != null){
					updateLevel();
				}
			}

			WandOfMagicMissile.MagicCharge buff = charger.target.buff(WandOfMagicMissile.MagicCharge.class);
			if (buff != null && buff.level() > lvl && charger.target.buff(SpellQueue.tmpTracker.class)==null){
				return buff.level();
			}
		}
		return lvl;
	}

	public void updateLevel() {
		maxCharges = Math.min( initialCharges() + level(), 10 );

		// Morello func
		// Date : 2024-07-08
		// By	: DoggingDog
		if(charger != null){
			if(charger.target !=null){
				Morello.MorelloCabala morelloCabala = charger.target.buff(Morello.MorelloCabala.class);
				if (charger.target == hero && morelloCabala != null){
					morelloCabala.updateMaxCharge(hero);
					this.maxCharges += morelloCabala.buffCharge();
				}
			}
		}

		curCharges = Math.min( curCharges, maxCharges );
	}

	protected int initialCharges() {
		return 2;
	}

	protected int chargesPerCast() {
		return 1;
	}

	public void fx(Ballistica bolt, Callback callback) {
		MagicMissile.boltFromChar( curUser.sprite.parent,
				MagicMissile.MAGIC_MISSILE,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

	public void staffFx( MagesStaff.StaffParticle particle ){
		particle.color(0xFFFFFF); particle.am = 0.3f;
		particle.setLifespan( 1f);
		particle.speed.polar( Random.Float(PointF.PI2), 2f );
		particle.setSize( 1f, 2f );
		particle.radiateXY(0.5f);
	}
	public void spellUsed(){
		curCharges -= 2;
		if (hero.hasTalent(Talent.SPELL_QUEUE)){
			SpellQueue mySq= hero.belongings.getItem(SpellQueue.class);
			if (mySq!=null) mySq.updateImage();
			if (hero.pointsInTalent(Talent.SPELL_QUEUE)>2){
				partialCharge+=0.33f;
			}
		}
		Invisibility.dispel();
		updateQuickslot();
	}
	protected void wandUsed() {

		if(hero.hasTalent(Talent.MAGIC_STICK) && hero.pointsInTalent(Talent.MAGIC_STICK) >=4 && ! isMagesStaff){
			if(hero.buff(MagicStick.class) == null ){
				Buff.affect(hero , MagicStick.class, 2);
			}else{
				hero.buff(MagicStick.class).resetTime();
			}
		}

		if (!isIdentified()) {
			float uses = Math.min(availableUsesToID, Talent.itemIDSpeedFactor(hero, this));
			availableUsesToID -= uses;
			usesLeftToID -= uses;
			if (usesLeftToID <= 0 || hero.pointsInTalent(Talent.SCHOLARS_INTUITION) == 2) {
				identify();
				GLog.p(Messages.get(Wand.class, "identify"));
				Badges.validateItemLevelAquired(this);
			}
		}

		if (hero.belongings.weapon() instanceof EndGuard) {
			EndGuard w2 = (EndGuard) hero.belongings.weapon;
			if (w2 != null) {
				Buff.affect(hero, Barrier.class).setShield((int) (0.2f * ( w2.level() +1 )));
			}
		}

		curCharges -= cursed ? 1 : chargesPerCast();
		if (hero.hasTalent(Talent.SPELL_QUEUE)) {
			SpellQueue mySq = hero.belongings.getItem(SpellQueue.class);
			if (mySq != null) mySq.updateImage();
		}
		//remove magic charge at a higher priority, if we are benefiting from it are and not the
		//wand that just applied it
		WandOfMagicMissile.MagicCharge buff = curUser.buff(WandOfMagicMissile.MagicCharge.class);
		if (buff != null
				&& buff.wandJustApplied() != this
				&& buff.level() == buffedLvl()
				&& buffedLvl() > super.buffedLvl()
				&& curUser.buff(SpellQueue.tmpTracker.class) == null) {
			buff.detach();
		} else {
			ScrollEmpower empower = curUser.buff(ScrollEmpower.class);
			if (empower != null) {
				empower.use();
			}
		}

		if (charger != null
				&& charger.target == hero) {

			//if the wand is owned by the hero, but not in their inventory, it must be in the staff
			if (!hero.belongings.contains(this)) {
				if (curCharges == 0 && hero.hasTalent(Talent.BACKUP_BARRIER)) {
					if (hero.belongings.weapon() instanceof EndGuard) {
						EndGuard w2 = (EndGuard) hero.belongings.weapon;
						if (w2 != null) {
							//grants 3/5 shielding
							Buff.affect(hero, Barrier.class).setShield((int) (0.2f * ( w2.level() +1 )));
						}
					} else {
						//grants 3/5 shielding
						Buff.affect(hero, Barrier.class).setShield(1 + 2 * hero.pointsInTalent(Talent.BACKUP_BARRIER));
					}


				}
				if (hero.hasTalent(Talent.EMPOWERED_STRIKE)) {
					Buff.prolong(hero, Talent.EmpoweredStrikeTracker.class, 10f);
				}

				//otherwise process logic for metamorphed backup barrier
			} else if (curCharges == 0
					&& hero.heroClass != HeroClass.MAGE
					&& hero.hasTalent(Talent.BACKUP_BARRIER)) {
				boolean highest = true;
				for (Item i : hero.belongings.getAllItems(Wand.class)) {
					if (i.level() > level()) {
						highest = false;
					}
				}
				if (highest) {
					if (hero.belongings.weapon() instanceof EndGuard) {
						EndGuard w2 = (EndGuard) hero.belongings.weapon;
						if (w2 != null) {
							//grants 3/5 shielding
							Buff.affect(hero, Barrier.class).setShield((int) (0.2f * ( w2.level() +1 )));
						}
					} else {
						//grants 3/5 shielding
						Buff.affect(hero, Barrier.class).setShield(1 + 2 * hero.pointsInTalent(Talent.BACKUP_BARRIER));
					}

				}
			}
		}
		Invisibility.dispel();
		updateQuickslot();

		if (hero.pointsInTalent(Talent.PHASE_FILLING) > 3 && Random.Float() < 0.5f) {
			for (Item item : hero.belongings) {
				if (item instanceof MissileWeapon && ((MissileWeapon) item).durabilityLeft() < MissileWeapon.MAX_DURABILITY) {
					((MissileWeapon) item).repair(((MissileWeapon) item).durabilityPerUse());
					break;
				}
			}
		}

		float timeToZap = TIME_TO_ZAP;

		if (hero.hasTalent(Talent.DUEL_DANCE)) {
			if (hero.buff(Talent.DuelDanceWandTracker.class) != null) {
				Buff.detach(hero, Talent.DuelDanceWandTracker.class);
				timeToZap *= (0.84f - 0.17f * hero.pointsInTalent(Talent.DUEL_DANCE));
			}
		}

		if (hero.belongings.contains(this) && hero.hasTalent(Talent.MAGIC_TACTICS)) {
			int talentLevel = curUser.pointsInTalent(Talent.MAGIC_TACTICS);
			int chance = 7 * talentLevel + (int) Math.floor(talentLevel / 2f);
			if (Random.Int(100) <= chance)
				timeToZap = -hero.cooldown();
		}

		if( !(hero.buff(MagicStick.class)!=null && isMagesStaff)) {
			curUser.spendAndNext(timeToZap);
		}else{
			hero.buff(MagicStick.class).detach();
			curUser.spendAndNext(0);
		}

		if (hero.hasTalent(Talent.DUEL_DANCE) && hero.cooldown() >= 0)
			Buff.affect(hero, Talent.DuelDanceMissileTracker.class, hero.cooldown());

		if(hero.heroClass == HeroClass.RECTOR){
			FaithObstruction failed = Dungeon.hero.buff(FaithObstruction.class);
			if(failed == null){
				Buff.affect(curUser, FaithObstruction.class, FaithObstruction.DURATION);
			}
		}
	}

	@Override
	public Item random() {
		//+0: 66.67% (2/3)
		//+1: 26.67% (4/15)
		//+2: 6.67%  (1/15)
		int n = 0;
		if (Random.Int(3) == 0) {
			n++;
			if (Random.Int(5) == 0){
				n++;
			}
		}
		level(n);
		curCharges += n;

		//30% chance to be cursed
		if (Random.Float() < 0.3f) {
			cursed = true;
		}

		return this;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		if (resinBonus == 0) return null;

		return new ItemSprite.Glowing(0xFFFFFF, 1f/(float)resinBonus);
	}

	@Override
	public int value() {
		int price = 75;
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level() > 0) {
				price *= (level() + 1);
			} else if (level() < 0) {
				price /= (1 - level());
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	private static final String USES_LEFT_TO_ID     = "uses_left_to_id";
	private static final String AVAILABLE_USES      = "available_uses";
	private static final String CUR_CHARGES         = "curCharges";
	private static final String CUR_CHARGE_KNOWN    = "curChargeKnown";
	private static final String PARTIALCHARGE       = "partialCharge";
	private static final String CURSE_INFUSION_BONUS= "curse_infusion_bonus";
	private static final String RESIN_BONUS         = "resin_bonus";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( USES_LEFT_TO_ID, usesLeftToID );
		bundle.put( AVAILABLE_USES, availableUsesToID );
		bundle.put( CUR_CHARGES, curCharges );
		bundle.put( CUR_CHARGE_KNOWN, curChargeKnown );
		bundle.put( PARTIALCHARGE , partialCharge );
		bundle.put( CURSE_INFUSION_BONUS, curseInfusionBonus );
		bundle.put( RESIN_BONUS, resinBonus );
		bundle.put("spellselected",spellSelected);
		if (!this.customName.equals("")) {
			bundle.put("customName", this.customName);
		}
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		usesLeftToID = bundle.getInt( USES_LEFT_TO_ID );
		availableUsesToID = bundle.getInt( AVAILABLE_USES );
		curseInfusionBonus = bundle.getBoolean(CURSE_INFUSION_BONUS);
		resinBonus = bundle.getInt(RESIN_BONUS);

		updateLevel();

		curCharges = bundle.getInt( CUR_CHARGES );
		curChargeKnown = bundle.getBoolean( CUR_CHARGE_KNOWN );
		partialCharge = bundle.getFloat( PARTIALCHARGE );
		if (bundle.contains("spellselected")){
			spellSelected=bundle.getInt("spellselected");
		}
		if (bundle.contains("customName")) {
			this.customName = bundle.getString("customName");
		}
	}

	@Override
	public void reset() {
		super.reset();
		usesLeftToID = USES_TO_ID;
		availableUsesToID = USES_TO_ID/2f;
	}

	public int collisionProperties(int target){
		if (cursed)     return Ballistica.MAGIC_BOLT;
		else            return collisionProperties;
	}

	public static class PlaceHolder extends Wand {

		{
			image = ItemSpriteSheet.WAND_HOLDER;
		}

		@Override
		public boolean isSimilar(Item item) {
			return item instanceof Wand;
		}

		@Override
		public void onZap(Ballistica attack) {}
		public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {}



		@Override
		public String info() {
			return "";
		}
	}

	protected static CellSelector.Listener zapper = new  CellSelector.Listener() {

		@Override
		public void onSelect( Integer target ) {

			if (target != null) {

				//FIXME this safety check shouldn't be necessary
				//it would be better to eliminate the curItem static variable.
				final Wand curWand;
				if (curItem instanceof Wand) {
					curWand = (Wand) Wand.curItem;
				} else {
					return;
				}

				final Ballistica shot = new Ballistica( curUser.pos, target, curWand.collisionProperties(target));
				int cell = shot.collisionPos;

				if (target == curUser.pos || cell == curUser.pos) {
					if (target == curUser.pos && curUser.hasTalent(Talent.SHIELD_BATTERY)){
						float shield = curUser.HT * (0.04f*curWand.curCharges);
						if (curUser.pointsInTalent(Talent.SHIELD_BATTERY) == 2) shield *= 1.5f;

						if(hero.belongings.weapon() instanceof EndGuard) {
							EndGuard w2 = (EndGuard) hero.belongings.weapon;
							if (w2 != null) {
								Buff.affect(curUser, Barrier.class).setShield((int) (0.2f * ( w2.level() +1 )));
							}
						} else {
							Buff.affect(curUser, Barrier.class).setShield(Math.round(shield));
						}

						curWand.curCharges = 0;
						curUser.sprite.operate(curUser.pos);
						Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
						ScrollOfRecharging.charge(curUser);
						updateQuickslot();
						curUser.spend(Actor.TICK);
						return;
					}
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));

				if (curWand.tryToZap(curUser, target)) {

					curUser.busy();

					if (curWand.cursed){
						if (!curWand.cursedKnown){
							GLog.n(Messages.get(Wand.class, "curse_discover", curWand.name()));
						}
						CursedWand.cursedZap(curWand,
								curUser,
								new Ballistica(curUser.pos, target, Ballistica.MAGIC_BOLT),
								new Callback() {
									@Override
									public void call() {
										curWand.wandUsed();
									}
								});
					} else {
						curWand.fx(shot, new Callback() {
							public void call() {
								curWand.onZap(shot);
								if (Random.Float() < WondrousResin.extraCurseEffectChance()){
									WondrousResin.forcePositive = true;
									CursedWand.cursedZap(curWand,
											curUser,
											new Ballistica(curUser.pos, target, Ballistica.MAGIC_BOLT), new Callback() {
												@Override
												public void call() {
													WondrousResin.forcePositive = false;
													curWand.wandUsed();
												}
											});
								} else {
									curWand.wandUsed();
								}
							}
						});
					}
					curWand.cursedKnown = true;

				}

			}
		}

		@Override
		public String prompt() {
			return Messages.get(Wand.class, "prompt");
		}
	};

	public class Charger extends Buff {

		private static final float BASE_CHARGE_DELAY = 10f;
		private static final float SCALING_CHARGE_ADDITION = 40f;
		private static final float NORMAL_SCALE_FACTOR = 0.875f;

		private static final float CHARGE_BUFF_BONUS = 0.25f;

		float scalingFactor = NORMAL_SCALE_FACTOR;

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				//if we're loading in and the hero has partially spent a turn, delay for 1 turn
				if (now() == 0 && cooldown() == 0 && target.cooldown() > 0) spend(TICK);
				return true;
			}
			return false;
		}

		@Override
		public boolean act() {
			if (curCharges < maxCharges)
				recharge();

			while (partialCharge >= 1 && curCharges < maxCharges) {
				partialCharge--;
				curCharges++;
				updateQuickslot();
			}

			if (curCharges == maxCharges){
				partialCharge = 0;
			}

			spend( TICK );

			return true;
		}

		private void recharge(){
			int missingCharges = maxCharges - curCharges;
			missingCharges = Math.max(0, missingCharges);

			float turnsToCharge = (float) (BASE_CHARGE_DELAY
					+ (SCALING_CHARGE_ADDITION * Math.pow(scalingFactor, missingCharges)));

			LockedFloor lock = target.buff(LockedFloor.class);
			if (lock == null || lock.regenOn())
				partialCharge += (1f/turnsToCharge) * RingOfEnergy.wandChargeMultiplier(target);

			for (Recharging bonus : target.buffs(Recharging.class)){
				if (bonus != null && bonus.remainder() > 0f) {
					partialCharge += CHARGE_BUFF_BONUS * bonus.remainder();
				}
			}
		}

		public Wand wand(){
			return Wand.this;
		}

		public void gainCharge(float charge){
			if (curCharges < maxCharges) {
				partialCharge += charge;
				while (partialCharge >= 1f) {
					curCharges++;
					partialCharge--;
				}
				curCharges = Math.min(curCharges, maxCharges);
				updateQuickslot();
			}
		}

		private void setScaleFactor(float value){
			this.scalingFactor = value;
		}
	}

	@Override
	public void getCurse(boolean extraEffect) {
		super.getCurse(extraEffect);
		updateLevel();
	}

	public boolean readyToIdentify(){
		return !isIdentified() && usesLeftToID <= 0;
	}

}
