package de.tudarmstadt.ukp.tutorial.gscl2013.ruta;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.ruta.engine.RutaEngine;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class WhatAliceDoesExample
{
	private static final String OUTPUT_BASE = "output/";

	public static void main(String[] args) throws Exception
    {
        // Configure the segmenter. It does sentence and token detection
        AnalysisEngineDescription segmenter = createEngineDescription(
                BreakIteratorSegmenter.class);

        // Configure the pos tagger
        AnalysisEngineDescription treetagger = createEngineDescription(
                OpenNlpPosTagger.class);

        AnalysisEngineDescription textmarker = createEngineDescription(
                RutaEngine.class,
                RutaEngine.PARAM_MAIN_SCRIPT, WhatAliceDoesExample.class.getName(),
                RutaEngine.PARAM_DEBUG, true);
                
        // Run pipeline
        runPipeline(
                createReaderDescription(TextReader.class,
                        TextReader.PARAM_LANGUAGE, "en",
                        TextReader.PARAM_SOURCE_LOCATION, "input",
                        TextReader.PARAM_PATTERNS, new String[] { "[+]carroll-alice.txt" }),

                segmenter,
                treetagger,
                textmarker,

                createEngineDescription(XmiWriter.class,
                        XmiWriter.PARAM_TARGET_LOCATION, OUTPUT_BASE,
                        XmiWriter.PARAM_TYPE_SYSTEM_FILE, new File("TypeSystem.xml").getAbsolutePath()));
    }
}
