package eu.newsreader.eventcoreference.input;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by piek on 05/02/16.
 */
public class TrigTripleReader {


    static public TrigTripleData readTripleFromTrigFiles (ArrayList<File> trigFiles) {
        TrigTripleData trigTripleData = new TrigTripleData();
        Dataset dataset = TDBFactory.createDataset();

        for (int i = 0; i < trigFiles.size(); i++) {
           // if (i==200) break;
            File file = trigFiles.get(i);
            //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            try {
                dataset = RDFDataMgr.loadDataset(file.getAbsolutePath());
                Iterator<String> it = dataset.listNames();
                while (it.hasNext()) {
                    String name = it.next();
                    // System.out.println("name = " + name);
                    if (name.equals(TrigTripleData.provenanceGraph)) {
                        Model namedModel = dataset.getNamedModel(name);
                        StmtIterator siter = namedModel.listStatements();
                        while (siter.hasNext()) {
                            Statement s = siter.nextStatement();
                            String subject = s.getSubject().getURI();
                            if (trigTripleData.tripleMapProvenance.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapProvenance.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapProvenance.put(subject, triples);
                            } else {

                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapProvenance.put(subject, triples);
                            }
                        }
                    } else if (name.equals(TrigTripleData.instanceGraph)) {
                        Model namedModel = dataset.getNamedModel(name);
                        StmtIterator siter = namedModel.listStatements();
                        while (siter.hasNext()) {
                            Statement s = siter.nextStatement();
                            String subject = s.getSubject().getURI();
                            if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            } else {

                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            }
                        }
                    }
                    else if (name.equals(TrigTripleData.graspGraph)) {
                        Model namedModel = dataset.getNamedModel(name);
                        StmtIterator siter = namedModel.listStatements();
                        while (siter.hasNext()) {
                            Statement s = siter.nextStatement();
                            String subject = s.getSubject().getURI();
                            String predicate = s.getPredicate().toString();
                            // System.out.println("predicate = " + predicate);
                            //http://groundedannotationframework.org/grasp#generatedBy
                            if (predicate.toLowerCase().endsWith("generatedby")) {
                                String object = s.getObject().toString();
                                if (!trigTripleData.perspectiveMentions.contains(object)) {
                                    //   System.out.println("object = " + object);
                                    trigTripleData.perspectiveMentions.add(object);
                                }
                            }
                            if (trigTripleData.tripleMapGrasp.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapGrasp.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapGrasp.put(subject, triples);
                            } else {

                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapGrasp.put(subject, triples);
                            }
                        }
                    } else {
                        Model namedModel = dataset.getNamedModel(name);
                        StmtIterator siter = namedModel.listStatements();
                        while (siter.hasNext()) {
                            Statement s = siter.nextStatement();
                            String subject = s.getSubject().getURI();
                            if (trigTripleData.tripleMapOthers.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapOthers.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapOthers.put(subject, triples);
                            } else {

                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapOthers.put(subject, triples);
                            }
                        }
                    }
                }
                dataset.close();
                dataset = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("trigTripleData instances = " + trigTripleData.tripleMapInstances.size());
        System.out.println("trigTripleData others = " + trigTripleData.tripleMapOthers.size());
        System.out.println("trigTripleData grasp = " + trigTripleData.tripleMapGrasp.size());
        return trigTripleData;
    }

