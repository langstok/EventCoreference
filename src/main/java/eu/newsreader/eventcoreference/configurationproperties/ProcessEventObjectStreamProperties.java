package eu.newsreader.eventcoreference.configurationproperties;

public abstract class ProcessEventObjectStreamProperties {

    /**
     * threshold for conceptual matches of events, default is 50
     */
    private int conceptMatch = 50;


    /**
     * threshold for phrase matches of events, default is 50
     */
    private int phraseMatch = 50;

    /**
     *  Indicates what is used to match events across resources.
     *  Default value is "ILILEMMA". Values: "LEMMA", "ILI", "ILILEMMA"
     */
    private String contextualMatchType = "ILILEMMA";


    /**
     * Use lowest-common-subsumers. Default value is ON
     */
    private boolean contextualLcs = true;


    /**
     * String with roles for which there must be a match, e.g. "pb:A1, sem:hasActor"
     */
    private String contextualRoles = "";


    /**
     * Indicates what is used to match events across resources. Default value is \"ILILEMMA\". Values:\"LEMMA\", \"ILI\", \"ILILEMMA\">\n" +
     */
    private String sourceMatchType = "ILILEMMA";


    /**
     * Use lowest-common-subsumers. Default value is OFF.>\n" +
     */
    private boolean sourceLcs = false;


    /**
     * String with roles for which there must be a match, e.g. "pb:A1, sem:hasActor"
     */
    private String sourceRoles = "pb:A0, pb:A1";


    /**
     * Indicates what is used to match events across resources. Default value is "LEMMA". Values:"LEMMA", "ILI", "ILILEMMA"
     */
    private String grammaticalMatchType = "LEMMA";


    /**
     * Use lowest-common-subsumers. Default value is OFF.
     */
    private boolean grammaticalLcs = false;


    /**
     * String with roles for which there must be a match, e.g. \"pb:A1, sem:hasActor\
     */
    private String grammaticalRoles = "";


    /**
     * Indicates what is used to match events across resources. Default value is "LEMMA". Values:"LEMMA", "ILI", "ILILEMMA\
     */
    private String futureMatchType = "LEMMA";


    /**
     * Use lowest-common-subsumers. Default value is OFF.
     */
    private boolean futureLcs = false;

    /**
     * Amount of past days which are still considered recent and are treated differently
     */
    private int recentSpan;


    private String knowledgeStore;

    private String userName;

    private String password;


    public int getConceptMatch() {
        return conceptMatch;
    }

    public void setConceptMatch(int conceptMatch) {
        this.conceptMatch = conceptMatch;
    }

    public int getPhraseMatch() {
        return phraseMatch;
    }

    public void setPhraseMatch(int phraseMatch) {
        this.phraseMatch = phraseMatch;
    }

    public String getContextualMatchType() {
        return contextualMatchType;
    }

    public void setContextualMatchType(String contextualMatchType) {
        this.contextualMatchType = contextualMatchType;
    }

    public boolean isContextualLcs() {
        return contextualLcs;
    }

    public void setContextualLcs(boolean contextualLcs) {
        this.contextualLcs = contextualLcs;
    }

    public String getContextualRoles() {
        return contextualRoles;
    }

    public void setContextualRoles(String contextualRoles) {
        this.contextualRoles = contextualRoles;
    }

    public String getSourceMatchType() {
        return sourceMatchType;
    }

    public void setSourceMatchType(String sourceMatchType) {
        this.sourceMatchType = sourceMatchType;
    }

    public boolean isSourceLcs() {
        return sourceLcs;
    }

    public void setSourceLcs(boolean sourceLcs) {
        this.sourceLcs = sourceLcs;
    }

    public String getSourceRoles() {
        return sourceRoles;
    }

    public void setSourceRoles(String sourceRoles) {
        this.sourceRoles = sourceRoles;
    }

    public String getGrammaticalMatchType() {
        return grammaticalMatchType;
    }

    public void setGrammaticalMatchType(String grammaticalMatchType) {
        this.grammaticalMatchType = grammaticalMatchType;
    }

    public boolean isGrammaticalLcs() {
        return grammaticalLcs;
    }

    public void setGrammaticalLcs(boolean grammaticalLcs) {
        this.grammaticalLcs = grammaticalLcs;
    }

    public String getGrammaticalRoles() {
        return grammaticalRoles;
    }

    public void setGrammaticalRoles(String grammaticalRoles) {
        this.grammaticalRoles = grammaticalRoles;
    }

    public String getFutureMatchType() {
        return futureMatchType;
    }

    public void setFutureMatchType(String futureMatchType) {
        this.futureMatchType = futureMatchType;
    }

    public boolean isFutureLcs() {
        return futureLcs;
    }

    public void setFutureLcs(boolean futureLcs) {
        this.futureLcs = futureLcs;
    }

    public int getRecentSpan() {
        return recentSpan;
    }

    public void setRecentSpan(int recentSpan) {
        this.recentSpan = recentSpan;
    }

    public String getKnowledgeStore() {
        return knowledgeStore;
    }

    public void setKnowledgeStore(String knowledgeStore) {
        this.knowledgeStore = knowledgeStore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
