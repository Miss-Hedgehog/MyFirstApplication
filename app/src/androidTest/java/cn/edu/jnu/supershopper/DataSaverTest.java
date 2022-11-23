package cn.edu.jnu.supershopper;

import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cn.edu.jnu.supershopper.data.DataSaver;
import cn.edu.jnu.supershopper.data.ShopItem;

public class DataSaverTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
        DataSaver dataSaver=new DataSaver();
    }

    @Test
    public void load() {
        DataSaver dataSaver=new DataSaver();
        ArrayList<ShopItem> shopItems=dataSaver.Load(ApplicationProvider.getApplicationContext());
        Assert.assertEquals(0,shopItems.size());

    }
}