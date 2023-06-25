package com.example.gsmiddleware;

/**
 * @author yangzq80@gmail.com
 * @date 6/24/23
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaMethodExtractor {
    public static void main(String[] args) throws Exception {

        String directoryPath = "/Users/zqy/codes/opensource/mall-1.0.2"; // 指定目录的路径
        String outputDirectory = "snippets-java"; // 输出文件目录的路径
        int limit = 10;
        if (args.length == 3) {
            directoryPath = args[0];
            outputDirectory = args[1];
            limit = Integer.parseInt(args[2]);
        }

        System.out.printf("Usage args:%s %s %s", directoryPath, outputDirectory, limit);


        File directory = new File(directoryPath);

        // 确保输出目录存在
        File outputDir = new File(outputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        AtomicInteger count = new AtomicInteger(1); // 序号计数器

        listDir(limit, count, directory, outputDir.getPath());
    }

    private static void listDir(int limit, AtomicInteger count, File directory, String outputDir) throws Exception {
        // 遍历指定目录下的所有 Java 文件
        for (File file : directory.listFiles()) {
            // 判断是否达到序号 1000
            if (count.get() >= limit) {
                break;
            }

            if (file.isDirectory()) {
                listDir(limit, count, file, outputDir);
            } else if (file.isFile() && file.getName().endsWith(".java")) {

                // 使用 JavaParser 解析 Java 文件
                ParseResult<CompilationUnit> compilationUnit = new JavaParser().parse(file);

                // 使用 Visitor 模式提取方法及注释
                compilationUnit.getResult().ifPresent(compilationUnit1 -> {
                    compilationUnit1.accept(new MethodVisitor(count, outputDir), null);
                });
            }
        }
    }

    // Visitor 类用于访问方法及注释
    public static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private AtomicInteger count;
        private String outputDirectory;

        public MethodVisitor(AtomicInteger count, String outputDirectory) {
            this.count = count;
            this.outputDirectory = outputDirectory;
        }

        @Override
        public void visit(MethodDeclaration method, Void arg) {

            if (notTarget(method)) {
                return;
            }

            String fileName = count.getAndIncrement() + ".java";
            File outputFile = new File(outputDirectory, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                // 将方法名和注释写入输出文件
//                System.out.println(method.toString());
                writer.write(method.toString());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            super.visit(method, arg);
        }
    }

    private static boolean notTarget(MethodDeclaration method) {
        if (!method.getComment().isPresent()) {
            return true;
        }

        if (!method.toString().contains("}")) {
            return true;
        }

        // 无中文注释
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+"); // 匹配中文字符的正则表达式

        Matcher matcher = pattern.matcher(method.getComment().toString());
        if (!matcher.find()) {
            return true;
        }

        // 行数少
        AtomicInteger lines = new AtomicInteger();
        method.getBody().ifPresent(blockStmt -> {
            lines.set(blockStmt.getEnd().get().line - blockStmt.getBegin().get().line);
        });
        if (lines.get() < 5) {
            return true;
        }


        return false;
    }
}