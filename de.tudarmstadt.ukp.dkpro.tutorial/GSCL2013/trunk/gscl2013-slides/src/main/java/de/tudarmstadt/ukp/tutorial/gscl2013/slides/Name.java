

/* First created by JCasGen Sun Sep 22 20:00:11 CEST 2013 */
package de.tudarmstadt.ukp.tutorial.gscl2013.slides;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Sep 22 20:01:52 CEST 2013
 * XML source: /Users/bluefire/UKP/Workspaces/dkpro-juno/gscl2013-slides/src/main/resources/de/tudarmstadt/ukp/tutorial/gscl2013/slides/typeSystemDescriptor.xml
 * @generated */
public class Name extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Name.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Name() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Name(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Name(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Name(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
}

    