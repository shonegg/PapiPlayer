package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.papi.player.javabean");
        // 1: 数据库版本号
        // com.xxx.bean:自动生成的Bean对象会放到/java-gen/com/xxx/bean中
        schema.setDefaultJavaPackageDao("com.papi.player.greendao");
        // DaoMaster.java、DaoSession.java、BeanDao.java会放到/java-gen/com/xxx/dao中
        // 上面这两个文件夹路径都可以自定义，也可以不设置
        initVideoBean(schema); // 初始化Bean了
        initMediaBean(schema);
        //这里输出 配置成自己的路径
        String out = "/home/shone/Public/work_androidstudio/PaPaPlayer/app/src/main/java-gen";

        new DaoGenerator().generateAll(schema, out);// 自动创建
    }

    private static void initVideoBean(Schema schema) {
        Entity bean = schema.addEntity("VideoBean");// 表名
        bean.setTableName("VideoBean");
        bean.addStringProperty("cover_url");

        bean.addStringProperty("enter_id").primaryKey().index();// 主键，索引
        bean.addStringProperty("cId");
        bean.addStringProperty("cCode");
        bean.addStringProperty("name");
        bean.addStringProperty("time");
    }


    private static void initMediaBean(Schema schema) {
        Entity bean = schema.addEntity("MediaBean");// 表名
        bean.setTableName("MediaBean");

        bean.addStringProperty("enter_id").primaryKey().index(); // 主键，索引
        bean.addStringProperty("cover_url");
        bean.addStringProperty("name");
        bean.addStringProperty("time");
        bean.addStringProperty("cId");
        bean.addStringProperty("cCode");

    }
}
