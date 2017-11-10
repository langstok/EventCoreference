package eu.newsreader.eventcoreference.configurationproperties;

import java.util.Arrays;
import java.util.List;

public abstract class EventCorefProperties {


	/**
	 * Resources https://github.com/cltl/vua-resources.git
	 */
	private String resources = "../resources/";

	/**
	 * path to wordnet file in lmf format
	 */
	private String wnLlmf =  "wneng-30.lmf.xml.xpos.gz";

	/**
	 * one of the following methods can be used leacock-chodorow, path, wu-palmer
	 */
	private String method = "leacock-chodorow";

	/**
	 * similarity threshold (double, e.g. 2.5) below which no coreference is no coreference relation is determined
	 */
	private double sim = 2.0;

	/**
	 * fall back similarity threshold
	 * (double, e.g. 1.5) if sim is below threshold but there is still an ontology match (ESO or FrameNet)
	 *  below which no coreference is no coreference relation is determined.
	 * This threshold needs to be lower than sim and wordnet-lmf need to have the ESO and FrameNet ontology layer
	 */
	private double simOnt = 0.6;

	/**
	 * synsets relations that are used for the distance measurement
	 */
	private List<String> relations = Arrays.asList(new String[]{"has_hyperonym","event","has_hypernym"});

	/**
	 * maximum number of lowest-common-subsumers allowed
	 */
	private int driftMax = -1;

	/**
	 * all senses from WSD with proportional score above threshold (double) are used, e.g. 0.8 means all senses proportionally scoring 80% of the best scoring sense
	 */
	private double wsd = 0.8;

	/**
	 * three letter prefix of the wordnet synset identifier in the
	 */
	private String wnPrefix = "eng";

	/**
	 * if terms have been scored by different systems, e.g. ukb or ims,
	 * you can restrict the wsd to a system by giving the name.
	 * This will match the source field in the external reference of the term.
	 */
	private String wnSource = "";

	/**
	 * List of framenet frames to be treates as source events. No coreference is applied
	 */
	private String sourceFramesPath = resources + "source.txt";

	/**
	 * List of framenet frames to be treates as contexual events. Coreference is applied
	 */
	private String contexualFramesPath = resources + "contextual.txt";

	/**
	 * List of framenet frames to be treates as grammatical events. No coreference is applied
	 */
	private String grammaticalFramesPath = resources + "grammatical.txt";

	/**
	 * Using this flag previous output of event-coreference is removed first
	 */
	private boolean replace = false;

	/**
	 * Using this flag removes srl with predicate status = "false"
	 */
	private boolean ignoreFalse = true;

	public String getWnLlmf() {
		return wnLlmf;
	}

	public void setWnLlmf(String wnLlmf) {
		this.wnLlmf = wnLlmf;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public double getSim() {
		return sim;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}

	public double getSimOnt() {
		return simOnt;
	}

	public void setSimOnt(double simOnt) {
		this.simOnt = simOnt;
	}

	public List<String> getRelations() {
		return relations;
	}

	public void setRelations(List<String> relations) {
		this.relations = relations;
	}

	public int getDriftMax() {
		return driftMax;
	}

	public void setDriftMax(int driftMax) {
		this.driftMax = driftMax;
	}

	public double getWsd() {
		return wsd;
	}

	public void setWsd(double wsd) {
		this.wsd = wsd;
	}

	public String getWnPrefix() {
		return wnPrefix;
	}

	public void setWnPrefix(String wnPrefix) {
		this.wnPrefix = wnPrefix;
	}

	public String getWnSource() {
		return wnSource;
	}

	public void setWnSource(String wnSource) {
		this.wnSource = wnSource;
	}

	public String getSourceFramesPath() {
		return sourceFramesPath;
	}

	public void setSourceFramesPath(String sourceFramesPath) {
		this.sourceFramesPath = sourceFramesPath;
	}

	public String getContexualFramesPath() {
		return contexualFramesPath;
	}

	public void setContexualFramesPath(String contexualFramesPath) {
		this.contexualFramesPath = contexualFramesPath;
	}

	public String getGrammaticalFramesPath() {
		return grammaticalFramesPath;
	}

	public void setGrammaticalFramesPath(String grammaticalFramesPath) {
		this.grammaticalFramesPath = grammaticalFramesPath;
	}

	public boolean isReplace() {
		return replace;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}

	public boolean isIgnoreFalse() {
		return ignoreFalse;
	}

	public void setIgnoreFalse(boolean ignoreFalse) {
		this.ignoreFalse = ignoreFalse;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}
}
