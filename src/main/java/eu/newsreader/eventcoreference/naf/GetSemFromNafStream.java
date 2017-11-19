package eu.newsreader.eventcoreference.naf;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.newsreader.eventcoreference.configurationproperties.SemFromNafStreamProperties;
import eu.newsreader.eventcoreference.coref.ComponentMatch;
import eu.newsreader.eventcoreference.objects.*;
import eu.newsreader.eventcoreference.output.JenaSerialization;
import eu.newsreader.eventcoreference.util.FrameTypes;
import eu.newsreader.eventcoreference.util.MD5Checksum;
import ixa.kaflib.KAFDocument;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by piek on 2/12/14.
 */
public class GetSemFromNafStream {

    private static final Logger logger = Logger.getLogger(GetSemFromNafStream.class);

    private static NafSemParameters nafSemParameters = new NafSemParameters();
    private SemFromNafStreamProperties semProperties;

    static public void main(String[] args) {
        nafSemParameters = new NafSemParameters(args);
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(System.in);
        getSem(kafSaxParser, System.out);
    }


    public GetSemFromNafStream(SemFromNafStreamProperties semProperties) {
        this.semProperties = semProperties;
        parseProperties();
    }


    private void parseProperties(){
        nafSemParameters.setALL(semProperties.isAll());
        nafSemParameters.setADDITIONALROLES(semProperties.isAdditionalRoles());
        nafSemParameters.setCONTEXTTIME(semProperties.isContexttime());
        nafSemParameters.setDOCTIME(semProperties.isDocTime());
        nafSemParameters.setILIURI(semProperties.isIliUri());
        nafSemParameters.setEVENTCOREF(semProperties.isEventCoref());
        nafSemParameters.setNONENTITIES(semProperties.isNonEntities());
        nafSemParameters.setNOMCOREF(semProperties.isNomCoref());
        nafSemParameters.setPERSPECTIVE(semProperties.isPerspective());
        nafSemParameters.setVERBOSE(semProperties.isVerbose());
        nafSemParameters.setTIMEEXPRESSIONMAX(semProperties.getTimeMax());

        nafSemParameters.setEuroVoc(semProperties.getPathToEurovocFile(), "en");
        nafSemParameters.readContextVector(semProperties.getContextualFrames());
        nafSemParameters.readSourceVector(semProperties.getSourceFrames());
        nafSemParameters.readGrammaticalVector(semProperties.getGrammaticalFrames());
    }


    public static void getSem(KafSaxParser kafSaxParser, PrintStream printStream){

        ArrayList<SemObject> semEvents = new ArrayList<>();
        ArrayList<SemObject> semActors = new ArrayList<>();
        ArrayList<SemTime> semTimes = new ArrayList<>();
        ArrayList<SemRelation> semRelations = new ArrayList<>();

        if (kafSaxParser.getKafMetaData().getUrl().isEmpty()) { //TODO add url validator
            try {
                logger.warn("Empty url in header NAF. Try to use rawText as checksum create unique URIs!");
                String checkSum = MD5Checksum.getMD5ChecksumFromString(kafSaxParser.rawText);
                kafSaxParser.getKafMetaData().setUrl(checkSum);
            } catch (Exception e) {
                logger.error("Unable to get checkSum of rawText", e);
            }
        }

        GetSemFromNaf.processNafFile(nafSemParameters, kafSaxParser, semEvents, semActors, semTimes, semRelations );
        ArrayList<CompositeEvent> compositeEventArraylist = new ArrayList<>();
        for (int j = 0; j < semEvents.size(); j++) {

            SemEvent mySemEvent = (SemEvent) semEvents.get(j);
            ArrayList<SemTime> myTimes = ComponentMatch.getMySemTimes(mySemEvent, semRelations, semTimes);
            ArrayList<SemActor> myActors = ComponentMatch.getMySemActors(mySemEvent, semRelations, semActors);
            ArrayList<SemRelation> myRelations = ComponentMatch.getMySemRelations(mySemEvent, semRelations);

            CompositeEvent compositeEvent = new CompositeEvent(mySemEvent, myActors, myTimes, myRelations);
            if (myTimes.size() > nafSemParameters.getTIMEEXPRESSIONMAX()) {
                logger.debug("Skipping event due to excessive time expressions linked to it. " +
                        "compositeEvent="+compositeEvent.getEvent().getURI() +" myTimes="+myTimes.size());
                continue;
            }
            if (!compositeEvent.isValid()) {
                logger.debug("Skipping event due to no time anchor and/or no participant. " +
                        "compositeEvent=" + compositeEvent.getEvent().getURI()
                        + " myTimes=" + myTimes.size() + " myActors=" + myActors.size() + " myRelations=" + myRelations.size());
                continue;
            }

            FrameTypes.setEventTypeString(compositeEvent.getEvent(), nafSemParameters);
            compositeEventArraylist.add(compositeEvent);
        }

        logger.info("Serialize compositeEventSize="+compositeEventArraylist.size()+" for docId="+kafSaxParser.getDocId());
        serializeCompositeEvent(printStream, kafSaxParser, compositeEventArraylist, semEvents, semActors);
    }

    public static void serializeCompositeEvent(PrintStream printStream, KafSaxParser kafSaxParser, ArrayList<CompositeEvent> compositeEventArraylist, ArrayList<SemObject> semEvents, ArrayList<SemObject> semActors){
        if (!nafSemParameters.isPERSPECTIVE()) {
            JenaSerialization.serializeJenaCompositeEvents(printStream, compositeEventArraylist, null, nafSemParameters.isILIURI(), nafSemParameters.isVERBOSE());
        }
        else {
            ArrayList<PerspectiveObject> sourcePerspectives = GetPerspectiveRelations.getSourcePerspectives(kafSaxParser,
                    semActors,
                    semEvents,
                    nafSemParameters);
            ArrayList<PerspectiveObject> documentPerspectives = GetPerspectiveRelations.getAuthorPerspectives(
                    kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives);
            JenaSerialization.serializeJenaCompositeEventsAndPerspective(printStream, compositeEventArraylist, kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives, documentPerspectives);
        }
    }


    public static ByteArrayOutputStream getSem(KAFDocument kafDocument) throws UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.name());

        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseStringContent(kafDocument.toString());

        getSem(kafSaxParser, printStream);
        return byteArrayOutputStream;
    }
}