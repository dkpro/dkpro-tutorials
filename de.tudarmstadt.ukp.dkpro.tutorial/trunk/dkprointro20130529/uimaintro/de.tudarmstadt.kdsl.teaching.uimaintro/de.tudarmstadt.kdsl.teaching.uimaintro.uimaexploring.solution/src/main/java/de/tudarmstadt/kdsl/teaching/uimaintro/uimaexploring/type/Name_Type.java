
/* First created by JCasGen Fri May 17 11:21:59 CEST 2013 */
package de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu May 23 09:47:20 CEST 2013
 * @generated */
public class Name_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Name_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Name_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Name(addr, Name_Type.this);
  			   Name_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Name(addr, Name_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Name.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name");



  /** @generated */
  final Feature casFeat_nameType;
  /** @generated */
  final int     casFeatCode_nameType;
  /** @generated */ 
  public int getNameType(int addr) {
        if (featOkTst && casFeat_nameType == null)
      jcas.throwFeatMissing("nameType", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name");
    return ll_cas.ll_getRefValue(addr, casFeatCode_nameType);
  }
  /** @generated */    
  public void setNameType(int addr, int v) {
        if (featOkTst && casFeat_nameType == null)
      jcas.throwFeatMissing("nameType", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.Name");
    ll_cas.ll_setRefValue(addr, casFeatCode_nameType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Name_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_nameType = jcas.getRequiredFeatureDE(casType, "nameType", "de.tudarmstadt.kdsl.teaching.uimaintro.uimaexploring.type.NameType", featOkTst);
    casFeatCode_nameType  = (null == casFeat_nameType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_nameType).getCode();

  }
}



    