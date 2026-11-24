package com.arithmetic;

/** 加法运算。 */
public class AddOperation extends AbstractOperation {

    @Override
    public int calculate(int left, int right) {
        return left + right;
    }

    @Override
    public String getSymbol() {
        return "+";
    }
}
