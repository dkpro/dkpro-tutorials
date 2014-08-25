#!/usr/bin/env jython
"""
Reads in a specific text file and chunks the input text
Run by: jython chunking.py <filename> <language-code>
"""


# Fix classpath scanning - otherise uimaFIT will not find the UIMA types
from java.lang import Thread
from org.python.core.imp import *
Thread.currentThread().contextClassLoader = getSyspathJavaLoader()

# Dependencies and imports for DKPro modules
from jip.embed import require

# Text Reader
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.text-asl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.io.text import *


# OpenNlp
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.opennlp import *


# Dependencies for selecting specific annotations like sentences/tokens
from de.tudarmstadt.ukp.dkpro.core.api.segmentation.type import *
from de.tudarmstadt.ukp.dkpro.core.api.syntax.type import *

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
    TextReader.PARAM_LANGUAGE, sys.argv[2],
     ),
  createEngineDescription(OpenNlpSegmenter),
  createEngineDescription(OpenNlpChunker));

for jcas in pipeline:
    print "Named Entities:"
    for token in select(jcas, Token):
        print token.coveredText
