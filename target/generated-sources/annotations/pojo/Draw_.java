package pojo;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojo.Prizecategory;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-06T17:20:26", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Draw.class)
public class Draw_ { 

    public static volatile SingularAttribute<Draw, Date> date;
    public static volatile ListAttribute<Draw, Prizecategory> prizecategoryList;
    public static volatile SingularAttribute<Draw, Integer> bonus;
    public static volatile SingularAttribute<Draw, Integer> winningnumber3;
    public static volatile SingularAttribute<Draw, Integer> winningnumber4;
    public static volatile SingularAttribute<Draw, Integer> winningnumber1;
    public static volatile SingularAttribute<Draw, Integer> id;
    public static volatile SingularAttribute<Draw, Integer> winningnumber2;
    public static volatile SingularAttribute<Draw, Integer> winningnumber5;

}