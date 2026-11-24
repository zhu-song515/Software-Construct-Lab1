package com.arithmetic;

/**
 * 一道三数运算题的数据模型：first op1 second op2 third。
 * 记录中间结果与最终结果，便于约束校验、控制台显示与 CSV 导出。
 */
public class Exercise {

    private final int first;
    private final int second;
    private final int third;
    private final AbstractOperation firstOp;
    private final AbstractOperation secondOp;
    private final int middleResult;
    private final int finalResult;
    private final boolean secondFirst;

    /**
     * @param secondFirst true 表示先算 second op2 third（乘除优先时出现）
     */
    public Exercise(int first, int second, int third,
                    AbstractOperation firstOp, AbstractOperation secondOp,
                    int middleResult, int finalResult, boolean secondFirst) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.firstOp = firstOp;
        this.secondOp = secondOp;
        this.middleResult = middleResult;
        this.finalResult = finalResult;
        this.secondFirst = secondFirst;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    public int getMiddleResult() {
        return middleResult;
    }

    public int getAnswer() {
        return finalResult;
    }

    /** 是否先计算后两个数（用于校验与展示）。 */
    public boolean isSecondFirst() {
        return secondFirst;
    }

    /** 题目文本，例如 "3 + 5 * 2 =" 。 */
    public String getExpression() {
        return first + " " + firstOp.getSymbol() + " " + second + " "
                + secondOp.getSymbol() + " " + third + " =";
    }

    /** CSV 行内容，例如 "3 + 5 * 2 =,13" 。 */
    public String toCsvLine() {
        return getExpression() + "," + finalResult;
    }

    @Override
    public String toString() {
        return getExpression() + " " + finalResult;
    }
}
