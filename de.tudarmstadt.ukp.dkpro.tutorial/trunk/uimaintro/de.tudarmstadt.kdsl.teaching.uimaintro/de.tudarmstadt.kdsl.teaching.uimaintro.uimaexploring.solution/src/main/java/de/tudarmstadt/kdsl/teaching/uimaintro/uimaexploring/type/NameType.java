

/* First created by JCasGen Thu May 23 09:47:20 CEST 2013 */
package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Thu May 23 09:47:20 CEST 2013
 * XML source: /s21/studium/ukp/20130426_tutorials/de.tudarmstadt.ukp.teaching.kdslintro/de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.solution/src/main/resources/desc/types/TypeSystem.xml
 * @generated */
public class NameType extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NameType.class);
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
  protected NameType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NameType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NameType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets 
   * @generated */
  public String getValue() {
    if (NameType_Type.featOkTst && ((NameType_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.NameType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NameType_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated */
  public void setValue(String v) {
    if (NameType_Type.featOkTst && ((NameType_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.NameType");
    jcasType.ll_cas.ll_setStringValue(addr, ((NameType_Type)jcasType).casFeatCode_value, v);}    
  }

    