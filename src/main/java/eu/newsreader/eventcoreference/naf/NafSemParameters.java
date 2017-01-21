package eu.newsreader.eventcoreference.naf;

import eu.newsreader.eventcoreference.output.JenaSerialization;
import eu.newsreader.eventcoreference.util.Util;

import java.util.Vector;

/**
 * Created by piek on 20/01/2017.
 */
public class NafSemParameters {


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
            "--verbose                  <(OPTIONAL) representation of mentions is extended with token ids, terms ids and sentence number\n" +
            "--no-additional-role       <(OPTIONAL) only roles for entities are extracted" +
            "--no-nomcoref          <(OPTIONAL) nominal coreference layer is ignored\n" +
            "--no-eventcoref          <(OPTIONAL) event coreference layer is ignored\n"
            ;

    private Vector<String> sourceVector = null;
    private Vector<String> grammaticalVector = null;
    private Vector<String> contextualVector = null;
    private int TIMEEXPRESSIONMAX = 5;
    private boolean ALL = true;
    private boolean NONENTITIES = false;
    private boolean ILIURI = false;
    private boolean VERBOSE = false;
    private boolean PERSPECTIVE = false;
    private String PROJECT = "";
    private boolean DOCTIME = true;
    private boolean CONTEXTTIME = true;
    private boolean ADDITIONALROLES;
    private boolean NOMCOREF = true;
    private boolean EVENTCOREF = true;

    public NafSemParameters () {
        init();
    }

    void init () {

        Vector<String> sourceVector = null;
        Vector<String> grammaticalVector = null;
        Vector<String> contextualVector = null;
        ALL = true;
        PROJECT = "";
        TIMEEXPRESSIONMAX = 5;
        NONENTITIES = false;
        ILIURI = false;
        VERBOSE = false;
        PERSPECTIVE = false;
        DOCTIME = true;
        CONTEXTTIME = true;
        NOMCOREF = true;
        EVENTCOREF = true;
        ADDITIONALROLES = true;
    }

    public NafSemParameters (String [] args) {
        init();
        readParameters(args);
    }

    public static String getUSAGE() {
        return USAGE;
    }

    public Vector<String> getSourceVector() {
        return sourceVector;
    }

    public void setSourceVector(Vector<String> sourceVector) {
        this.sourceVector = sourceVector;
    }

    public Vector<String> getGrammaticalVector() {
        return grammaticalVector;
    }

    public void setGrammaticalVector(Vector<String> grammaticalVector) {
        this.grammaticalVector = grammaticalVector;
    }

    public Vector<String> getContextualVector() {
        return contextualVector;
    }

    public void setContextualVector(Vector<String> contextualVector) {
        this.contextualVector = contextualVector;
    }

    public int getTIMEEXPRESSIONMAX() {
        return TIMEEXPRESSIONMAX;
    }

    public void setTIMEEXPRESSIONMAX(int TIMEEXPRESSIONMAX) {
        this.TIMEEXPRESSIONMAX = TIMEEXPRESSIONMAX;
    }

    public boolean isNONENTITIES() {
        return NONENTITIES;
    }

    public void setNONENTITIES(boolean NONENTITIES) {
        this.NONENTITIES = NONENTITIES;
    }

    public boolean isILIURI() {
        return ILIURI;
    }

    public void setILIURI(boolean ILIURI) {
        this.ILIURI = ILIURI;
    }

    public boolean isVERBOSE() {
        return VERBOSE;
    }

    public void setVERBOSE(boolean VERBOSE) {
        this.VERBOSE = VERBOSE;
    }

    public boolean isPERSPECTIVE() {
        return PERSPECTIVE;
    }

    public void setPERSPECTIVE(boolean PERSPECTIVE) {
        this.PERSPECTIVE = PERSPECTIVE;
    }

    public boolean isDOCTIME() {
        return DOCTIME;
    }

    public void setDOCTIME(boolean DOCTIME) {
        this.DOCTIME = DOCTIME;
    }

