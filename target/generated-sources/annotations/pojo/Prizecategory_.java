package pojo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojo.Draw;
import pojo.PrizecategoryPK;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-06T17:20:26", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Prizecategory.class)
public class Prizecategory_ { 

    public static volatile SingularAttribute<Prizecategory, Integer> winners;
    public static volatile SingularAttribute<Prizecategory, PrizecategoryPK> prizecategoryPK;
    public static volatile SingularAttribute<Prizecategory, String> name;
    public static volatile SingularAttribute<Prizecategory, Double> divident;
    public static volatile SingularAttribute<Prizecategory, Draw> draw;

}