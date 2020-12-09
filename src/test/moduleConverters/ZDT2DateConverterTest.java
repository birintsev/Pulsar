package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;

public class ZDT2DateConverterTest {
    ZDT2DateConverter zDT2DateConverter = new ZDT2DateConverter();

    @Test
    public void testApply() throws Exception {
        Date date = new Date();
        Date result = zDT2DateConverter.apply(ZonedDateTime.now());
        Assert.assertEquals(date.getTime(), result);
    }
}
