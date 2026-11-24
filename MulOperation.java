package com.arithmetic;

/** 乘法运算。 */
public class MulOperation extends AbstractOperation {

    @Override
    public int calculate(int left, int right) {
        return left * right;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public boolean isHighPrecedence() {
        return true;
    }
}
