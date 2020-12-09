package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

public class String2TimestampConverterTest {
    String2TimestampConverter string2TimestampConverter = new String2TimestampConverter();
    @Test
    public void testApply() throws Exception {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Timestamp result = string2TimestampConverter.apply(String.valueOf(timestamp));

        Assert.assertEquals(new Timestamp(date.getTime()), result);
    }
}
