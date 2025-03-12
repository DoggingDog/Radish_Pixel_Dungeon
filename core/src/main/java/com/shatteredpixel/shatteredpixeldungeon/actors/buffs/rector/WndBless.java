package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.rector;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInfoItem;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;

public class WndBless extends Window {
    private static final int WIDTH		= 100;
    private static final int BTN_SIZE	= 32;
    private static final int BTN_GAP	= 6;
    private static final int GAP		= 6;

    public static Item S1;
    public static Item S2;
    public static Item S3;
    public static Item S4;

    public WndBless(Belief belief) {

        S1 = new RectorSkills.CORRECT();
        S2 = new RectorSkills.LIGHTIMUEE();
        S3 = new RectorSkills.CLEAN();
        S4 = new RectorSkills.PRAYERS();

        IconTitle titlebar = new IconTitle();
        titlebar.setRect(0, -3, WIDTH, 0);
        titlebar.icon(new BuffIcon(BuffIndicator.BLESS, true));
        titlebar.label(Messages.get(belief,"action_name"));
        add( titlebar );
        RenderedTextBlock message = PixelScene.renderTextBlock( (Messages.get(this,"select_skills")), 6 );
        message.maxWidth(WIDTH);
        message.setPos(0, titlebar.bottom() + GAP);
        add( message );

        RewardButton shop1 = new RewardButton( S1 );
        shop1.setRect( (WIDTH) / 2f - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE,
                BTN_SIZE );
        add( shop1 );

        RewardButton shop2 = new RewardButton( S2 );
        shop2.setRect( shop1.right(), shop1.top(), BTN_SIZE, BTN_SIZE );
        add(shop2);

        RewardButton shop3 = new RewardButton( S3 );
        shop3.setRect( shop1.left(), shop1.bottom(), BTN_SIZE, BTN_SIZE );
        add(shop3);

        RewardButton shop4 = new RewardButton( S4 );
        shop4.setRect( shop3.right(), shop2.bottom(), BTN_SIZE, BTN_SIZE );
        add(shop4);

        resize(WIDTH, (int) shop3.bottom());
    }

    private class RewardWindow extends WndInfoItem {

        public RewardWindow( Item item ) {
            super(item);
            Belief failed = Dungeon.hero.buff(Belief.class);
            StyledButton btnConfirm = new StyledButton(Chrome.Type.RED_BUTTON,Messages.get(this, "ac_ask")){
                @Override
                protected void onClick() {
                    if(item == S1){
                        if(failed != null){
                            failed.useSkills(Belief.SkillList.valueOf("CORRECT"));
                        }
                    } else if(item == S2){

                    } else {

                    }
                    hide();
                    RewardWindow.this.hide();
                }
            };
            btnConfirm.setRect(0, height+2, width, 16);
            add(btnConfirm);

            resize(width, (int)btnConfirm.bottom());
        }
    }

    public class RewardButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public RewardButton( Item item ){
            bg = Chrome.get( Chrome.Type.WINDOW);
            add( bg );

            slot = new ItemSlot( item ){
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().addToFront(new RewardWindow(item));
                }
            };
            add(slot);
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );

        }
    }
}
