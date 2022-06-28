package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {

    // H2Dialect 안에 보면 함수가 정의되어 있는게 그걸 참고해야함.
    public MyH2Dialect() {
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
