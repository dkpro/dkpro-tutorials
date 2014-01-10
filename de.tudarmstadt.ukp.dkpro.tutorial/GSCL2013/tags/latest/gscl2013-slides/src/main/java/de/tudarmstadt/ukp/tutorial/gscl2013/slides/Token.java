

/* First created by JCasGen Sun Sep 22 19:56:08 CEST 2013 */
package de.tudarmstadt.ukp.tutorial.gscl2013.slides;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Sep 22 20:01:52 CEST 2013
 * XML source: /Users/bluefire/UKP/Workspaces/dkpro-juno/gscl2013-slides/src/main/resources/de/tudarmstadt/ukp/tutorial/gscl2013/slides/typeSystemDescriptor.xml
 * @generated */
public class Token extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);
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
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Token(JCas jcas, int begin, int end) {
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
     
  //*--------------*
  //* Feature: length

  /** getter for length - gets Just the length of the token.
   * @generated */
  public int getLength() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Token_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets Just the length of the token. 
   * @generated */
  public void setLength(int v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "de.tudarmstadt.ukp.tutorial.gscl2013.slides.Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((Token_Type)jcasType).casFeatCode_length, v);}    
  }

    