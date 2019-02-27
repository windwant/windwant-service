package org.windwant.algorithm.subjects;

/**
 * 给定一个 n × n 的二维矩阵表示一个图像。
 * 将图像顺时针旋转 90 度。
 * 说明：
 * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
 * Created by Administrator on 19-2-27.
 */
public class MatrixRotate {
    public static void rotate(int[][] matrix) {
        int step = matrix.length;
        int[][] tmp = new int[step][step];
        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                tmp[i][j] = matrix[step - j - 1][i];
            }
        }
        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                matrix[i][j] = tmp[i][j];
            }
        }
    }

    public static void printArr(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        printArr(matrix);
        rotate(matrix);
        printArr(matrix);
    }
}
