package com.shatteredpixel.shatteredpixeldungeon.actors.hero;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class StormAttackArrow extends MissileWeapon {

    {
        image = ItemSpriteSheet.SPIRIT_ARROW + 1;

        hitSound = Assets.Sounds.HIT_ARROW;
    }


    @Override
    public Emitter emitter() {
            return super.emitter();
    }

    @Override
    public int damageRoll(Char owner) {
        if(owner instanceof Hero){
            int lv = ((Hero) owner).pointsInTalent(Talent.STORM_ATTACK);
            if(lv % 2 == 0){
                SpiritBow bow = ((Hero) owner).belongings.getItem(SpiritBow.class);
                if(bow != null){
                    return bow.damageRoll(owner)/4;
                }
                else return 1;
            }
            else return 1;
        }
        else {
            return 1;
        }
    }

    @Override
    public float delayFactor(Char user) {
        return 0;
    }

    @Override
    public float accuracyFactor(Char owner, Char target) {
        return Float.POSITIVE_INFINITY;
    }

    @Override
    public int STRReq(int lvl) {
        return 0;
    }

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        if (enemy == null || enemy == curUser) {
            parent = null;
            Splash.at( cell, 0xCC99FFFF, 1 );
        } else {
            if (!curUser.shoot( enemy, this )) {
                Splash.at(cell, 0xCC99FFFF, 1);
            }
        }
    }

    @Override
    public void throwSound() {
        Sample.INSTANCE.play( Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f) );
    }

    int flurryCount = -1;
    Actor flurryActor = null;

    @Override
    public void cast(final Hero user, final int dst) {
        final int cell = throwPos( user, dst );
        if (user.pointsInTalent(Talent.STORM_ATTACK)-2>0){
            if (flurryCount == -1) flurryCount = 2;

            final Char enemy = Actor.findChar( cell );

            if (enemy == null){
                if (user.buff(Talent.LethalMomentumTracker.class) != null){
                    user.buff(Talent.LethalMomentumTracker.class).detach();
                    user.next();
                } else {
                    user.spendAndNext(castDelay(user, dst));
                }
                flurryCount = -1;

                if (flurryActor != null){
                    flurryActor.next();
                    flurryActor = null;
                }
                return;
            }

            QuickSlotButton.target(enemy);

            user.busy();

            throwSound();

            user.sprite.zap(cell);
            ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                    reset(user.sprite,
                            cell,
                            this,
                            new Callback() {
                                @Override
                                public void call() {
                                    if (enemy.isAlive()) {
                                        curUser = user;
                                        onThrow(cell);
                                    }

                                    flurryCount--;
                                    if (flurryCount > 0){
                                        Actor.add(new Actor() {

                                            {
                                                actPriority = VFX_PRIO-1;
                                            }

                                            @Override
                                            protected boolean act() {
                                                flurryActor = this;
                                                int target = QuickSlotButton.autoAim(enemy, StormAttackArrow.this);
                                                if (target == -1) target = cell;
                                                cast(user, target);
                                                Actor.remove(this);
                                                return false;
                                            }
                                        });
                                        curUser.next();
                                    } else {
                                        if (user.buff(Talent.LethalMomentumTracker.class) != null){
                                            user.buff(Talent.LethalMomentumTracker.class).detach();
                                            user.next();
                                        } else {
                                            user.spendAndNext(castDelay(user, dst));
                                        }
                                        flurryCount = -1;
                                    }

                                    if (flurryActor != null){
                                        flurryActor.next();
                                        flurryActor = null;
                                    }
                                }
                            });

        } else {

            if (user.hasTalent(Talent.SEER_SHOT)
                    && user.buff(Talent.SeerShotCooldown.class) == null){
                int shotPos = throwPos(user, dst);
                if (Actor.findChar(shotPos) == null) {
                    RevealedArea a = Buff.affect(user, RevealedArea.class, 5 * user.pointsInTalent(Talent.SEER_SHOT));
                    a.depth = Dungeon.depth;
                    a.branch = Dungeon.branch;
                    a.pos = shotPos;
                    Buff.affect(user, Talent.SeerShotCooldown.class, 20f);
                }
            }

            super.cast(user, dst);
        }
    }
}

