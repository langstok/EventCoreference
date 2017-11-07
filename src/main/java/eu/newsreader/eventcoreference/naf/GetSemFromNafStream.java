package eu.newsreader.eventcoreference.naf;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.newsreader.eventcoreference.configurationproperties.SemProperties;
import eu.newsreader.eventcoreference.coref.ComponentMatch;
import eu.newsreader.eventcoreference.objects.*;
import eu.newsreader.eventcoreference.output.JenaSerialization;
import eu.newsreader.eventcoreference.util.FrameTypes;
import eu.newsreader.eventcoreference.util.MD5Checksum;
import ixa.kaflib.KAFDocument;
import org.apache.jena.atlas.logging.Log;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by piek on 2/12/14.
 */
public class GetSemFromNafStream {
/*

    static final String USAGE = "This program processes a single NAF file and generates SEM RDF-TRiG results" +
            "The program has the following arguments:\n" +
            "--project              <string> <The name of the project for creating URIs>\n" +
            "--non-entities                  <If used, additional FrameNet roles and non-entity phrases are included>\n" +
            "--contextual-frames    <path>   <Path to a file with the FrameNet frames considered contextual>\n" +
            "--communication-frames <path>   <Path to a file with the FrameNet frames considered source>\n" +
            "--grammatical-frames   <path>   <Path to a file with the FrameNet frames considered grammatical>" +
            "--time-max   <string int>   <Maximum number of time-expressions allows for an event to be included in the output. Excessive time links are problematic. The defeault value is 5" +
            "--ili                  <(OPTIONAL) Path to ILI.ttl file to convert wordnet-synsets identifiers to ILI identifiers>\n" +
            "--ili-uri                  <(OPTIONAL) If used, the ILI-identifiers are used to represents events. This is necessary for cross-lingual extraction>\n" +
            "--verbose                  <(OPTIONAL) representation of mentions is extended with token ids, terms ids and sentence number\n"
            ;

    static public Vector<String> sourceVector = null;
    static public Vector<String> grammaticalVector = null;
    static public Vector<String> contextualVector = null;
    static public int TIMEEXPRESSIONMAX = 5;
    static public boolean NONENTITIES = false;
    static public boolean ILIURI = false;
    static public boolean VERBOSE = false;
    static public boolean PERSPECTIVE = false;

    static boolean DOCTIME = true;
    static boolean CONTEXTTIME = true;

    static boolean NOMCOREF = true;
    static boolean EVENTCOREF = true;
*/
    static public void main(String[] args) {
        Log.setLog4j("jena-log4j.properties");
        NafSemParameters nafSemParameters = new NafSemParameters(args);


        ArrayList<SemObject> semEvents = new ArrayList<SemObject>();
        ArrayList<SemObject> semActors = new ArrayList<SemObject>();
        ArrayList<SemTime> semTimes = new ArrayList<SemTime>();
        ArrayList<SemRelation> semRelations = new ArrayList<SemRelation>();
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(System.in);
        if (kafSaxParser.getKafMetaData().getUrl().isEmpty()) {
            //System.err.println("ERROR! Empty url in header NAF. Cannot create unique URIs! Aborting");
            try {
                String checkSum = MD5Checksum.getMD5ChecksumFromString(kafSaxParser.rawText);
              //  System.err.println("checkSum = " + checkSum);
                kafSaxParser.getKafMetaData().setUrl(checkSum);
            } catch (Exception e) {
             //   e.printStackTrace();
            }
        }
        GetSemFromNaf.processNafFile(nafSemParameters, kafSaxParser, semEvents, semActors, semTimes, semRelations );
        ArrayList<CompositeEvent> compositeEventArraylist = new ArrayList<CompositeEvent>();
        for (int j = 0; j < semEvents.size(); j++) {
            SemEvent mySemEvent = (SemEvent) semEvents.get(j);
            ArrayList<SemTime> myTimes = ComponentMatch.getMySemTimes(mySemEvent, semRelations, semTimes);
            ArrayList<SemActor> myActors = ComponentMatch.getMySemActors(mySemEvent, semRelations, semActors);
            ArrayList<SemRelation> myRelations = ComponentMatch.getMySemRelations(mySemEvent, semRelations);
            CompositeEvent compositeEvent = new CompositeEvent(mySemEvent, myActors, myTimes, myRelations);
            if (myTimes.size() <= nafSemParameters.getTIMEEXPRESSIONMAX()) {
                if (compositeEvent.isValid()) {
                    FrameTypes.setEventTypeString(compositeEvent.getEvent(), nafSemParameters);
                    compositeEventArraylist.add(compositeEvent);
                }
                else {
/*
                    System.out.println("Skipping EVENT due to no time anchor and/or no participant");
                    System.out.println("compositeEvent = " + compositeEvent.getEvent().getURI());
                    System.out.println("myTimes = " + myTimes.size());
                    System.out.println("myActors = " + myActors.size());
                    System.out.println("myRelations = " + myRelations.size());
*/
                }
            } else {
/*
                System.out.println("Skipping event due to excessive time expressions linked to it");
                System.out.println("compositeEvent = " + compositeEvent.getEvent().getURI());
                System.out.println("myTimes.size() = " + myTimes.size());
*/
            }
        }
        if (!nafSemParameters.isPERSPECTIVE()) {
            JenaSerialization.serializeJenaCompositeEvents(System.out, compositeEventArraylist, null, nafSemParameters.isILIURI(), nafSemParameters.isVERBOSE());
        }
        else {
            ArrayList<PerspectiveObject> sourcePerspectives = GetPerspectiveRelations.getSourcePerspectives(kafSaxParser,
                    semActors,
                    semEvents,
                    nafSemParameters);
            ArrayList<PerspectiveObject> documentPerspectives = GetPerspectiveRelations.getAuthorPerspectives(
                    kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives);
            JenaSerialization.serializeJenaCompositeEventsAndPerspective(System.out, compositeEventArraylist, kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives, documentPerspectives);
        }
    }


