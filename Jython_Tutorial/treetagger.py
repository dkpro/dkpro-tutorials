#!/usr/bin/env jython
"""
Reads in a specific text file and runs first the OpenNlpSegmenter and then the TreeTagger for POS-tagging and lemmatisation.
Run by: jython pos.py <filename> <language-code>
"""

# Fix classpath scanning - otherise uimaFIT will not find the UIMA types
from java.lang import Thread
from org.python.core.imp import *
Thread.currentThread().contextClassLoader = getSyspathJavaLoader()

# Dependencies and imports for DKPro modules
from jip.embed import require
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.6.1')
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.treetagger-asl:1.6.1')
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.text-asl:1.6.1')
from de.tudarmstadt.ukp.dkpro.core.opennlp import *
from de.tudarmstadt.ukp.dkpro.core.treetagger import *
from de.tudarmstadt.ukp.dkpro.core.io.text import *
from de.tudarmstadt.ukp.dkpro.core.api.segmentation.type import *

# uimaFIT imports
from org.apache.uima.fit.util.JCasUtil import *
from org.apache.uima.fit.pipeline.SimplePipeline import *
from org.apache.uima.fit.factory.CollectionReaderFactory import *
from org.apache.uima.fit.factory.AnalysisEngineFactory import *

# Access to commandline arguments
import sys

# Check that all necessary arguments have been passed to the program
if len(sys.argv) < 3:
    print globals()['__doc__'] % locals()
    sys.exit(1)


# Assemble and run pipeline
pipeline = iteratePipeline(
  createReaderDescription(TextReader,
    TextReader.PARAM_PATH, sys.argv[1],
    TextReader.PARAM_LANGUAGE, sys.argv[2]),
  createEngineDescription(OpenNlpSegmenter),
  createEngineDescription(TreeTaggerPosLemmaTT4J,
    TreeTaggerPosLemmaTT4J.PARAM_EXECUTABLE_PATH, "tree-tagger", #!! Change to "tree-tagger.exe" if the script is executed under windows !!
    TreeTaggerPosLemmaTT4J.PARAM_MODEL_PATH, "english-par-linux-3.2-utf8.bin",
    TreeTaggerPosLemmaTT4J.PARAM_MODEL_ENCODING, "UTF-8"));

for jcas in pipeline:
  for token in select(jcas, Token):
    print token.coveredText + " " + token.pos.posValue + " " + token.lemma.value