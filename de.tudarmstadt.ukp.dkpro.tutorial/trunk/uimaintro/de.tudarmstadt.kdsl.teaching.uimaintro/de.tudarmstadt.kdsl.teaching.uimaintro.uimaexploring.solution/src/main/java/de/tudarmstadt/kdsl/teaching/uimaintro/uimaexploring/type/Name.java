

/* First created by JCasGen Fri May 17 11:21:59 CEST 2013 */
package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu May 23 09:47:20 CEST 2013
 * XML source: /s21/studium/ukp/20130426_tutorials/de.tudarmstadt.ukp.teaching.kdslintro/de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.solution/src/main/resources/desc/types/TypeSystem.xml
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
     
  //*--------------*
  //* Feature: nameType

  /** getter for nameType - gets 
   * @generated */
  public NameType getNameType() {
    if (Name_Type.featOkTst && ((Name_Type)jcasType).casFeat_nameType == null)
      jcasType.jcas.throwFeatMissing("nameType", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name");
    return (NameType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Name_Type)jcasType).casFeatCode_nameType)));}
    
  /** setter for nameType - sets  
   * @generated */
  public void setNameType(NameType v) {
    if (Name_Type.featOkTst && ((Name_Type)jcasType).casFeat_nameType == null)
      jcasType.jcas.throwFeatMissing("nameType", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name");
    jcasType.ll_cas.ll_setRefValue(addr, ((Name_Type)jcasType).casFeatCode_nameType, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    