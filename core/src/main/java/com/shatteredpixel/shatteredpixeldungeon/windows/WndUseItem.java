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

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.InventoryPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class WndUseItem extends WndInfoItem {

	private static final float BUTTON_HEIGHT	= 16;
	
	private static final float GAP	= 2;
	
	public WndUseItem( final Window owner, final Item item ) {
		
		super(item);

		float y = height;

		if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)) {
			y += GAP;
			ArrayList<RedButton> buttons = new ArrayList<>();
			for (final String action : item.actions( Dungeon.hero )) {

				RedButton btn = new RedButton( item.actionName(action, Dungeon.hero), 8 ) {
					@Override
					protected void onClick() {
						hide();
						if (owner != null && owner.parent != null) owner.hide();
						if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)){
							item.execute( Dungeon.hero, action );
						}
						Item.updateQuickslot();
						if (action == item.defaultAction && item.usesTargeting && owner == null){
							InventoryPane.useTargeting();
						}
					}
				};
				btn.setSize( btn.reqWidth(), BUTTON_HEIGHT );
				buttons.add(btn);
				add( btn );

				if (action.equals(item.defaultAction)) {
					btn.textColor( TITLE_COLOR );
				}

				boolean itemname = item instanceof EquipableItem || item instanceof Wand;
				if (itemname){
					Image renamebutton = Icons.get(Icons.RENAME_OFF);
					if (item instanceof EquipableItem) {
						if (!((EquipableItem) item).customName.equals("")) {
							renamebutton = Icons.get(Icons.RENAME_ON);
						}
					}
					if (item instanceof Wand) {
						if (!((Wand)item).customName.equals("")) {
							Icons.get(Icons.RENAME_ON);
						}
					}

					IconButton Rename = new IconButton(renamebutton) {
						public String hoverText() {
							return Messages.titleCase(Messages.get(WndGame.class, "rename"));
						}

						public void onClick() {
							GameScene.show(new WndTextInput(Messages.get(WndGame.class, "dialog"), Messages.get(WndGame.class, "dialog_title"), item.name(), 20, false, Messages.get(WndGame.class, "dialog_rename"), Messages.get(WndGame.class, "dialog_revert")) {
								public void onSelect(boolean name, String str) {
									if (name) {
										if (item instanceof EquipableItem) {
											((EquipableItem) item).customName = str;
										} else {
											((Wand) item).customName = str;
										}
									} else {
										if (item instanceof EquipableItem) {
											((EquipableItem) item).customName = "";
										} else  {
											((Wand) item).customName = "";
										}
									}

									WndUseItem.this.hide();
									GameScene.show(new WndUseItem(owner, item));
								}
							});
							icon(Icons.get(!item.name().equals("") ? Icons.RENAME_OFF : Icons.RENAME_ON));
						}
					};
					Rename.setRect((float)(super.width - 16), 0.0F, 16.0F, 16.0F);
					this.add(Rename);
				}

			}
			y = layoutButtons(buttons, width, y);
		}
		
		resize( width, (int)(y) );
	}

	private static float layoutButtons(ArrayList<RedButton> buttons, float width, float y){
		ArrayList<RedButton> curRow = new ArrayList<>();
		float widthLeftThisRow = width;
		
		while( !buttons.isEmpty() ){
			RedButton btn = buttons.get(0);
			
			widthLeftThisRow -= btn.width();
			if (curRow.isEmpty()) {
				curRow.add(btn);
				buttons.remove(btn);
			} else {
				widthLeftThisRow -= 1;
				if (widthLeftThisRow >= 0) {
					curRow.add(btn);
					buttons.remove(btn);
				}
			}
			
			//layout current row. Currently forces a max of 3 buttons but can work with more
			if (buttons.isEmpty() || widthLeftThisRow <= 0 || curRow.size() >= 3){
				
				//re-use this variable for laying out the buttons
				widthLeftThisRow = width - (curRow.size()-1);
				for (RedButton b : curRow){
					widthLeftThisRow -= b.width();
				}
				
				//while we still have space in this row, find the shortest button(s) and extend them
				while (widthLeftThisRow > 0){
					
					ArrayList<RedButton> shortest = new ArrayList<>();
					RedButton secondShortest = null;
					
					for (RedButton b : curRow) {
						if (shortest.isEmpty()) {
							shortest.add(b);
						} else {
							if (b.width() < shortest.get(0).width()) {
								secondShortest = shortest.get(0);
								shortest.clear();
								shortest.add(b);
							} else if (b.width() == shortest.get(0).width()) {
								shortest.add(b);
							} else if (secondShortest == null || secondShortest.width() > b.width()){
								secondShortest = b;
							}
						}
					}
					
					float widthToGrow;
					
					if (secondShortest == null){
						widthToGrow = widthLeftThisRow / shortest.size();
						widthLeftThisRow = 0;
					} else {
						widthToGrow = secondShortest.width() - shortest.get(0).width();
						if ((widthToGrow * shortest.size()) >= widthLeftThisRow){
							widthToGrow = widthLeftThisRow / shortest.size();
							widthLeftThisRow = 0;
						} else {
							widthLeftThisRow -= widthToGrow * shortest.size();
						}
					}
					
					for (RedButton toGrow : shortest){
						toGrow.setRect(0, 0, toGrow.width()+widthToGrow, toGrow.height());
					}
				}
				
				//finally set positions
				float x = 0;
				for (RedButton b : curRow){
					b.setRect(x, y, b.width(), b.height());
					x += b.width() + 1;
				}
				
				//move to next line and reset variables
				y += BUTTON_HEIGHT+1;
				widthLeftThisRow = width;
				curRow.clear();
				
			}
			
		}
		
		return y - 1;
	}

}
