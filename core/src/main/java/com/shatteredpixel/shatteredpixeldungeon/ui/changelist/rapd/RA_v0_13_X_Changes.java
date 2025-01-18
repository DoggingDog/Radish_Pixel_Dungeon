package com.shatteredpixel.shatteredpixeldungeon.ui.changelist.rapd;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RadishEnemySprite.GiantWormSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeInfo;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class RA_v0_13_X_Changes {

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v04_2_Changes(changeInfos);
        add_v04_1_Changes(changeInfos);
        add_v03_X_Changes(changeInfos);
        add_v03_9_Changes(changeInfos);
        add_v03_8_Changes(changeInfos);
        add_v03_7_Changes(changeInfos);
        add_v03_6_Changes(changeInfos);
        add_v03_5_Changes(changeInfos);
        add_v03_4_Changes(changeInfos);
        add_v03_3_Changes(changeInfos);
        add_v03_2_Changes(changeInfos);
        add_v03_1_Changes(changeInfos);
    }

    public static void add_v04_2_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.9-R3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.WARRIOR, 8), ("战士天赋：精巧纹章"),
                ("修复 精巧纹章不生效 和 天赋2阶强化不生效")));

        changes.addButton(new ChangeButton(new Image(new RatKingSprite()), ("鼠王优化"),
                ("对于有恶魔之力的英雄，鼠王会有新的特殊对话。")));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ELTIE7), "精英证章",
                "功能修正：精英证章充能异常和其他小问题"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_CONCEAL), "匿踪斗篷",
                "功能修正：匿踪斗篷充能异常修正"));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战重制：荒芜之地"),
                ("草本身也不是很能在地牢里长的多好……\n\n-在每区，有50%/60%/70%/80%/90%的草变为枯草，额外生成的草也遵循此规律。\n\n之前的荒芜之地挑战规则全部废弃")));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "_-_1、修复法术序列实际效果与描述不符\n" +
                            "_-_2、修复藤蔓陷阱天赋失效\n" +
                            "_-_3、修复获得小恶魔的恶魔之力后回去见鼠王并没有特殊互动\n" +
                            "_-_4、修复奇迹树脂描述有问题，但实际效果没问题\n" +
                            "_-_5、修复匿踪斗篷不随使用而升级，修复精英证章无法充能和其他异常\n" +
                            "_-_6、修复仍有一些原版武器在生成池中未被删掉，但保留【十字弩】\n" +
                            "_-_7、 修复盗贼一层天赋的小干粮 和 矿洞任务文本缺失" ));
    }

    public static void add_v04_1_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.8->R2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.DISPLAY), ("破碎 & 萝卜 UI"),
                ("现在萝卜地牢默认萝卜UI,如果不习惯可在界面设置中调回破碎经典界面。")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.WARRIOR, 8), ("战士4层恶魔天赋"),
                ("战士恶魔天赋，完全实装，欢迎尝鲜")));

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.MAGE, 8), ("法师4层恶魔天赋"),
                ("除'缠怨恶灵'天赋禁用外，其他完全实装，欢迎尝鲜")));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SEED_CARD), "新初始物品：种子袋",
                "弥补开局关卡运营问题，可以自选一个种子，腐莓种除外，只能使用一次。"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.STONE_CRAD), "新初始物品：符石袋",
                "弥补开局关卡运营问题，可以自选一个符石，只能使用一次。"));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);


        changes.addButton(new ChangeButton(new Image(new GiantWormSprite()), ("巨型蠕虫平衡调整"),
                ("巨型蠕虫的吸血现在固定为1。")));

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.WARRIOR, 3), ("战士初始武器强化"),
                ("战士初始武器基准提升至2-10，成长基准为1-2。")));

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.MAGE, 4), ("法师初始武器强化"),
                ("法师初始武器基准提升至1-8，成长为1-2。")));

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.ROGUE, 5), ("盗贼初始武器强化"),
                ("盗贼初始武器基准提升至1-9，成长为1-2。")));

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.HUNTRESS, 6), ("女猎初始武器强化"),
                ("女猎灵能弓箭伤害强化，从1-6提升到2-6基准数值。")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战平衡：精英强敌"),
                ("精英怪生成概率由原先的1/8，调整为1/10")));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "1.修复DM-175护盾异常问题\n" +
                        "2.修复部分天赋不生效的问题\n" +
                        "3.修复伤痛难愈部分挑战未生效的问题" ));
    }

    public static void add_v03_X_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.7", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONG_STARK), "新武器：长棍",
                "正式实装此武器，"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.TAIKIG), "新武器：大太刀",
                "正式实装此武器"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WONDROUS_RESIN), "奇迹树脂",
                "1.修复奇迹树脂不生效的问题\n" +
                        "2.同步诅咒法杖的破碎全新效果"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LENGDS_PAGE), "育言故事",
                "全新育言故事登场，在探索地牢时阅读一些野史！"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AMETHYST), "T4天赋",
                "1.战士和法师的T4恶魔天赋已经实装\n" +
                        "2.部分天赋界面得到重制"));

        changes.addButton(new ChangeButton(Icons.get(Icons.WARNING), ("新崩溃界面"),
                ("由Cold Mint制作的新崩溃界面实装，什么，你连这位都不知道？萝卜的内部更新服务器接口就是薄荷姐姐提供的哦")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHANGES), ("内部更新系统"),
                ("内部更新系统回归！")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战加强：伤痛难愈"),
                ("新效果：冻肉露珠/诅咒法杖的吸血都受伤痛难愈的影响变为1")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战修正：弱点洞悉"),
                ("修复全局伤害加成问题")));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "1.少量文本缺失补充\n" +
                        "2.修复绝对闪避失效的问题\n" +
                        "3.修复闪避之戒数值加成异常的问题\n" +
                        "4.修复部分楼层贴图异常\n" +
                        "5.修复狂战士物理伤害不加怒气的问题\n" +
                        "6.修复法师天赋T4-短棍格斗1-3级不生效异常\n" +
                        "7.修复螃蟹护甲移速不生效的问题" ));
    }

    public static void add_v03_9_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AMETHYST), "戒指调整",
                "1.狂怒之戒成长调整为每级固定20%\n" +
                           "2.神射之戒耐久从20%-->10%\n" +
                           "3.闪避之戒成长调整为-->20%"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RUNE_SLADE), "符文外刀",
                "修复附魔相关异常"));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战优化：弱点洞悉"),
                ("此挑战效果已正常。")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("挑战改动：伤痛难愈"),
                ("新效果：治疗药水及其制品的效果被削弱为原来的1/10")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "1.少量文本缺失补充\n" +
                            "2.修复末日守卫迁移后失效的问题\n" +
                            "3.修复蝎子巨弩迁移后失效的问题\n" +
                            "4.修复部分楼层贴图异常\n" +
                            "5.修复兵师直觉异常" ));
    }

    public static void add_v03_8_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RUNE_SLADE), "新武器：符文外刀",
                "这柄武器刀刃上的符文会将附魔力量巧妙的转化成更大的杀伤力。\n\n当你在附魔这件武器时，也会升级它。"));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.RUNE_SLADE,new ItemSprite.Glowing(0x00ff00)), ("全新附魔登场"),
                ("连击，追寻，附魔登场")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("新挑战：伤痛难愈"),
                ("替代 恐药异症，挑战详情请查阅挑战描述")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), ("新挑战：弱点洞悉"),
                ("替代 信念护体，挑战详情请查阅挑战描述")));

        changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), ("杂项调整"),
                (       "1.图鉴系统登场\n" +
                        "2.探险者日志登场\n" +
                        "3.新增混乱香炉，遗忘碎片饰品\n" +
                        "4.炼金釜可鉴定物品\n" +
                        "5.炸弹伤害全局提升50%\n" +
                        "6.重命名系统回归\n" +
                        "7.正式移除部分破碎武器\n" +
                        "8.上个版本的补偿系统移除")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.4.4:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.修复部分文案异常问题\n" +
                        "_-_ 1.修复部分闪退问题\n" +
                        "_-_ 2.修复部分房间贴图异常问题\n" +
                        "_-_ 3.修复回音锤的一个小Bug" ));
    }

    public static void add_v03_7_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.4.3:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.修复 能量胸甲闪退异常问题\n" +
                        "_-_ 1.修复 长棍存档异常问题\n" +
                        "_-_ 2.修复 DM175护盾丢失问题\n" +
                        "_-_ 3.修复 怪物数量全局+4问题，应为+2\n" +
                        "_-_ 4.修复 大太刀不能必定暴击的问题\n" +
                        "_-_ 5.修复 部分NPC素材异常问题\n" +
                        "_-_ 6.修复 锁镰的拉怪裂缝，不攻击，以及概率异常问题\n" +
                        "_-_ 7.部分文案优化迭代" ));

    }

    public static void add_v03_6_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LOCK_CHAIN), "新武器：锁镰",
                "三阶，力量需求14\n" +
                        "初始4-20，成长1-4，力量需求15\n" +
                        "有且仅有额外的攻击距离3，用远端攻击击中敌人后可以不消耗回合的将其拉近\n" +
                        "近战会交替使用流星锤或镰刀，用流星锤攻击有25%的概率使对手虚弱2+武器等级回合，用镰刀攻击则有25%的概率给予初始值为2+武器等级的流血\n" +
                        "你的下一次近战攻击会使用镰刀\n" +
                        "极其奇特的长链武器，战法灵活多变。但有时连续攻击只能顺着武器的势头走，并不受你自己控制"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONG_STARK), "新武器：长棍",
                "三阶，力量需求14\n" +
                        "三阶，力量需求14\n" +
                        "初始4-20，成长1-4\n" +
                        "此武器的命中值暴击率与攻击速度会随着闪避值的提升而提升。 \n" +
                        "（闪避每提升一点命中就提升一点，暴击率攻击速度提升1%）  \n" +
                        "随身而动，随心而行。"));

        changes.addButton(new ChangeButton(Icons.get(Icons.STATS), ("全局优化"),
                ("每层的初始出怪数量增加4。")));

        changes.addButton(new ChangeButton(Icons.get(Icons.DISPLAY_LAND), ("恶魔之力天赋UI"),
                ("现在可以在英雄界面和开始游戏界面预览恶魔天赋")));

        changes.addButton(new ChangeButton(Icons.get(Icons.DISPLAY_PORT), ("随机图层"),
                ("二次优化图层优化，如果不喜欢可在游戏设置里面启用“原始地图风格")));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.4.0:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.修复 部分天赋异常问题\n" +
                        "_-_ 1.修复 部分素材效果异常问题\n" +
                        "_-_ 2.修复 上个版本的一些崩溃问题" ));
    }

    public static void add_v03_5_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.4.0-1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.PNEGLOVE_FIVE), "新武器：气动拳套",
                "在启动状态下的每次攻击需要消耗1炼金能量，此武器的伤害上升150%+10%*武器等级，" +
                        "\n\n" +
                        "攻击必定命中并会将敌人击退2+0.5*武器等级\n\n"+
                        "在启动状态下点击周围3*3范围内的非空地格时将会消耗1能量释放冲击波，（无伤害）击退范围内除自己外的单位2+0.5*武器等级"));

        changes.addButton(new ChangeButton(Icons.get(Icons.STATS), ("全局优化"),
                ("每层的初始出怪数量增加2，物品刷新率增加个10%，楼层大小增加15%。")));

        changes.addButton(new ChangeButton(Icons.get(Icons.DISPLAY_LAND), ("随机图层"),
                ("每层可以出现隐藏图块，但怪物不变。你也许能在新图块环境中有更好的作战积极性！")));

        changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), ("全新3大附魔登场"),
                ("汲能，狂热，重击 附魔登场")));

        changes.addButton(new ChangeButton(Icons.get(Icons.WARNING), ("0层相关问题修复"),
                ("吞力量，升级，魔能触媒问题修正")));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.4.0:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.修复 斩舰刃 和 两书的问题\n" +
                        "_-_ 1.移除决斗家\n" +
                        "_-_ 2.修复 上个版本的一些崩溃问题" ));
    }

    public static void add_v03_4_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.3.9-RC", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(HeroSprite.avatar(HeroClass.WARRIOR, 8), ("战士改动：角斗士更新"),
                ("角斗士连携技大改")));

        changes.addButton(new ChangeButton(Icons.get(Icons.DEPTH), ("0层回归"),
                ("萝卜地牢0层回归！")));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.3.9:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.修复 恶魔天赋 重进失效的问题\n" +
                        "_-_ 1.修复角斗士连击技能面板全英文；你现在已有X块暗金矿英文；被魅惑后攻击怪物提示英文的文本丢失\n" +
                        "_-_ 2.修复 火印恶魔火印不可见但仍能触发 的异常\n" +
                        "_-_ 3.二次修复 蜂巢 武器的功能缺失异常\n" +
                        "_-_ 4.修复 恶魔领主 伤害异常问题\n" +
                        "_-_ 5.修复 刻印者 与 火印恶魔的素材异常问题\n" +
                        "_-_ 6.修复部分情况下，每次退回主菜单，鉴定天赋会退回升级" ));
    }

    public static void add_v03_3_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.3.8", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.DISPLAY_LAND), ("界面更新"),
                ("过渡界面已迁移到2.5.0风格")));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "测试时间加强",
                "_-_ 追加属性生成器，更方便您的调试"));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n\n" +
                        "_来自于 v0.3.8:_\n\n" +
                        "[修复者：JDSALing]：\n"+
                        "_-_ 0.优化萝卜更新记录的界面的显示问题\n" +
                        "_-_ 1.修复各种文本丢失的异常\n" +
                        "_-_ 2.修复 自然之覆 的功能异常\n" +
                        "_-_ 3.战术，法师，盗贼，女猎的T1-T3天赋迁移完成\n" +
                        "_-_ 4.修复 守卫者陷阱 崩溃异常\n" +
                        "_-_ 5.修复穿戴护甲必定崩溃游戏的异常\n" +
                        "_-_ 6.修复全局伤害翻倍异常\n" +
                        "_-_ 7.修复 蜂巢 武器的功能缺失异常\n" +
                        "_-_ 8.修复 战士 部分护甲状态下素材显示错误\n" +
                        "_-_ 9.修复 石像 给予Buff闪退的严重异常\n\n" +
                        "[修复者：Doge]：\n" +
                        "_-_ 10.修复 护盾 伤害失效异常\n" +
                        "_-_ 11.修复 地底亚龙 的各种异常\n" +
                        "_-_ 12.修复 Buff基类 的一些迁移迭代异常"));
    }

    public static void add_v03_2_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.3.6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHANGES), ("重大更新"),
                ("萝卜现已更新底层到破碎V2.4.2版本，迁移者：JDSALing\n\n" +
                        "注意：迁移后可能还含有各种问题，请积极反馈！")));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "杂项生成器",
                "_-_ 同步 魔绫像素地牢的 生成器，让你的测试更加简单。"));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n" +
                        "_来自于 v0.3.5-MD3:_\n" +
                        "_-_ 修复石像Buff状态异常问题\n" +
                        "_-_ 部分素材贴图校准\n" +
                        "_-_ 部分效果失效修正"));
    }


    public static void add_v03_1_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("v0.3.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo("内部测试-MD3", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_EMERALD), "戒指生成",
                "内部测试1版：\n\n" +
                        "部分戒指的生成异常已经修正"));

        changes = new ChangeInfo("内部测试-MD2", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "1.迁移后的贴图定位错乱已经修正--Thanks(过去的事)\n\n" +
                        "2.部分界面的显示异常已经修正"));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "修复了以下Bug:\n" +
                        "_来自于 v0.3.5-MD1:_\n" +
                        "_-_ 修复DM175脱战护盾异常问题\n" +
                        "_-_ 修复豺狼祭司的一些潜在问题\n" +
                        "_-_ 修复迁移后的导致的部分贴图错乱\n" +
                        "\n" +
                        "_来自于 v0.3.5-MD0:_\n" +
                        "_-_ 修复石像的调查文本异常\n" +
                        "_-_ 修复迁移后的护甲生成异常"));
    }

}
