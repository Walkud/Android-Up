package cn.studyjams.s1.sj10.zhuliya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private Button jieFangBeiBtn;
    private Button renMinDaLiTangBtn;
    private Button hongYaDongBtn;
    private Button ciQiKouGuZhenBtn;
    private Button jinFoShanBtn;
    private Button daZuShiKeBtn;

    private ScenicSpot jieFangBei;
    private ScenicSpot renMinDaLiTang;
    private ScenicSpot hongYaDong;
    private ScenicSpot ciQiKouGuZhen;
    private ScenicSpot jinFoShan;
    private ScenicSpot daZuShiKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        initData();
    }

    /**
     * 绑定View
     */
    private void bindView() {
        jieFangBeiBtn = (Button) findViewById(R.id.jieFangBeiBtn);
        renMinDaLiTangBtn = (Button) findViewById(R.id.renMinDaLiTangBtn);
        hongYaDongBtn = (Button) findViewById(R.id.hongYaDongBtn);
        ciQiKouGuZhenBtn = (Button) findViewById(R.id.ciQiKouGuZhenBtn);
        jinFoShanBtn = (Button) findViewById(R.id.jinFoShanBtn);
        daZuShiKeBtn = (Button) findViewById(R.id.daZuShiKeBtn);

        //解放碑
        jieFangBeiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(jieFangBei.getName(), jieFangBei.getImgRes(), jieFangBei.getIntroduction(), jieFangBei.getAspect(), jieFangBei.getOpenTime());
            }
        });

        //人民大礼堂
        renMinDaLiTangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(renMinDaLiTang.getName(), renMinDaLiTang.getImgRes(), renMinDaLiTang.getIntroduction(), renMinDaLiTang.getAspect(), renMinDaLiTang.getOpenTime());
            }
        });

        //洪崖洞
        hongYaDongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(hongYaDong.getName(), hongYaDong.getImgRes(), hongYaDong.getIntroduction(), hongYaDong.getAspect(), hongYaDong.getOpenTime());
            }
        });

        //磁器口古镇
        ciQiKouGuZhenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(ciQiKouGuZhen.getName(), ciQiKouGuZhen.getImgRes(), ciQiKouGuZhen.getIntroduction(), ciQiKouGuZhen.getAspect(), ciQiKouGuZhen.getOpenTime());
            }
        });

        //金佛山
        jinFoShanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(jinFoShan.getName(), jinFoShan.getImgRes(), jinFoShan.getIntroduction(), jinFoShan.getAspect(), jinFoShan.getOpenTime());
            }
        });

        //大足石刻
        daZuShiKeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(daZuShiKe.getName(), daZuShiKe.getImgRes(), daZuShiKe.getIntroduction(), daZuShiKe.getAspect(), daZuShiKe.getOpenTime());
            }
        });
    }

    /**
     * 跳转详情介绍界面
     *
     * @param name         风景名称
     * @param imgRes       图片资源Id
     * @param introduction 简介
     * @param aspect       看点
     * @param openTime     开放时间
     */
    private void jumpDetailedActivity(String name, int imgRes, String introduction, String aspect, String openTime) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("imgRes", imgRes);
        intent.putExtra("introduction", introduction);
        intent.putExtra("aspect", aspect);
        intent.putExtra("openTime", openTime);
        startActivity(intent);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        jieFangBei = new ScenicSpot();
        jieFangBei.setName("解放碑");
        jieFangBei.setImgRes(R.mipmap.img_jiefangbei);
        jieFangBei.setIntroduction("如今，解放碑一带已经成为重庆市区最繁华的商贸中心地带。这里高楼大厦林立，交通四通八达，百货商店、影剧院、歌舞厅、副食品市场、书店、宾馆、饭店等鳞次栉比，一应俱全，游客至此，既可观光巴渝风物人情，又可品尝地方名特小吃，还可购买纪念品和其他物品。");
        jieFangBei.setAspect("解放碑位于渝中区民族、民权、邹容三大路交汇的十字路口，通高27.5米，有旋梯可达顶端。该建筑最初落成于1940年3月12日孙中山先生逝世纪念日，为低矮木质结构，称“精神堡垒”。1945年抗战胜利后重建，题名为“抗战胜利纪功碑”。1950年由刘伯承改题“重庆人民解放纪念碑”。");
        jieFangBei.setOpenTime("免费/全天");

        renMinDaLiTang = new ScenicSpot();
        renMinDaLiTang.setName("人民大礼堂");
        renMinDaLiTang.setImgRes(R.mipmap.img_renmindalitang);
        renMinDaLiTang.setIntroduction("重庆市人民大礼堂位于人民路学田湾，于1951年6月破土兴建，1954年4月竣工，是一座仿古民族建筑群，也是重庆独具特色的标志建筑物之一。\n" +
                "整座建筑由大礼堂和东、南、北楼四大部分组成。占地总面积为6.6万平方米，其中礼堂占地1.85万平方米。礼堂建筑高65米，大厅净空高55米，内径46.33米，圆形大厅四周环绕五层挑楼，可容纳4200余人。\n" +
                "重庆人民大礼堂的设计，仿明、清的宫殿形式，采用轴向对称的传统手法，结构匀称，对比强烈，布局严谨，古雅明快。主体部分的穹庐金顶，脱胎于北京天坛的皇穹宇，仿天坛有祷祝“国泰民安”之意。；正中的圆柱望楼，是北京天安门的缩影；南北两翼，镶嵌着类似北京紫禁城四角的塔楼；广袤的庭院中，前阶宽阔平展，梯次六重。1997年，重庆设立直辖市，大礼堂前改建为有草坪与喷泉的人民广场，成为供游人参观、休息和举办节庆集会的重要景点和场所。");
        renMinDaLiTang.setAspect("重庆市人民大礼堂是一精美奇巧的东方式建筑。1987年，英国出版的世界建筑经典著作《比较建筑史》收录我国当代43项建筑工程，将该建筑排列为第二位。");
        renMinDaLiTang.setOpenTime("5元/8:30-17:00");

        hongYaDong = new ScenicSpot();
        hongYaDong.setName("洪崖");
        hongYaDong.setImgRes(R.mipmap.img_hongyadong);
        hongYaDong.setIntroduction("洪崖洞位于重庆市核心商圈解放碑沧白路、长江、嘉陵江两江交汇的滨江地带，是新兴的集娱乐、休闲、观光、餐饮于一体的大型功能区域，也是时下重庆最火爆、最时尚、最具风情的都市休闲区。\n" +
                "整个区域以最具巴渝传统建筑特色的“吊脚楼”风貌为主体，依山就势，沿江而建，让解放碑直达江滨。外来游客可游吊脚群楼、观洪崖滴翠，逛山城老街、赏巴渝文化，品山城火锅、尝天下美食，看两江汇流、玩不夜风情的休闲娱乐新天地。\n" +
                "洪崖洞整体业态分为纸盐河酒吧街、天成巷巴渝风情街、盛宴美食街及异域风情城市阳台四条大街。四条大街分别融汇了当下所有时尚元素，主题迥异、特色鲜明，绝对是今天来重庆不可不去的地方。");
        hongYaDong.setAspect("有美国全球连锁海盗酒吧等一系列全球知名酒吧加盟的纸盐河酒吧街，不但可引领重庆新娱乐生活方式，也将带动重庆夜生活的新标向。\n" +
                "天成巷巴渝风情街为世人展示的一种不同于以往的时尚潮流，他以2300前年的巴渝盛景为载体，展示出当时盛行于世的青砖、石瓦、红檐绿瓦的古典民居。\n" +
                "盛宴美食街为展现的则是一种“另类美食城”的盛景，一个集中外古今名店于一堂的美食盛景。\n" +
                "异域风情城市阳台作为重庆最大的一个城市交通转换站而备受世人注目，需要游逛解放碑或停留在洪崖洞游玩的游客可乘坐扶梯或观光电梯直上解放碑或达到洪崖洞的任何一层楼。");
        hongYaDong.setOpenTime("免费/全天");

        ciQiKouGuZhen = new ScenicSpot();
        ciQiKouGuZhen.setName("磁器口古镇");
        ciQiKouGuZhen.setImgRes(R.mipmap.img_ciqikouguzhen);
        ciQiKouGuZhen.setIntroduction("磁器口，以出产瓷器而得名。在1918年地方商绅集资在青草坡创建了新工艺制瓷的“蜀瓷厂”，瓷器质地很好，品种繁多，名声渐大，产品远销省内外。渐渐地“磁器口”名代替了“龙隐镇”。现已发现古窑遗址20余处。\n" +
                "古镇磁器口有12条街巷，街道两旁大多是明清风格的建筑，地面由石板铺成，沿街店铺林立。商贸集中在大码头和靠码头的金蓉正街。现今磁器口古镇保存了较为完整的古建筑，开发了榨油、抽丝、制糖、捏面人、川戏等传统表演项目和各种传统小吃、茶馆等，每年春节举办的瓷器口庙会四古镇最具特色的传统活动，吸引数万市民前往参与，是距重庆主城区最近的古镇景观。在古镇磁器口最有特色的一景是茶馆。昔日在这千年古镇，随处可见茶馆。当年的水手、袍哥大爷、闲杂人等都喜爱出入此间，茶馆成了龙蛇混杂之地。直到如今，茶馆仍是磁器口一景，百来米长的老街便有13家茶馆，家家茶客满座，古风犹存。名特小吃毛血旺、软烩千张、椒盐花生被誉为磁器口的“三宝”。");
        ciQiKouGuZhen.setAspect("一条石板路，千年磁器口是古镇的写照。磁器口古镇原名龙隐镇，位于重庆城西14公里，在沙坪坝区。自明、清时期以来磁器口古镇名扬巴蜀大地。昔日这里是一个热闹非凡的水陆码头，为嘉陵江下游物资集散地。那经千年不变的浓郁纯朴的古风，令其成为重庆江州古城的缩影和象征。");
        ciQiKouGuZhen.setOpenTime("免费/全天");

        jinFoShan = new ScenicSpot();
        jinFoShan.setName("金佛山");
        jinFoShan.setImgRes(R.mipmap.img_jinfoshan);
        jinFoShan.setIntroduction("金佛山位于重庆 西南部南川市境内，属大娄山东段支脉，主峰风吹岭海拔2251米，景区总面积1300平方公里，游览区域264平方公里。金佛山区由金佛、柏枝、箐坝三座大山组成，主峰山顶与山麓的相对高差达1,900多米，森林复盖率为76%，原始森林约占30%。\n" +
                "金佛山属石灰岩卡斯特地貌，山势雄伟，切割强烈，山中有古老的溶洞群落。金佛山自然保护区始建于1979年，山上珍稀动植物种类繁多，植物多达5099种，其中银杉、银杏、大叶茶、方竹、杜鹃王树属国家一类保护植物，被誉为“金山五绝”。动物500多种，其中有属于国家一级保护动物的金钱豹、云豹、华南虎、白冠鹤、红腹角鸡、金丝猴、黑叶猴、梅花鹿等。\n" +
                "冬日畅游金佛山，最大的快乐来自雪上运动。金佛山滑雪场是重庆地区积雪条件最好的高山滑雪场之一。除了滑雪以外，雪地摩托、雪上飞碟、雪上飞船、马拉雪橇等等也是雪场不可缺少的娱乐项目。");
        jinFoShan.setAspect("金佛山动植物资源丰富，出产大型蝴蝶。此外，活化石银杉、大片方竹林、古生大茶树、野生古银杏和杜鹃王被称为金佛山“ 五绝”。\n" +
                "金佛山是天然的植物宝库：银杉为第四纪冰川袭击后幸存的植物大熊猫，华夏独有，异常珍贵。方竹笋肉厚脆嫩，是佐餐的美味山珍，远销海外。2000多株古生大茶树证明了我国巴蜀是世界茶叶的起源地。杜鹃王高12米，胸围近4米，三人合抱，是世界上名副其实的杜鹃花王。金佛山灌木、乔木杜鹃花33种，30多万株。古银杏高26米，胸围11.6米，老态龙钟，当地人称“白果娘娘”。珙桐、粗榧、小虫草誉为“金山三宝”；人参、竹米、天竺黄则为“金山三精”。");
        jinFoShan.setOpenTime("门票25元，索道50元，古佛洞10元。\n" +
                "滑雪价格：第一个小时每人80元，此后每小时50元；雪上飞船第一个半小时每人30元，此后每小时20元；雪地摩托每圈每人50元；山地摩托为20元每人每圈；马拉雪车是20元一次。\n" +
                "8:00~16:00 停止售票时间：16:00");

        daZuShiKe = new ScenicSpot();
        daZuShiKe.setName("大足石刻");
        daZuShiKe.setImgRes(R.mipmap.img_dazushike);
        daZuShiKe.setIntroduction("大足石刻艺术群位于大足县境内，距重庆市区87公里，有石刻造像70余处，总计10万尊，其中以宝顶山和北山石刻最为著名，保存最为完好。1999年12月被联合国教科文组织列入《世界文化遗产名录》。\n" +
                "以北山、宝顶山、南山、石篆山、石门山(简称“五山”)摩崖造像为代表的大足石刻是中国石窟艺术重要的组成部分，也是世界石窟艺术中公元9世纪末至13世纪中叶间，中国晚唐景福元年至南宋淳佑十二年最为壮丽辉煌的一页。大足石刻始建于公元650年唐永徽元年，兴盛于公元9世纪末至13世纪中叶，余绪延至明、清，是中国晚期石窟艺术的代表作品。\n" +
                "“五山”摩崖造像以规模宏大，雕刻精美，题材多样，内涵丰富，保存完好而著称于世。以集释佛教、道道教、儒儒家“三教”造像之大成而异于前期石窟。以鲜明的民族化、生活化特色，在中国石窟艺术中独树一帜。以大量的实物形象和文字史料，从不同侧面展示了公元9世纪末至13世纪中叶间中国石窟艺术风格及民间宗教信仰的重大发展、变化，对中国石窟艺术的创新与发展有重要贡献，具有前期各代石窟不替代的历史、艺术、科学和鉴赏价值。");
        daZuShiKe.setAspect("大足石刻以其规模宏大、雕刻精美、题材多样、内涵丰富和保存完整而著称于世。它集中国佛教、道教、儒家“三教”造像艺术的精华，以鲜明的民族化和生活化特色，成为中国石窟艺术中一颗璀璨的明珠。\n" +
                "宝顶山摩崖(石窟)石刻：宝顶山位于大足县城龙岗镇东北15公里处，海拔527.83米，是佛教圣地之一，有“上朝峨嵋，下朝宝顶”之说。石刻创始人为宋蜀中名僧赵智凤，建于南宋淳熙六年至淳祐九年(1179—1249年)，历时70多年，石刻共13处，造像数以万计，四周2.5公里内山岩上遍刻佛像，包括以圣寿寺为中心的大佛湾、小佛湾造像。以大佛湾为主体，小佛湾次之，分布在东、南、北三面。巨型雕刻360余幅，以六道轮回，广大宝楼阁、华严二圣像、千手观音像等最为著名。宝顶大佛湾处有川东古刹圣寿寺，创建于南宋。庙宇巍峨，雕梁满目，坐落于山势峻秀、环境幽雅的林木之中。寺侧南岩为万岁楼，这是一座造型别致的二层飞檐翘角楼阁。  \n" +
                "北山摩崖(石窟)石刻：位于大足县城北2公里处，海拔545.5米，开凿于唐代昭宗景福元年(892年)，历经五代，两宋，相继在佛湾、营盘坡、观音坡、北塔寺、佛耳岩等处造像近万躯。长达500米余，共编为290号龛窟。其中有碑碣6通，题记和造像55处，经幢8座，银刻线阁1幅，石刻造像364龛窟。北山石刻以佛湾造像最为集中，共编290号龛窟。在长300多米、高7米的崖壁上，有碑碣6通，题记和造像铭记55则，经幢8座，阴刻“文殊师利问疾图”一幅，石刻造像264龛窟。佛湾佛像雕刻精细，体态俊逸，风格独特。“心神车窟”中的“普贤菩萨”造像精美，被誉为“东方维纳斯”；“转轮藏经洞”被称为“石雕宫阙”；“韦君靖碑”、“蔡京碑”、“古文孝经碑”为世所独存，既是书法珍品，又可补史料之遗缺，价值极高。\n" +
                "南山石刻： 南山，古名广华山，位于大足县城东南方向2.5公里处。山顶上原有道现，名玉皇观。南山石刻造像缘起于南宋时期(公元1127～1279年)，属道教造像。明清两代稍有增补。  \n" +
                "此处摩崖造像一共有十五龛造像，都以道家作品为主，这是此山的一大特点。三清古洞是其代表作。\n" +
                "石门山石刻：石门山位于大足县城龙岗镇东20公里处的石马镇新胜村，海拔374.1米。造像开凿于公元1094～1151年(北宋绍圣至南宋绍兴二十一年)。刻像崖面全长71.8米，崖高3.4～5米，通编为16号，其中有造像12龛窟。此外，尚存造像记20件，碑碣、题刻8件。石门山摩崖造像为佛教、道教合一造像区，尤以道教造像最具特色。\n" +
                "石篆山石刻：石篆山位于大足县城龙岗镇西南25公里处的三驱镇佛惠村，海拔444.6米。据佛惠寺《严逊记碑》记载，造像于公元1082～1096年北宋元丰五年至绍圣三年开凿而成。造像崖面长约130米，高约3～8米，通编为10号。石篆山摩崖造像为典型的释、道、儒“三教”合一造像区，在石窟中罕见。其中，第6号为孔子及十哲龛，正壁刻中国大思想家、儒家创始人孔子坐像，两侧壁刻孔子最著名的十大弟子。这在石窟造像中，实属凤毛麟角。第7号为三身佛龛。第8号为老君龛，正中凿中国道教创始人老子坐像，左右各立7尊真人、法师像。");
        daZuShiKe.setOpenTime("北山、宝顶山(大佛湾、小佛湾)联票：120元\n" +
                "宝顶山石刻(大佛湾、小佛湾)门票：80元\n" +
                "北山石刻门票：60元\n" +
                "南山石刻门票：5元\n" +
                "石门山石刻门票：5元\n" +
                "开放时间：08:30~18:00");
    }
}
