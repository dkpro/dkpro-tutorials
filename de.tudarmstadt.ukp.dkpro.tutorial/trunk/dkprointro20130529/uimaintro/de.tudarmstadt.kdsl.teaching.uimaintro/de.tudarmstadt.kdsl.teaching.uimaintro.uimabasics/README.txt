The Pipeline
============
(Class: de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.UimaBasicsPipeline)

The pipeline 
 - demonstrates the structure of a typical pipeline (Reader, AE(s), Consumer(s))
 - presents some core components (text reader, segmenter,...)

Please, take a look at the implementation of the components labeled as "custom" 
in order to get a feeling of how to implement your own ones.

The Type System
===============
(File: src/main/resources/desc/types/TypeSystem.xml)

The type system is defined in src/main/resources/desc/types/TypeSystem.xml. In order 
for uimaFit to know where to find it, we need to provide the lookup file 
META-INF/org.uimafit/types.txt as a classpath resource.

Dependencies
============
(File: pom.xml)

All DKPro-related dependencies are managed by the aggregator/parent project. It is 
good practice to set the artifact versions to be used in the Dependency Management
section. The string ${uimaintro.version} is a property which is defined in the 
parent project.