    static public TrigTripleData readInstanceTripleFromTrigFiles (String STAT, ArrayList<File> trigFiles, int n) {
        TrigTripleData trigTripleData = new TrigTripleData();
        Dataset dataset = TDBFactory.createDataset();

        for (int i = 0; i < trigFiles.size(); i++) {
           // if (i==200) break;
            File file = trigFiles.get(i);
            //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            if (i%500==0) {
                System.out.println("i = " + i);
            }
            if (n>0 && i==n) {
                break;
            }
           // System.out.println("file.getName() = " + file.getName());
            try {
                dataset = RDFDataMgr.loadDataset(file.getAbsolutePath());
                Model namedModel = dataset.getNamedModel(TrigTripleData.instanceGraph);
                StmtIterator siter = namedModel.listStatements();
                while (siter.hasNext()) {
                    Statement s = siter.nextStatement();
                    String subject = s.getSubject().getURI();
                    //// store all label info
                    if (s.getPredicate().getLocalName().equals("label") ||
                        s.getPredicate().getLocalName().equals("count")
                            ){
                        if (trigTripleData.tripleMapLabels.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapLabels.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapLabels.put(subject, triples);
                        } else {
                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapLabels.put(subject, triples);
                        }
                    }
                    else if (STAT.isEmpty()) {
                        if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapInstances.put(subject, triples);
                        } else {
                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapInstances.put(subject, triples);
                        }
                    }
                    else if (STAT.equals("dbp")) {
                        if (subject.indexOf("dbpedia") >-1) {
                            if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            } else {
                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            }
                        }
                    }
                    else if (STAT.equals("en")) {
                        if (subject.indexOf("/entities/") >-1) {
                            if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            } else {
                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            }
                        }
                    }
                    else if (STAT.equals("ne")) {
                        if (subject.indexOf("/non-entities/") >-1) {
                            if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            } else {
                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            }
                        }
                    }
                    else if (STAT.equals("event")) {
                        if (subject.indexOf("#ev") >-1) {
                            if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                                ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            } else {
                                ArrayList<Statement> triples = new ArrayList<Statement>();
                                triples.add(s);
                                trigTripleData.tripleMapInstances.put(subject, triples);
                            }
                        }
                    }
                }
                dataset.close();
                dataset = null;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        System.out.println("trigTripleData labels = " + trigTripleData.tripleMapLabels.size());
        System.out.println("trigTripleData instances = " + trigTripleData.tripleMapInstances.size());
        return trigTripleData;
    }

    static public TrigTripleData readGraspTripleFromTrigFiles (ArrayList<File> trigFiles) {
        TrigTripleData trigTripleData = new TrigTripleData();
        Dataset dataset = TDBFactory.createDataset();

        for (int i = 0; i < trigFiles.size(); i++) {
           // if (i==200) break;
            File file = trigFiles.get(i);
            //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            try {
                dataset = RDFDataMgr.loadDataset(file.getAbsolutePath());
                Model namedModel = dataset.getNamedModel(TrigTripleData.graspGraph);
                StmtIterator siter = namedModel.listStatements();
                while (siter.hasNext()) {
                    Statement s = siter.nextStatement();
                    String subject = s.getSubject().getURI();
                    if (trigTripleData.tripleMapGrasp.containsKey(subject)) {
                        ArrayList<Statement> triples = trigTripleData.tripleMapGrasp.get(subject);
                        triples.add(s);
                        trigTripleData.tripleMapGrasp.put(subject, triples);
                    } else {

                        ArrayList<Statement> triples = new ArrayList<Statement>();
                        triples.add(s);
                        trigTripleData.tripleMapGrasp.put(subject, triples);
                    }
                }
                dataset.close();
                dataset = null;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        System.out.println("trigTripleData GRaSP = " + trigTripleData.tripleMapGrasp.size());
        return trigTripleData;
    }

    static public TrigTripleData readTripleFromTrigFile (File file) {
        TrigTripleData trigTripleData = new TrigTripleData();
        Dataset dataset = TDBFactory.createDataset();
        try {
            dataset = RDFDataMgr.loadDataset(file.getAbsolutePath());
            Iterator<String> it = dataset.listNames();
            while (it.hasNext()) {
                String name = it.next();
                // System.out.println("name = " + name);
                if (name.equals(TrigTripleData.provenanceGraph)) {
                    Model namedModel = dataset.getNamedModel(name);
                    StmtIterator siter = namedModel.listStatements();
                    while (siter.hasNext()) {
                        Statement s = siter.nextStatement();
                        String subject = s.getSubject().getURI();
                        if (trigTripleData.tripleMapProvenance.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapProvenance.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapProvenance.put(subject, triples);
                        } else {

                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapProvenance.put(subject, triples);
                        }
                    }
                } else if (name.equals(TrigTripleData.instanceGraph)) {
                    Model namedModel = dataset.getNamedModel(name);
                    StmtIterator siter = namedModel.listStatements();
                    while (siter.hasNext()) {
                        Statement s = siter.nextStatement();
                        String subject = s.getSubject().getURI();
                        if (trigTripleData.tripleMapInstances.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapInstances.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapInstances.put(subject, triples);
                        } else {

                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapInstances.put(subject, triples);
                        }
                    }
                }
                else if (name.equals(TrigTripleData.graspGraph)) {
                    Model namedModel = dataset.getNamedModel(name);
                    StmtIterator siter = namedModel.listStatements();
                    while (siter.hasNext()) {
                        Statement s = siter.nextStatement();
                        String subject = s.getSubject().getURI();
                        if (trigTripleData.tripleMapGrasp.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapGrasp.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapGrasp.put(subject, triples);
                        } else {

                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapGrasp.put(subject, triples);
                        }
                    }
                } else {
                    Model namedModel = dataset.getNamedModel(name);
                    StmtIterator siter = namedModel.listStatements();
                    while (siter.hasNext()) {
                        Statement s = siter.nextStatement();
                        String subject = s.getSubject().getURI();
                        if (trigTripleData.tripleMapOthers.containsKey(subject)) {
                            ArrayList<Statement> triples = trigTripleData.tripleMapOthers.get(subject);
                            triples.add(s);
                            trigTripleData.tripleMapOthers.put(subject, triples);
                        } else {

                            ArrayList<Statement> triples = new ArrayList<Statement>();
                            triples.add(s);
                            trigTripleData.tripleMapOthers.put(subject, triples);
                        }
                    }
                }
            }
            dataset.close();
            //dataset = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("trigTripleData instances = " + trigTripleData.tripleMapInstances.size());
        System.out.println("trigTripleData others = " + trigTripleData.tripleMapOthers.size());
        return trigTripleData;
    }
}
