package com.fivetran.truffle.parse;

import org.apache.calcite.plan.Context;

class TrufflePlannerContext implements Context {
    @Override
    public <T> T unwrap(Class<T> clazz) {
        if (clazz.isInstance(this))
            return clazz.cast(this);
        else
            return null;
    }
}
