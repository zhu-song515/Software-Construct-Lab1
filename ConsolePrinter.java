package com.arithmetic;

import java.io.PrintStream;
import java.util.List;

/**
 * 控制台输出器（单一职责：只负责按指定列数格式化打印）。
 */
public class ConsolePrinter {

    /** 每列占用的显示宽度，便于对齐。 */
    private static final int COLUMN_WIDTH = 16;

    private final PrintStream out;

    public ConsolePrinter() {
        this(System.out);
    }

    public ConsolePrinter(PrintStream out) {
        this.out = out;
    }

    /**
     * 按每行 columns 列打印题目。
     *
     * @param exercises 题目列表
     * @param columns   每行列数
     */
    public void print(List<Exercise> exercises, int columns) {
        checkColumns(columns);
        out.println("---------- 题目 ----------");
        int index = 0;
        for (Exercise exercise : exercises) {
            out.print(pad(exercise.getExpression()));
            index++;
            if (index % columns == 0) {
                out.println();
            }
        }
        if (index % columns != 0) {
            out.println();
        }
    }

    /**
     * 按每行 columns 列打印答案，便于批改。
     *
     * @param exercises 题目列表
     * @param columns   每行列数
     */
    public void printAnswers(List<Exercise> exercises, int columns) {
        checkColumns(columns);
        out.println("---------- 答案 ----------");
        int index = 0;
        for (Exercise exercise : exercises) {
            out.print(pad(String.valueOf(exercise.getAnswer())));
            index++;
            if (index % columns == 0) {
                out.println();
            }
        }
        if (index % columns != 0) {
            out.println();
        }
    }

    private String pad(String text) {
        StringBuilder builder = new StringBuilder(text);
        while (width(builder.toString()) < COLUMN_WIDTH) {
            builder.append(' ');
        }
        return builder.toString();
    }

    /** 粗略按字符宽度计算，中文按 2 个宽度处理。 */
    private int width(String text) {
        int width = 0;
        for (char ch : text.toCharArray()) {
            width += ch > 0x7F ? 2 : 1;
        }
        return width;
    }

    private void checkColumns(int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException("列数必须大于 0");
        }
    }
}
