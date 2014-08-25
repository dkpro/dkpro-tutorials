#!/usr/bin/env jython
"""
Reads in a specific text file and prints a parse tree following the conventions of Penn TreeBank
Run by: jython parsing_constituency.py <filename> <language-code>
"""


# Fix classpath scanning - otherise uimaFIT will not find the UIMA types
from java.lang import Thread
from org.python.core.imp import *
Thread.currentThread().contextClassLoader = getSyspathJavaLoader()

# Dependencies and imports for DKPro modules
from jip.embed import require

# Text Reader
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.text-asl:1.6.1')
from de.tudarmstadt.ukp.dkpro.core.io.text import *

# OpenNlp
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.6.1')
from de.tudarmstadt.ukp.dkpro.core.opennlp import *

# StanfordNlp
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl:1.6.1')
from de.tudarmstadt.ukp.dkpro.core.stanfordnlp import *

# Berkeley
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.berkeleyparser-gpl:1.6.1')
from de.tudarmstadt.ukp.dkpro.core.berkeleyparser import *

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
    
# Function to print the parser output
def printParseOutput(name, pipeline):
    print "\n\n%s:" % name
    for jcas in pipeline:
        for tree in select(jcas, PennTree):
            print tree.pennTree

# Our text reader which we use for all parser
reader = createReaderDescription(TextReader,
    TextReader.PARAM_PATH, sys.argv[1],
    TextReader.PARAM_LANGUAGE, sys.argv[2],
     )


# Run OpenNlpParser
try:
    pipeline = iteratePipeline(
      reader,
      createEngineDescription(OpenNlpSegmenter),
      createEngineDescription(OpenNlpPosTagger),
      createEngineDescription(OpenNlpParser,                          
        OpenNlpParser.PARAM_WRITE_PENN_TREE, True)
    );
    
    printParseOutput("OpenNlpParser", pipeline)
except:
    print 'OpenNlpParser is not working'
    
    
# Run StanfordParser
try:
    pipeline = iteratePipeline(
          reader,
          createEngineDescription(StanfordSegmenter),
          createEngineDescription(StanfordPosTagger),
          createEngineDescription(StanfordParser,                          
            StanfordParser.PARAM_WRITE_PENN_TREE, True)
        );
        
    printParseOutput("StanfordParser", pipeline)
except:
    print 'StanfordParser is not working'
    

# Run BerkeleyParser
try:
    pipeline = iteratePipeline(
          reader,
          createEngineDescription(OpenNlpSegmenter),
          createEngineDescription(OpenNlpPosTagger),
          createEngineDescription(BerkeleyParser,                          
            BerkeleyParser.PARAM_WRITE_PENN_TREE, True)
        );
        
    printParseOutput("BerkeleyParser", pipeline)
except:
    print 'BerkeleyParser is not working'
    
