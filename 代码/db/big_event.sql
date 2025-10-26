/*
 Navicat Premium Dump SQL

 Source Server         : mysql80
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : big_event

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 26/10/2025 17:29:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `cover_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章封面',
  `state` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '草稿' COMMENT '文章状态: 只能是[已发布] 或者 [草稿]',
  `category_id` int UNSIGNED NULL DEFAULT NULL COMMENT '文章分类ID',
  `create_user` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_category`(`category_id` ASC) USING BTREE,
  INDEX `fk_article_user`(`create_user` ASC) USING BTREE,
  CONSTRAINT `fk_article_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_article_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '陕西旅游攻略', '兵马俑,华清池,法门寺,华山...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 6, '2025-09-27 17:41:49', '2025-09-27 17:41:49');
INSERT INTO `article` VALUES (3, '天津旅游攻略', '天安门,颐和园,鸟巢,长城...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '已发布', 2, 6, '2025-09-27 18:55:24', '2025-09-27 18:55:24');
INSERT INTO `article` VALUES (4, '北京旅游攻略', '天安门,颐和园,鸟巢,长城...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '已发布', 2, 6, '2025-09-27 18:55:53', '2025-09-27 18:55:53');
INSERT INTO `article` VALUES (6, '西安旅游攻略', '兵马俑,华清池,华山...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 5, '2025-09-27 21:18:29', '2025-09-27 21:18:29');
INSERT INTO `article` VALUES (7, '秦岭旅游攻略', '兵马俑,华清池,法门寺,华山...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '草稿', 2, 5, '2025-09-28 21:31:32', '2025-09-28 21:31:32');
INSERT INTO `article` VALUES (10, 'AI的未来', '<p>探讨人工智能技术的未来发展趋势，包括其在各行各业的应用前景以及可能带来的社会变革</p>', '', '', 37, 5, '2025-10-17 17:24:17', '2025-10-17 17:24:17');
INSERT INTO `article` VALUES (11, '锲而不舍 久久为功', '<p class=\"ql-align-justify\">2026年—2030年，“十五五”时期是基本实现社会主义现代化夯实基础、全面发力的关键时期。</p><p class=\"ql-align-justify\">从新中国成立初期的百废待兴到新时代的伟大变革，14个“五年”串联起不同时期的奋斗篇章，“一以贯之的主题是把我国建设成为社会主义现代化国家”。</p><p class=\"ql-align-justify\">今年5月，在河南考察时，习近平总书记一番话意味深长：“把历史视角、战略思维，都落实到具体行动上来。”谈及即将制定的新的五年规划，总书记说：“这实际上是中国的一个特色。我们在调查研究的基础上，提出充分尊重民意并且符合实际的全面系统的规划，并且有超强的执行力来落实这些规划。”</p><p class=\"ql-align-justify\">“有外国领导人对我说，中国有一大优势，你们总是制定各种规划，而且各种规划总是能实现。”一张蓝图绘到底的制度优势背后，是一茬接着一茬干的韧性。</p><p class=\"ql-align-justify\">《之江新语》里写着这样的深刻自觉：“要一以贯之，决不能朝令夕改”。</p><p class=\"ql-align-justify\">2013年，在党的十八届二中全会上，习近平总书记强调：“我们要牢记一个道理，政贵有恒。为官一方，为政一时，当然要大胆开展工作、锐意进取，同时也要保持工作的稳定性和连续性。”</p><p class=\"ql-align-justify\">政贵有恒。描绘新时代万里长江图，习近平总书记态度坚定：“只要坚持走，都会走到底，就怕来回来去改路线，什么时候能走到头”；擘画长三角一体化发展，总书记明确要求：“决不半途而废，决不南辕北辙，决不回头倒退”。</p><p class=\"ql-align-justify\">行之苟有恒，久久自芬芳。</p><p class=\"ql-align-justify\">从《1985年—2000年厦门经济社会发展战略》、福州“3820”战略工程，到浙江“八八战略”，站在今天回望，习近平同志在地方工作时领导编制的一个个长时间跨度的战略规划，决非着眼一时的孤篇，而是绵延不绝的实践续篇。</p><p class=\"ql-align-justify\">时间，在久久为功的累积中催生质变。</p><p class=\"ql-align-justify\">厦门从海防小城蜕变为开放窗口，福州从纸褙旧城发展为现代都市，浙江从资源小省转型为创新高地……它们以成功实践印证，“一张好的蓝图，只要是科学的、切合实际的、符合人民愿望的，大家就要一茬一茬接着干，干出来的都是实绩”。</p><p class=\"ql-align-justify\">大国复兴，志在千秋；跬步千里，积健为雄。</p><p class=\"ql-align-justify\">河北雄安新区雄安站候车厅，一座“千年轮”齿轮缓缓转动，走完一圈需一千年。它的起始日期被设定为2017年4月1日，那是中共中央、国务院决定设立雄安新区消息发布的日子。</p><p class=\"ql-align-justify\">“建设雄安新区是一项历史性工程，一定要保持历史耐心，有‘功成不必在我’的精神境界，稳扎稳打，一茬儿接着一茬儿干。”在习近平总书记引领下，这座未来之城，将千年大计的定力和只争朝夕的干劲，融入每一寸拔节生长。</p><p class=\"ql-align-justify\">“当好中国式现代化建设的坚定行动派、实干家”。放眼神州大地，更多接续奋斗的画卷，铺展在新的征程上。</p><p class=\"ql-align-justify\">如期完成脱贫攻坚，接续推进乡村全面振兴，不断满足人民对美好生活的向往；从年年植树到代代治沙，推动荒山成林、沙地变绿，为了美丽中国久久为功；“神舟”飞天、“嫦娥”奔月、“祝融”探火、“羲和”逐日，向着航天强国的星辰大海进军……</p><p class=\"ql-align-justify\">历史在砥砺中前行，未来在奋斗中书写。</p><p class=\"ql-align-justify\">2024年5月，习近平总书记来到山东，就进一步全面深化改革进行调研。</p><p class=\"ql-align-justify\">日照港边，海天辽阔，碧波荡漾。总书记驻足码头岸边，临海迎风，远眺凝思。</p><p class=\"ql-align-justify\">看到港口职工朝气蓬勃、志气昂扬，习近平总书记十分高兴，深情勉励：“我们都是奋斗者，从过去奋斗到今天，取得这么辉煌的成就。我们未来的目标很明确很伟大，要实现它，还得靠我们继续实干奋斗。”</p><p class=\"ql-align-justify\">路虽远，行则将至；事虽难，做则必成。</p><p class=\"ql-align-justify\">2022年农历小年刚过，习近平总书记驱车沿着蜿蜒山路，冒雪来到汾河岸的村庄，走进老百姓家中，拉家常、问冷暖。</p><p class=\"ql-align-justify\">“现在村里水、电、交通都很好，大家都有事做、有收入，我们赶上了好时代，都是托共产党的福、托总书记的福。”村民朴实的话语道出了真挚的心声。</p><p class=\"ql-align-justify\">“共产党就是给人民办事的，就是要让人民的生活一天天好起来，一年比一年过得好。”习近平总书记说。</p><p class=\"ql-align-justify\">一天天、一年年累积的沧桑之变，见证着人民政党实干富民，“好了还要再好，不能止步”的不变信念。</p><p class=\"ql-align-justify\">“一任接着一任干，一任干给一任看。”30多年前，在福建宁德干部队伍中流传着这样一句话。习近平同志把它进一步改为：“一任接着一任干，任任干给群众看。”</p><p class=\"ql-align-justify\">2015年，再次回到梁家河，同乡亲们一起回忆七年知青岁月，习近平总书记这样坦露心扉：“那个时候，我就说，今后如果有条件、有机会，我要做一些为老百姓办好事的工作。”</p><p class=\"ql-align-justify\">“要为人民做实事”——这是坚定不移的初心，也是矢志不渝的行动。</p><p class=\"ql-align-justify\">大道如砥，不负人民。循道而行，功成事遂。</p><p class=\"ql-align-justify\">回望来路，“我们取得的一切成就，是一代又一代中国共产党人同中国人民接续奋斗的结果”。</p><p class=\"ql-align-justify\">眺望前方，“实现中华民族伟大复兴是前无古人的伟大事业。憧憬和挑战，都激发我们只争朝夕、永不懈怠的奋斗精神”。</p><p class=\"ql-align-justify\">历史不会辜负实干者，未来一定属于奋斗者。</p><p><br></p>', '', '已发布', 38, 5, '2025-10-21 18:41:06', '2025-10-21 18:41:06');
INSERT INTO `article` VALUES (12, '在一个傍晚', '<p>完成、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、</p>', '', '', 38, 5, '2025-10-26 16:55:18', '2025-10-26 16:55:18');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_alias` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类别名',
  `create_user` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_category_user`(`create_user` ASC) USING BTREE,
  CONSTRAINT `fk_category_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '自然风光', 'zrfg', 5, '2025-09-27 11:57:06', '2025-09-27 11:57:06');
INSERT INTO `category` VALUES (2, '风土人情', 'ftrq', 5, '2025-09-27 12:19:02', '2025-09-27 12:19:02');
INSERT INTO `category` VALUES (9, '人文', 'renwen', 6, '2025-09-27 17:45:48', '2025-09-27 17:45:48');
INSERT INTO `category` VALUES (37, '人工智能', 'AI', 5, '2025-10-17 16:44:48', '2025-10-17 16:44:48');
INSERT INTO `category` VALUES (38, '社科', 'sk', 5, '2025-10-21 18:40:00', '2025-10-21 18:40:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '邮箱',
  `user_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'quanli', '26fd45817c0a0bb1e951d17fa7df947c', '', '', '', '2025-09-23 18:25:04', '2025-09-23 18:25:04');
INSERT INTO `user` VALUES (5, 'liming', 'e10adc3949ba59abbe56e057f20f883e', 'wbss', '1331@gmail.com', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '2025-09-26 17:04:55', '2025-10-26 16:54:34');
INSERT INTO `user` VALUES (6, 'wangba', 'e10adc3949ba59abbe56e057f20f883e', 'jiangjiu', '159@qq.com', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '2025-09-27 12:49:46', '2025-10-21 19:55:41');
INSERT INTO `user` VALUES (8, '123456', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '2025-10-15 16:50:09', '2025-10-15 16:50:09');

SET FOREIGN_KEY_CHECKS = 1;