    public boolean isCONTEXTTIME() {
        return CONTEXTTIME;
    }

    public void setCONTEXTTIME(boolean CONTEXTTIME) {
        this.CONTEXTTIME = CONTEXTTIME;
    }

    public boolean isNOMCOREF() {
        return NOMCOREF;
    }

    public void setNOMCOREF(boolean NOMCOREF) {
        this.NOMCOREF = NOMCOREF;
    }

    public boolean isEVENTCOREF() {
        return EVENTCOREF;
    }

    public void setEVENTCOREF(boolean EVENTCOREF) {
        this.EVENTCOREF = EVENTCOREF;
    }

    public String getPROJECT() {
        return PROJECT;
    }

    public void setPROJECT(String PROJECT) {
        this.PROJECT = PROJECT;
    }

    public boolean isALL() {
        return ALL;
    }

    public void setALL(boolean ALL) {
        this.ALL = ALL;
    }

    public boolean isADDITIONALROLES() {
        return ADDITIONALROLES;
    }

    public void setADDITIONALROLES(boolean ADDITIONALROLES) {
        this.ADDITIONALROLES = ADDITIONALROLES;
    }

    public void readParameters (String [] args) {
        String sourceFrameFile = "";
        String contextualFrameFile = "";
        String grammaticalFrameFile = "";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--project") && args.length > (i + 1)) {
                PROJECT = args[i + 1];
            } else if (arg.equals("--non-entities")) {
                NONENTITIES = true;
            }
            else if (arg.equals("--verbose")) {
                VERBOSE = true;
            }
            else if (arg.equals("--perspective")) {
                PERSPECTIVE = true;
            }
            else if (arg.equals("--no-doc-time")) {
                DOCTIME = false;
            }
            else if (arg.equals("--no-context-time")) {
                CONTEXTTIME = false;
            }
            else if (arg.equals("--no-additional-roles")) {
                ADDITIONALROLES = false;
            }
            else if (arg.equals("--all")) {
                ALL = true;
            }
            else if (arg.equals("--eurovoc-en") && args.length > (i + 1)) {
                String pathToEurovocFile = args[i+1];
                GetSemFromNaf.initEurovoc(pathToEurovocFile, "en");
            }
            else if (arg.equals("--eurovoc-nl") && args.length > (i + 1)) {
                String pathToEurovocFile = args[i+1];
                GetSemFromNaf.initEurovoc(pathToEurovocFile, "nl");
            }
            else if (arg.equals("--eurovoc-es") && args.length > (i + 1)) {
                String pathToEurovocFile = args[i+1];
                GetSemFromNaf.initEurovoc(pathToEurovocFile, "es");
            }
            else if (arg.equals("--eurovoc-it") && args.length > (i + 1)) {
                String pathToEurovocFile = args[i+1];
                GetSemFromNaf.initEurovoc(pathToEurovocFile, "it");
            }
            else if (arg.equals("--ili") && args.length > (i + 1)) {
                String pathToILIFile = args[i+1];
                JenaSerialization.initILI(pathToILIFile);
            }
            else if (arg.equals("--ili-uri")) {
                ILIURI = true;
            }
            else if (arg.equals("--time-max") && args.length > (i + 1)) {
                try {
                    TIMEEXPRESSIONMAX = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (arg.equals("--source-frames") && args.length > (i + 1)) {
                sourceFrameFile = args[i + 1];
            } else if (arg.equals("--grammatical-frames") && args.length > (i + 1)) {
                grammaticalFrameFile = args[i + 1];
            } else if (arg.equals("--contextual-frames") && args.length > (i + 1)) {
                contextualFrameFile = args[i + 1];
            }
        }
        sourceVector = Util.ReadFileToStringVector(sourceFrameFile);
        grammaticalVector = Util.ReadFileToStringVector(grammaticalFrameFile);
        contextualVector = Util.ReadFileToStringVector(contextualFrameFile);
    }
}
