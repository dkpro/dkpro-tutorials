#!/usr/bin/env jython
# Fix classpath scanning - otherise uimaFIT will not find the UIMA types
from java.lang import Thread
from org.python.core.imp import *
Thread.currentThread().contextClassLoader = getSyspathJavaLoader()

# Dependencies and imports for DKPro modules
from jip.embed import require
require('de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.6.2')
from de.tudarmstadt.ukp.dkpro.core.opennlp import *
from de.tudarmstadt.ukp.dkpro.core.api.segmentation.type import *
from de.tudarmstadt.ukp.dkpro.core.api.syntax.type import *

# uimaFIT imports
from org.apache.uima.fit.util.JCasUtil import *
from org.apache.uima.fit.pipeline.SimplePipeline import *
from org.apache.uima.fit.factory.AnalysisEngineFactory import *
from org.apache.uima.fit.factory import JCasFactory


# First, define a class which helps us to create an own document reader
class MyDocumentReader(object):	
	def internalReadDocument(self):
		""" Implement here your document reader. This example returns sentences from a defined array with sentences.
		
		Returns: A list, first element is the document language, second is the document text
		"""
		documents = ['This is a test.', 'To test my first own reader.', 'This function returns each sentence as a new jcas. This jcas is then processed by different engines. The output is written to the console.']
		documentLanguage = "en"
		for documentText in documents:		
			yield documentLanguage, documentText
			
	def pipelineComponents(self, *args):
		""" Call this function to specific your pipeline components, e.g. a segmenter, POS tagger, chunker etc."""
		aaeDesc = createEngineDescription(args);
		self.processEngine = createEngine(aaeDesc);
	
	
	def read(self):
		""" This function yields the jcas with the specified components applied"""
		jcas = JCasFactory.createJCas()
		
		for documentLanguage, document in self.internalReadDocument():			
			jcas.reset()
			jcas.documentText = document
			jcas.documentLanguage = documentLanguage
			self.processEngine.process(jcas)
			yield jcas
					
	
######################################
#
# A small example how MyDocumentReader can be used 
#
######################################

reader = MyDocumentReader()
reader.pipelineComponents(
						createEngineDescription(OpenNlpSegmenter), 
						createEngineDescription(OpenNlpPosTagger)
						)

# Now we can iterate through the jcas returned by MyDocumentReader
for jcas in reader.read():	
	print "\n\n ------- New Document ------- \n\n"
	for token in select(jcas, Token):
		  print token.coveredText + " " + token.pos.posValue

