package eu.newsreader.eventcoreference.objects;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/28/12
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SemEvent {
  /*
  <semEvent id="e30" lcs="raid" score="2.4849066497880004" synset="eng-30-02020027-v" label="raid" mentions="2">
	<mentions>
	<event-mention>
		<event>
			<target termId="t285" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="eng-30-02020027-v" rank="0.257681" word="raid"/>
		<event>
		<participants>
			<participant id="p30" lcs="eng-30-00007846-n" score="2.639057329615259" synset="eng-30-10210137-n" label="rebel" mentions="26">
					<target termId="t288" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-11346710-n" rank="0.227748" word="town"/>
			</participant>
			<participant id="p93" lcs="" score="0.0" synset="" label="Khalanga" mentions="1">
					<target termId="t2810" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="" rank="0.0" word="Khalanga"/>
			</participant>
			<participant id="p34" lcs="eng-30-08008335-n" score="2.639057329615259" synset="eng-30-08209687-n" label="police" mentions="16">
					<target termId="t2827" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-08337324-n" rank="0.143377" word="office"/>
					<target termId="t2830" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-08051946-n" rank="0.0895559" word="court"/>
			</participant>
		</participants>
		<times>
			<time id="e3" lcs="eng-30-15163157-n" score="2.890371757896165" synset="eng-30-15163979-n" label="Monday" mentions="9">
					<target termId="t284" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="eng-30-15164570-n" rank="1.0" word="Saturday"/>
			</time>
		</times>
		<locations>
		</locations>
	</event-mention>

   */

   private String id;
   private double score;
   private String synset;
   private String lcs;
   private String label;
   private int mentions;
   private ArrayList<EventMention> eventMentions;

   public SemEvent() {
        this.eventMentions = new ArrayList<EventMention>();;
        this.id = "";
        this.label = "";
        this.lcs = "";
        this.mentions = 0;
        this.score = 0;
        this.synset = "";
    }


    public ArrayList<EventMention> getEventMentions() {
        return eventMentions;
    }

    public void setEventMentions(ArrayList<EventMention> eventMentions) {
        this.eventMentions = eventMentions;
    }

    public void addEventMentions(EventMention eventMention) {
        this.eventMentions.add(eventMention);
    }

    public String getLcs() {
        return lcs;
    }

    public void setLcs(String lcs) {
        this.lcs = lcs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSynset() {
        return synset;
    }

    public void setSynset(String synset) {
        this.synset = synset;
    }

    
    public String toString () {
        String str = "<semEvent id=\""+id+"\" lcs=\""+lcs+"\" score=\""+score+"\" synset=\""+synset+"\" label=\""+label+"\" mentions=\""+mentions+"\">\n";
        str += "<mentions>\n";
        for (int i = 0; i < eventMentions.size(); i++) {
            EventMention eventMention = eventMentions.get(i);
            str += eventMention.toString();
        }
        str += "</mentions>\n";
        str += "</semEvent>\n";
        return str;
    }


    /*
   <semEvent id="e30" lcs="raid" score="2.4849066497880004" synset="eng-30-02020027-v" label="raid" mentions="2">
     <mentions>
     <event-mention>
         <event>
             <target termId="t285" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="eng-30-02020027-v" rank="0.257681" word="raid"/>
         <event>
         <participants>
             <participant id="p30" lcs="eng-30-00007846-n" score="2.639057329615259" synset="eng-30-10210137-n" label="rebel" mentions="26">
                     <target termId="t288" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-11346710-n" rank="0.227748" word="town"/>
             </participant>
             <participant id="p93" lcs="" score="0.0" synset="" label="Khalanga" mentions="1">
                     <target termId="t2810" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="" rank="0.0" word="Khalanga"/>
             </participant>
             <participant id="p34" lcs="eng-30-08008335-n" score="2.639057329615259" synset="eng-30-08209687-n" label="police" mentions="16">
                     <target termId="t2827" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-08337324-n" rank="0.143377" word="office"/>
                     <target termId="t2830" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.5596157879354228" synset="eng-30-08051946-n" rank="0.0895559" word="court"/>
             </participant>
         </participants>
         <times>
             <time id="e3" lcs="eng-30-15163157-n" score="2.890371757896165" synset="eng-30-15163979-n" label="Monday" mentions="9">
                     <target termId="t284" docId="AFP_ENG_20040823.0382.src.xml.txt.blk.tok.stp.tbf.xml.isi-term.ont.kaf" sentenceId="28" corefScore="0.0" synset="eng-30-15164570-n" rank="1.0" word="Saturday"/>
             </time>
         </times>
         <locations>
         </locations>
     </event-mention>
 
    */
}
