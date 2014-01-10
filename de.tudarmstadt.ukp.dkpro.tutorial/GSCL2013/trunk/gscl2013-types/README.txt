This example demonstrates how to use custom annotation types.

The types are defined in the file

  src/main/resources/de/tudarmstadt/ukp/tutorial/gscl2013/types/typeSystemDescriptor.xml
  
This file can be opened using the UIMA Component Editor plugin for Eclipse. Use the "JCasGen" button
on the "Type system" pane there to generate the Java classes for these types:

  src/main/java/de/tudarmstadt/ukp/tutorial/gscl2013/types
    Location_Type.java
    Location.java
    Sentence_Type.java
    Sentence.java
    Token_Type.java
    Token.java
    
The types are made auto-detectable by uimaFIT using the configuration file

  src/main/resources/META-INF/org.apache.uima.fit/types.txt