    private static NafSemParameters nafSemParameters = new NafSemParameters();
    private SemProperties semProperties;

    public GetSemFromNafStream(SemProperties semProperties) {
        this.semProperties = semProperties;
        init();
    }

    private void init(){
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


    static public String annotate(KAFDocument kafDocument) {
        ArrayList<SemObject> semEvents = new ArrayList<SemObject>();
        ArrayList<SemObject> semActors = new ArrayList<SemObject>();
        ArrayList<SemTime> semTimes = new ArrayList<SemTime>();
        ArrayList<SemRelation> semRelations = new ArrayList<SemRelation>();

        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseStringContent(kafDocument.toString());
        if (kafSaxParser.getKafMetaData().getUrl().isEmpty()) {
            //System.err.println("ERROR! Empty url in header NAF. Cannot create unique URIs! Aborting");
            try {
                String checkSum = MD5Checksum.getMD5ChecksumFromString(kafSaxParser.rawText);
                //  System.err.println("checkSum = " + checkSum);
                kafSaxParser.getKafMetaData().setUrl(checkSum);
            } catch (Exception e) {
                //   e.printStackTrace();
            }
        }
        GetSemFromNaf.processNafFile(nafSemParameters, kafSaxParser, semEvents, semActors, semTimes, semRelations );

        ArrayList<CompositeEvent> compositeEventArraylist = new ArrayList<CompositeEvent>();
        for (int j = 0; j < semEvents.size(); j++) {
            SemEvent mySemEvent = (SemEvent) semEvents.get(j);
            ArrayList<SemTime> myTimes = ComponentMatch.getMySemTimes(mySemEvent, semRelations, semTimes);
            ArrayList<SemActor> myActors = ComponentMatch.getMySemActors(mySemEvent, semRelations, semActors);
            ArrayList<SemRelation> myRelations = ComponentMatch.getMySemRelations(mySemEvent, semRelations);
            CompositeEvent compositeEvent = new CompositeEvent(mySemEvent, myActors, myTimes, myRelations);
            if (myTimes.size() <= nafSemParameters.getTIMEEXPRESSIONMAX()) {
                if (compositeEvent.isValid()) {
                    FrameTypes.setEventTypeString(compositeEvent.getEvent(), nafSemParameters);
                    compositeEventArraylist.add(compositeEvent);
                }
                else {
/*
                    System.out.println("Skipping EVENT due to no time anchor and/or no participant");
                    System.out.println("compositeEvent = " + compositeEvent.getEvent().getURI());
                    System.out.println("myTimes = " + myTimes.size());
                    System.out.println("myActors = " + myActors.size());
                    System.out.println("myRelations = " + myRelations.size());
*/
                }
            } else {
/*
                System.out.println("Skipping event due to excessive time expressions linked to it");
                System.out.println("compositeEvent = " + compositeEvent.getEvent().getURI());
                System.out.println("myTimes.size() = " + myTimes.size());
*/
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (!nafSemParameters.isPERSPECTIVE()) {
            JenaSerialization.serializeJenaCompositeEvents(outputStream, compositeEventArraylist, null, nafSemParameters.isILIURI(), nafSemParameters.isVERBOSE());
        }
        else {
            ArrayList<PerspectiveObject> sourcePerspectives = GetPerspectiveRelations.getSourcePerspectives(kafSaxParser,
                    semActors,
                    semEvents,
                    nafSemParameters);
            ArrayList<PerspectiveObject> documentPerspectives = GetPerspectiveRelations.getAuthorPerspectives(
                    kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives);
            JenaSerialization.serializeJenaCompositeEventsAndPerspective(outputStream, compositeEventArraylist, kafSaxParser, nafSemParameters.getPROJECT(), sourcePerspectives, documentPerspectives);
        }
        StringReader reader = new StringReader(outputStream.toString());
        Scanner scanner = new Scanner(reader).useDelimiter("\\A");
        String str = scanner.hasNext() ? scanner.next() : "";
        return str;
    }
}