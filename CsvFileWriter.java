package com.arithmetic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * CSV 文件写入器（单一职责：只负责把题目写成逗号分隔文件，可用 Excel 打开）。
 */
public class CsvFileWriter {

    /**
     * 将题目写入 CSV 文件，首行为表头。
     *
     * @param exercises 题目列表
     * @param filePath  目标文件路径
     * @throws IOException 写文件失败时抛出
     */
    public void write(List<Exercise> exercises, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("序号,题目,答案");
            writer.newLine();
            int index = 1;
            for (Exercise exercise : exercises) {
                writer.write(index + "," + exercise.toCsvLine());
                writer.newLine();
                index++;
            }
        }
    }
}
