package com.arithmetic;

/**
 * 二元运算抽象父类。
 * 加法、减法、乘法、除法均继承本类，上层生成器只依赖本抽象类，
 * 满足依赖倒置原则；各子类实现同一接口，可相互替换，满足里氏替换原则。
 */
public abstract class AbstractOperation {

    /**
     * 执行一次二元运算。
     *
     * @param left  左操作数
     * @param right 右操作数
     * @return 运算结果
     */
    public abstract int calculate(int left, int right);

    /**
     * 返回运算符符号，用于题目文本拼装。
     *
     * @return 运算符，例如 "+"、"-"、"*"、"/"
     */
    public abstract String getSymbol();

    /**
     * 是否属于高优先级运算（乘、除优先于加、减）。
     *
     * @return 高优先级返回 true，默认为 false
     */
    public boolean isHighPrecedence() {
        return false;
    }

    @Override
    public String toString() {
        return getSymbol();
    }
}
