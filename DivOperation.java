package com.arithmetic;

/**
 * 除法运算。要求整除，否则抛出异常，由题目生成器捕获后重新生成，
 * 以保证中间结果与最终结果都是正整数。
 */
public class DivOperation extends AbstractOperation {

    @Override
    public int calculate(int left, int right) {
        if (right == 0) {
            throw new ArithmeticException("除数不能为 0");
        }
        if (left % right != 0) {
            throw new ArithmeticException("除法不能整除：" + left + " / " + right);
        }
        return left / right;
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public boolean isHighPrecedence() {
        return true;
    }
}
