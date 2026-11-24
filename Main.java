package com.arithmetic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * 程序入口：读取用户输入（题目数量、每行列数），生成题目、控制台输出并导出 CSV。
 * 对非法输入做异常捕获并给出友好提示，保证程序健壮性。
 */
public class Main {

    /** 默认导出文件名。 */
    private static final String CSV_FILE = "exercise.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int count = readInt(scanner, "请输入题目总数（1-1000）：", 1, 1000);
            int columns = readInt(scanner, "请输入每行显示列数（1-10）：", 1, 10);

            ExerciseGenerator generator = new ExerciseGenerator();
            List<Exercise> exercises = generator.generate(count);

            ConsolePrinter printer = new ConsolePrinter();
            printer.print(exercises, columns);
            printer.printAnswers(exercises, columns);

            new CsvFileWriter().write(exercises, CSV_FILE);
            System.out.println("已生成 " + count + " 道题目，并写入文件：" + new File(CSV_FILE).getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("写文件失败：" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("参数错误：" + ex.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * 读取一个区间内的整数，非法输入时提示并重新读取。
     *
     * @param scanner 输入源
     * @param prompt  提示语
     * @param min     最小值（含）
     * @param max     最大值（含）
     * @return 合法整数
     */
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null) {
                return min;
            }
            try {
                int value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println("输入超出范围，请输入 " + min + "-" + max + " 之间的整数");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("输入不是整数，请重新输入");
            }
        }
    }
}
