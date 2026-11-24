package com.arithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * 核心逻辑单元测试（JUnit 5）。
 */
class ExerciseGeneratorTest {

    @Test
    @DisplayName("加法运算结果正确")
    void testAdd() {
        assertEquals(7, new AddOperation().calculate(3, 4));
    }

    @Test
    @DisplayName("减法运算结果正确")
    void testSub() {
        assertEquals(2, new SubOperation().calculate(6, 4));
    }

    @Test
    @DisplayName("乘法运算结果正确")
    void testMul() {
        assertEquals(12, new MulOperation().calculate(3, 4));
    }

    @Test
    @DisplayName("整除的除法结果正确")
    void testDiv() {
        assertEquals(4, new DivOperation().calculate(12, 3));
        assertEquals(3, new DivOperation().calculate(9, 3));
    }

    @Test
    @DisplayName("不能整除时抛出异常")
    void testDivNotExact() {
        assertThrows(ArithmeticException.class, () -> new DivOperation().calculate(7, 2));
    }

    @Test
    @DisplayName("除数为 0 时抛出异常")
    void testDivByZero() {
        assertThrows(ArithmeticException.class, () -> new DivOperation().calculate(7, 0));
    }

    @Test
    @DisplayName("生成数量与请求一致")
    void testGenerateCount() {
        List<Exercise> exercises = new ExerciseGenerator(new Random(20261124L)).generate(30);
        assertEquals(30, exercises.size());
    }

    @Test
    @DisplayName("1000 道题的操作数、中间结果、最终结果都在 0~100 的正整数范围内")
    void testAllValuesInRange() {
        ExerciseGenerator generator = new ExerciseGenerator(new Random(20261124L));
        List<Exercise> exercises = generator.generate(1000);
        for (Exercise exercise : exercises) {
            assertTrue(ExerciseGenerator.isValid(exercise.getFirst()));
            assertTrue(ExerciseGenerator.isValid(exercise.getSecond()));
            assertTrue(ExerciseGenerator.isValid(exercise.getThird()));
            assertTrue(ExerciseGenerator.isValid(exercise.getMiddleResult()));
            assertTrue(ExerciseGenerator.isValid(exercise.getAnswer()));
        }
    }

    @Test
    @DisplayName("题目表达式含有两个运算符且答案可复算")
    void testExpressionAndAnswer() {
        Exercise exercise = new Exercise(3, 5, 2, new AddOperation(), new MulOperation(), 10, 13, true);
        assertEquals("3 + 5 * 2 =", exercise.getExpression());
        assertEquals(13, exercise.getAnswer());
    }

    @Test
    @DisplayName("题目数量非法时抛出 IllegalArgumentException")
    void testIllegalCount() {
        assertThrows(IllegalArgumentException.class, () -> new ExerciseGenerator().generate(0));
    }

    @Test
    @DisplayName("控制台按指定列数换行打印")
    void testConsolePrinter() {
        List<Exercise> exercises = new ExerciseGenerator(new Random(20261124L)).generate(6);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        new ConsolePrinter(out).print(exercises, 3);

        String text = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("题目"));
        assertEquals(3, text.split("\n").length, "3 列时 6 道题应输出标题行 + 2 行题目");
    }

    @Test
    @DisplayName("CSV 文件写出后行数与题目数量一致")
    void testCsvWriter(@TempDir Path tempDir) throws IOException {
        List<Exercise> exercises = new ExerciseGenerator(new Random(20261124L)).generate(5);
        Path csv = tempDir.resolve("exercise.csv");
        new CsvFileWriter().write(exercises, csv.toString());

        List<String> lines = Files.readAllLines(csv, StandardCharsets.UTF_8);
        assertEquals(6, lines.size());
        assertEquals("序号,题目,答案", lines.get(0));
        assertTrue(lines.get(1).contains(","));

        try (BufferedReader reader = Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {
            assertNotNull(reader.readLine());
        }
    }
}
