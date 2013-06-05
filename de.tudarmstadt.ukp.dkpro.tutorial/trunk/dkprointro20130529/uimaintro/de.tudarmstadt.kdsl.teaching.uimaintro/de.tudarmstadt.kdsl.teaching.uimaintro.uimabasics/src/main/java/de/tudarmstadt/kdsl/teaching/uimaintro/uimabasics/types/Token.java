

/* First created by JCasGen Fri May 17 11:25:26 CEST 2013 */
package de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri May 17 11:25:26 CEST 2013
 * XML source: /s21/studium/ukp/20130426_tutorials/de.tudarmstadt.ukp.teaching.kdslintro.uimabasics/src/main/resources/desc/types/TypeSystem.xml
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

  /** getter for length - gets 
   * @generated */
  public int getLength() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.types.Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Token_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets  
   * @generated */
  public void setLength(int v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "de.tudarmstadt.kdsl.teaching.uimaintro.uimabasics.types.Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((Token_Type)jcasType).casFeatCode_length, v);}    
  }

    