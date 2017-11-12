import eu.newsreader.eventcoreference.configurationproperties.ProcessEventObjectStreamProperties;
import eu.newsreader.eventcoreference.configurationproperties.SemFromNafStreamProperties;
import eu.newsreader.eventcoreference.naf.GetSemFromNafStream;
import eu.newsreader.eventcoreference.naf.ProcessEventObjectsStream;
import ixa.kaflib.KAFDocument;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

@Disabled
public class StreamTest {

    private static final Logger logger = Logger.getLogger(StreamTest.class);

    private GetSemFromNafStream getSemFromNafStream;
    private ProcessEventObjectsStream processEventObjectsStream;
    private KAFDocument kafDocument;

    @Test
    public void testStream() throws UnsupportedEncodingException {

        logger.info("sem transform");
        ByteArrayOutputStream semOutputStream = getSemFromNafStream.getSem(kafDocument);

        logger.info("process event object stream");
        ByteArrayOutputStream processEventOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream processEventInputStream = new ByteArrayInputStream(semOutputStream.toByteArray());
        processEventObjectsStream.processTrigInputStream(processEventInputStream, processEventOutputStream);

        logger.info("Processed Object Stream:" + new String(processEventOutputStream.toByteArray()));
    }

    @BeforeEach
    public void init() throws IOException {
        SemFromNafStreamProperties semFromNafStreamProperties = new SemFromNafStreamProperties(){};
        ProcessEventObjectStreamProperties processEventObjectStreamProperties = new ProcessEventObjectStreamProperties(){};
        this.getSemFromNafStream = new GetSemFromNafStream(semFromNafStreamProperties);
        this.processEventObjectsStream = new ProcessEventObjectsStream(processEventObjectStreamProperties);

        File file = new File(StreamTest.this.getClass().getClassLoader().getResource("wikinews_1173_en.naf").getFile());
        kafDocument = KAFDocument.createFromFile(file);
    }

}
