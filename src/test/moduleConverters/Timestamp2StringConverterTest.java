package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.sql.Timestamp;

public class Timestamp2StringConverterTest {
    Timestamp2StringConverter timestamp2StringConverter = new Timestamp2StringConverter();

    @Test
    public void testApply() throws Exception {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String result = timestamp2StringConverter.apply(timestamp);
        Assert.assertEquals(date.getTime(), result);
    }
}
