package com.arithmetic;

/** 减法运算。 */
public class SubOperation extends AbstractOperation {

    @Override
    public int calculate(int left, int right) {
        return left - right;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
