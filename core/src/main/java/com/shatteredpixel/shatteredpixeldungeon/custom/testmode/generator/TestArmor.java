package com.shatteredpixel.shatteredpixeldungeon.custom.testmode.generator;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.AntiEntropy;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Bulk;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Displacement;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Metabolism;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Multiplicity;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Overgrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.curses.Stench;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Affection;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Brimstone;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Entanglement;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Flow;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Obfuscation;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Potential;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Repulsion;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.SkyWalker;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Stone;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Swiftness;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Thorns;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Viscosity;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.OptionSlider;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Objects;

public class TestArmor extends TestGenerator {
    {
        image = ItemSpriteSheet.ARMOR_HOLDER;
    }

    private int tier = 1;
    private boolean cursed = false;
    private int levelToGen = 0;
    private int enchant_id = 0;
    private int enchant_rarity = 0;
    private int armor_id = 0;

    @Override
    public ArrayList<String> actions(Hero hero) {
        return super.actions(hero);
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_GIVE)) {
            GameScene.show(new SettingsWindow());
        }
    }

    private Armor modifyArmor(Armor a) {
        a.level(levelToGen);
        Class<? extends Armor.Glyph> ench = generateEnchant(enchant_rarity, enchant_id);
        if (ench == null) {
            a.inscribe(null);
        } else {
            a.inscribe(Reflection.newInstance(ench));
        }
        a.cursed = cursed;
        return a;
    }

    private void createArmor(){
        Armor armor = Reflection.newInstance(armorList(tier)[armor_id]);
        armor = modifyArmor(armor);
        armor.identify();
        if(armor.collect()){
            GLog.i(Messages.get(this, "collect_success", armor.name()));
        }else{
            armor.doDrop(curUser);
        }
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("tier", tier);
        bundle.put("is_cursed", cursed);
        bundle.put("level_to_gen", levelToGen);
        bundle.put("enchant_rarity", enchant_rarity);
        bundle.put("enchant_id", enchant_id);
        bundle.put("armor_id", armor_id);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        tier = bundle.getInt("tier");
        cursed = bundle.getBoolean("is_cursed");
        levelToGen = bundle.getInt("level_to_gen");
        enchant_rarity = bundle.getInt("enchant_rarity");
        enchant_id = bundle.getInt("enchant_id");
        armor_id = bundle.getInt("armor_id");
    }

    private Class<? extends Armor.Glyph> generateEnchant(int category, int id) {
        if (category == 1) switch (id) {
            case 0:
                return Obfuscation.class;
            case 1:
                return Swiftness.class;
            case 2:
                return Viscosity.class;
            case 3:
                return Potential.class;
            case 4:
                return SkyWalker.class;
            default:
                return null;
        }
        else if (category == 2) switch (id) {
            case 0:
                return Stone.class;
            case 1:
                return Brimstone.class;
            case 2:
                return Entanglement.class;
            case 3:
                return Repulsion.class;
            case 4:
                return Camouflage.class;
            case 5:
                return Flow.class;
            default:
                return null;
        }
        else if (category == 3) switch (id) {
            case 0:
                return AntiMagic.class;
            case 1:
                return Thorns.class;
            case 2:
                return Affection.class;
            default:
                return null;
        }
        else if (category == 4) switch (id) {
            case 0:
                return AntiEntropy.class;
            case 1:
                return Bulk.class;
            case 2:
                return Corrosion.class;
            case 3:
                return Displacement.class;
            case 4:
                return Metabolism.class;
            case 5:
                return Multiplicity.class;
            case 6:
                return Overgrowth.class;
            case 7:
                return Stench.class;
            default:
                return null;
        }
        return null;
    }

    private Class<? extends Armor>[] armorList(int t) {
        switch (t) {
            case 1:
            default:
                return (Class<? extends Armor>[]) Generator.Category.ARM_T1.classes.clone();
            case 2:
                return (Class<? extends Armor>[]) Generator.Category.ARM_T2.classes.clone();
            case 3:
                return (Class<? extends Armor>[]) Generator.Category.ARM_T3.classes.clone();
            case 4:
                return (Class<? extends Armor>[]) Generator.Category.ARM_T4.classes.clone();
            case 5:
                return (Class<? extends Armor>[]) Generator.Category.ARM_T5_NATURE.classes.clone();
        }
    }

    private String currentEnchName(Class<? extends Armor.Glyph> ench) {
        if (enchant_rarity < 4)
            return currentEnchName(ench, Messages.get(Armor.Glyph.class, "glyph"));
        else
            return currentEnchName(ench, Messages.get(Item.class, "curse"));
    }

    private String currentEnchName(Class<? extends Armor.Glyph> ench, String armName) {
        return Messages.get(ench, "name", armName);
    }

    private int maxSlots(int t){
        switch (t){
            case 1:return 1;
            case 2: case 4:
            case 3: case 5: default:return 3;
        }
    }

    private class SettingsWindow extends Window {
        private static final int WIDTH = 120;
        private static final int BTN_SIZE = 20;
        private static final int GAP = 2;
        private static final int r_limit=5;
        private int t_row;
        private OptionSlider o_tier;
        private OptionSlider o_level;
        private OptionSlider o_enchant_rarity;
        private OptionSlider o_enchant_id;
        private CheckBox c_curse;
        private RenderedTextBlock t_selectedArmor;
        private RenderedTextBlock t_infoEnchant;
        private Class<? extends Armor>[] all;
        private ArrayList<IconButton> iconButtons = new ArrayList<IconButton>();
        private RedButton b_create;

        private void createArmorArray() {
            all = armorList(tier);
        }

        public SettingsWindow() {
            super();
            createArmorArray();
            o_tier = new OptionSlider(Messages.get(this, "tier"), "1", "5", 1, 5) {
                @Override
                protected void onChange() {
                    tier = getSelectedValue();
                    for(IconButton ib : iconButtons.toArray(new IconButton[0])){
                        ib.destroy();
                    }
                    createArmorArray();
                    createArmorImage(all);
                    t_row = (all.length-1)/r_limit+1;
                    updateSelectedArmorText();
                }
            };
            o_tier.setSelectedValue(tier);
            add(o_tier);
            o_tier.setRect(0, GAP, WIDTH, 24);
            //this is executed in layout because the pos of buttom is affected by the whole window.
            createArmorImage(all);
            t_row = (all.length-1)/r_limit+1;

            t_selectedArmor = PixelScene.renderTextBlock("", 6);
            t_selectedArmor.text(Messages.get(this, "selected", Messages.get(all[Math.min(armor_id, all.length-1)], "name")));
            t_selectedArmor.maxWidth(WIDTH);
            add(t_selectedArmor);

            o_level = new OptionSlider(Messages.get(this, "level"), "0", "12", 0, 12) {
                @Override
                protected void onChange() {
                    levelToGen = getSelectedValue();
                }
            };
            o_level.setSelectedValue(levelToGen);
            add(o_level);

            t_infoEnchant = PixelScene.renderTextBlock("", 6);
            t_infoEnchant.text(enchantDesc());
            add(t_infoEnchant);

            o_enchant_rarity = new OptionSlider(Messages.get(this, "enchant_rarity"), "0", "4", 0, 4) {
                @Override
                protected void onChange() {
                    enchant_rarity = getSelectedValue();
                    updateEnchantText();
                }
            };
            o_enchant_rarity.setSelectedValue(enchant_rarity);
            add(o_enchant_rarity);

            o_enchant_id = new OptionSlider(Messages.get(this, "enchant_id"), "0", "7", 0, 7) {
                @Override
                protected void onChange() {
                    enchant_id = getSelectedValue();
                    updateEnchantText();
                }
            };
            o_enchant_id.setSelectedValue(enchant_id);
            add(o_enchant_id);

            c_curse = new CheckBox(Messages.get(this, "curse")) {
                @Override
                protected void onClick() {
                    super.onClick();
                    cursed = checked();
                }
            };
            c_curse.checked(cursed);
            add(c_curse);

            b_create = new RedButton(Messages.get(this, "create_button")) {
                @Override
                protected void onClick() {
                    createArmor();
                }
            };
            add(b_create);

            layout();
        }

        private void createArmorImage(Class<? extends Armor>[] all) {
            float left;
            float top = o_tier.bottom() + GAP;
            int placed = 0;
            int length = all.length;
            t_row = (length-1)/r_limit+1;
            for (int i = 0; i < length; ++i) {
                final int j = i;
                IconButton btn = new IconButton() {
                    @Override
                    protected void onClick() {
                        armor_id = Math.min(maxSlots(tier)-1, j);
                        updateSelectedArmorText();
                        super.onClick();
                    }
                };
                Image im = new Image(Assets.Sprites.ITEMS);
                im.frame(ItemSpriteSheet.film.get(Objects.requireNonNull(Reflection.newInstance(all[i])).image));
                im.scale.set(1f);
                btn.icon(im);
                left = (WIDTH - BTN_SIZE * ((i/r_limit+1<t_row)?r_limit:length-(t_row-1)*r_limit) )/2f;
                btn.setRect(left+(placed-r_limit*(i/r_limit))*BTN_SIZE,top+GAP*(i/r_limit)+BTN_SIZE*(i/r_limit),BTN_SIZE,BTN_SIZE);

                add(btn);
                placed++;
                iconButtons.add(btn);
            }
        }

        private void layout() {
            o_tier.setRect(0, GAP, WIDTH, 24);
            //createWeaponImage(all);
            t_selectedArmor.setPos(0, GAP * 2 + o_tier.bottom() + BTN_SIZE*t_row);
            o_level.setRect(0, t_selectedArmor.bottom() + GAP, WIDTH, 24);
            t_infoEnchant.setPos(0, GAP + o_level.bottom());
            o_enchant_rarity.setRect(0, GAP + t_infoEnchant.bottom(), WIDTH, 24);
            o_enchant_id.setRect(0, GAP + o_enchant_rarity.bottom(), WIDTH, 24);
            c_curse.setRect(0, GAP + o_enchant_id.bottom(), WIDTH/2f - GAP/2f, 16);
            b_create.setRect(WIDTH/2f+GAP/2f, c_curse.top(), WIDTH/2f - GAP/2f, 16);
            resize(WIDTH, (int) (c_curse.bottom() + GAP));
        }

        private void updateSelectedArmorText() {
            t_selectedArmor.text(Messages.get(this, "selected", Messages.get(all[Math.min(armor_id, all.length-1)], "name")));
            layout();
        }

        private void updateEnchantText() {
            t_infoEnchant.text(enchantDesc());
            layout();
        }

        private String enchantDesc() {
            //String desc = Messages.get(BossRushMelee.class, "enchant_id_pre", enchant_rarity);
            String desc = "";
            String key = "enchant_id_e" + String.valueOf(enchant_rarity);
            Class<? extends Armor.Glyph> ench = generateEnchant(enchant_rarity, enchant_id);
            desc += Messages.get(TestArmor.class, key, (ench == null ? Messages.get(TestArmor.class, "null_enchant") : currentEnchName(ench)));
            return desc;
        }
    }
}
