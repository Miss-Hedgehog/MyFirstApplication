package cn.edu.jnu.supershopper;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cn.edu.jnu.supershopper.data.DataSaver;
import cn.edu.jnu.supershopper.data.ShopItem;

public class DataSaverTest {
    DataSaver dataSaverBackup;
    ArrayList<ShopItem> shopItemsBackup;
    @Before
    public void setUp() throws Exception {
        //测试前保存测试环境，备份
        Context targetContext=InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup=new DataSaver();
        shopItemsBackup=dataSaverBackup.Load(targetContext);

    }

    @After
    public void tearDown() throws Exception {
        //测试后恢复测试环境，恢复
        Context targetContext=InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup.Save(targetContext,shopItemsBackup);
    }

    @Test
    public void saveAndLoad() {
        DataSaver dataSaver=new DataSaver();
        Context targetContext=InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<ShopItem> shopItems=new ArrayList<>();
        ShopItem shopItem=new ShopItem("测试",56.7,R.drawable.book_no_name);
        shopItems.add(shopItem);
        shopItem=new ShopItem("正常",12.3,R.drawable.folder);
        shopItems.add(shopItem);
        dataSaver.Save(targetContext,shopItems);

        DataSaver dataLoader=new DataSaver();
        ArrayList<ShopItem> shopItemRead=dataLoader.Load(targetContext);
        Assert.assertEquals(shopItems.size(),shopItemRead.size());
        for(int index=0;index<shopItems.size();index++){
            Assert.assertEquals(shopItems.get(index).getTitle(),shopItemRead.get(index).getTitle());
            Assert.assertEquals(shopItems.get(index).getPrice(),shopItemRead.get(index).getPrice(),1e-2);
            Assert.assertEquals(shopItems.get(index).getResourceId(),shopItemRead.get(index).getResourceId());
        }

    }
    /*
    @Test
    public void load() {
        DataSaver dataSaver=new DataSaver();
        ArrayList<ShopItem> shopItems=dataSaver.Load(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Assert.assertEquals(2,shopItems.size());

    }*/
}