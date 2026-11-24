package com.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 习题生成器（单一职责：只负责按约束生成合法题目）。
 * 约束：三个参与运算的数字、每一步的中间结果、最终结果都必须满足 0 &lt; value &lt; 100 的正整数；
 * 除法必须整除。不满足约束时丢弃该组数据并重新生成。
 */
public class ExerciseGenerator {

    /** 数值下界（不含）。 */
    public static final int MIN_VALUE = 0;
    /** 数值上界（不含）。 */
    public static final int MAX_VALUE = 100;
    /** 单题最大尝试次数，防止极端情况下死循环。 */
    private static final int MAX_ATTEMPTS = 1000;

    private final AbstractOperation[] operations;
    private final Random random;

    public ExerciseGenerator() {
        this(new Random());
    }

    /**
     * 允许注入随机源，便于单元测试复现。
     *
     * @param random 随机数生成器
     */
    public ExerciseGenerator(Random random) {
        this.random = random;
        // 只依赖抽象父类，具体运算由子类提供，满足依赖倒置
        this.operations = new AbstractOperation[] {
                new AddOperation(), new SubOperation(), new MulOperation(), new DivOperation()
        };
    }

    /**
     * 生成指定数量的合法题目。
     *
     * @param count 题目数量，必须大于 0
     * @return 题目列表
     */
    public List<Exercise> generate(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("题目数量必须大于 0");
        }
        List<Exercise> exercises = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            exercises.add(generateOne());
        }
        return exercises;
    }

    /**
     * 生成一道满足全部约束的题目。
     *
     * @return 合法题目
     */
    public Exercise generateOne() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int first = randomOperand();
            int second = randomOperand();
            int third = randomOperand();
            AbstractOperation firstOp = randomOperation();
            AbstractOperation secondOp = randomOperation();
            try {
                boolean secondFirst = secondOp.isHighPrecedence() && !firstOp.isHighPrecedence();
                int middleResult;
                int finalResult;
                if (secondFirst) {
                    middleResult = secondOp.calculate(second, third);
                    finalResult = firstOp.calculate(first, middleResult);
                } else {
                    middleResult = firstOp.calculate(first, second);
                    finalResult = secondOp.calculate(middleResult, third);
                }
                if (isValid(middleResult) && isValid(finalResult)) {
                    return new Exercise(first, second, third, firstOp, secondOp,
                            middleResult, finalResult, secondFirst);
                }
            } catch (ArithmeticException ex) {
                // 除数为 0 或不能整除，丢弃当前数据重新生成
                continue;
            }
        }
        throw new IllegalStateException("在规定次数内未能生成满足约束的题目");
    }

    /**
     * 判断数值是否满足 0 &lt; value &lt; 100。
     *
     * @param value 待校验数值
     * @return 满足约束返回 true
     */
    public static boolean isValid(int value) {
        return value > MIN_VALUE && value < MAX_VALUE;
    }

    private int randomOperand() {
        return random.nextInt(MAX_VALUE - 1 - MIN_VALUE) + 1;
    }

    private AbstractOperation randomOperation() {
        return operations[random.nextInt(operations.length)];
    }
}
