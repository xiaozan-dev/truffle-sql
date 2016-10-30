package com.fivetran.truffle;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "AND")
public abstract class ExprAnd extends ExprBinary {

    @Specialization
    protected boolean and(boolean left, boolean right) {
        return left && right;
    }

    @Specialization
    protected SqlNull or(SqlNull left, boolean right) {
        return SqlNull.INSTANCE;
    }

    @Specialization
    protected SqlNull or(boolean left, SqlNull right) {
        return SqlNull.INSTANCE;
    }
}
