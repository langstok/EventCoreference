package eu.newsreader.eventcoreference.configurationproperties;


public abstract class SemFromNafStreamProperties {

    /**
     * Extract all events, including events without time and without participants
     */
    private boolean all = true;

    /**
     * Include additional FrameNet roles and non-entity phrases
     */
    private boolean nonEntities = false;

    /**
     * ILI-identifiers are used to represents events. This is necessary for cross-lingual extraction
     */
    private boolean iliUri = false;


    /**
     * representation of mentions is extended with token ids, terms ids and sentence number
     */
    private boolean verbose = false;

    /**
     * GRaSP layer is included in the output
     */
    private boolean perspective = false;

    /**
     * The name of the project for creating project-specific URIs within the NewsReader domain
     */
    private String project = "";

    /**
     * consider document creation time
     */
    private boolean docTime = true;


    /**
     * time expressions of preceding and following sentences are associated with events
     */
    private boolean contexttime = true;


    /**
     * only roles for entities are extracted
     */
    private boolean additionalRoles;


    /**
     * nominal coreference layer is ignore
     */
    private boolean nomCoref = true;

    /**
     * event coreference layer is ignored
     */
    private boolean eventCoref = true;

    /**
     * Eurovoc resource to map topic labels to topic concept identifiers
     */
    private String pathToEurovocFile = "";

    /**
     * Path to ILI.ttl file to convert wordnet-synsets identifiers to ILI identifiers
     */
    private String pathToILIFile = "";

    /**
     * Maximum number of time-expressions allows for an event to be included in the output. Excessive time links are problematic. The defeault value is 5
     */
    private int timeMax = 5;

    /**
     * Path to a file with the FrameNet frames considered sourc
     */
    private String sourceFrames = "";

    /**
     * Path to a file with the FrameNet frames considered grammatical
     */
    private String grammaticalFrames = "";

    /**
     * Path to a file with the FrameNet frames considered contextual
     */
    private String contextualFrames = "";


    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isNonEntities() {
        return nonEntities;
    }

    public void setNonEntities(boolean nonEntities) {
        this.nonEntities = nonEntities;
    }

    public boolean isIliUri() {
        return iliUri;
    }

    public void setIliUri(boolean iliUri) {
        this.iliUri = iliUri;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isPerspective() {
        return perspective;
    }

    public void setPerspective(boolean perspective) {
        this.perspective = perspective;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public boolean isDocTime() {
        return docTime;
    }

    public void setDocTime(boolean docTime) {
        this.docTime = docTime;
    }

    public boolean isContexttime() {
        return contexttime;
    }

    public void setContexttime(boolean contexttime) {
        this.contexttime = contexttime;
    }

    public boolean isAdditionalRoles() {
        return additionalRoles;
    }

    public void setAdditionalRoles(boolean additionalRoles) {
        this.additionalRoles = additionalRoles;
    }

    public boolean isNomCoref() {
        return nomCoref;
    }

    public void setNomCoref(boolean nomCoref) {
        this.nomCoref = nomCoref;
    }

    public boolean isEventCoref() {
        return eventCoref;
    }

    public void setEventCoref(boolean eventCoref) {
        this.eventCoref = eventCoref;
    }

    public String getPathToEurovocFile() {
        return pathToEurovocFile;
    }

    public void setPathToEurovocFile(String pathToEurovocFile) {
        this.pathToEurovocFile = pathToEurovocFile;
    }

    public String getPathToILIFile() {
        return pathToILIFile;
    }

    public void setPathToILIFile(String pathToILIFile) {
        this.pathToILIFile = pathToILIFile;
    }

    public int getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public String getSourceFrames() {
        return sourceFrames;
    }

    public void setSourceFrames(String sourceFrames) {
        this.sourceFrames = sourceFrames;
    }

    public String getGrammaticalFrames() {
        return grammaticalFrames;
    }

    public void setGrammaticalFrames(String grammaticalFrames) {
        this.grammaticalFrames = grammaticalFrames;
    }

    public String getContextualFrames() {
        return contextualFrames;
    }

    public void setContextualFrames(String contextualFrames) {
        this.contextualFrames = contextualFrames;
    }
}
