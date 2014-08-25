#!/usr/bin/env jython
"""
Reads in a specific text file and runs different lemmatization algorithms.
Run by: jython lemmatization.py <filename> <language-code>
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

# ClearNlp
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.clearnlp-asl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.clearnlp import *

# StanfordNlp
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.stanfordnlp import *

# LanguageTool
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.languagetool-asl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.languagetool import *


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


# Function to print the detected sentences and tokens
def printLemmas(name, pipeline):
    print "\n\n%s: " % (name)
    for jcas in pipeline:
        for lemma in select(jcas, Lemma):            
            print("%s/%s " % (lemma.coveredText, lemma.value)),

#A common reader            
reader = createReaderDescription(TextReader,
                                TextReader.PARAM_PATH, sys.argv[1],
                                TextReader.PARAM_LANGUAGE, sys.argv[2])

# Stanford Tools
try:
    pipeline = iteratePipeline(
      reader,
      createEngineDescription(StanfordSegmenter),
      createEngineDescription(StanfordLemmatizer));  
    printLemmas('StanfordLemmatizer', pipeline)
except:
    print 'StanfordLemmatizer is not working'
    
# ClearNlp Tools
try:
    pipeline = iteratePipeline(
      reader,
      createEngineDescription(ClearNlpSegmenter),
      createEngineDescription(ClearNlpPosTagger), #Note, ClearNlpLemmatizer requires POS-tags
      createEngineDescription(ClearNlpLemmatizer));  
    printLemmas('ClearNlpLemmatizer', pipeline)
except:
    print 'ClearNlpLemmatizer is not working'
    
# Language Tools
try:
    pipeline = iteratePipeline(
      reader,
      createEngineDescription(LanguageToolSegmenter),
      createEngineDescription(LanguageToolLemmatizer));  
    printLemmas('LanguageToolLemmatizer', pipeline)
except:
    print 'LanguageToolLemmatizer is not working'

