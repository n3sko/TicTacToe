import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    // параметры игрового поля
    static final int SIZE_Y = 5; //кол-во клеток по вертикали
    static final int SIZE_X = 5; //кол-во клеток по горизонтале
    static final int SIZE_WIN = 4; //кол-во клеток для победы
    static final char[][] field =  new char [SIZE_Y][SIZE_X];
    static final char PlAYER_DOT= 'X';
    static final char AI_DOT= 'O';
    static final char EMPTY_DOT= '.';

    static Scanner scan = new Scanner(System.in);
    static Random rnd = new Random();

    // заполнение поля пустыми клетками
    private static void emtpyField () {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // печать поля в консоль
    private static void printField () {
        //System.out.println("Введите координаты в диапозоне от 1 до " + SIZE_Y);
        System.out.print("   ");
        for (int k = 0; k < SIZE_Y; k++) {
            System.out.printf("%d ", k + 1);
        }
        System.out.println();
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.printf("%d |", i + 1);
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
    }

    // запись хода игрока на поле
    private static void dotField (int y, int x, char dot) {
        field[y][x] = dot;
    }

    // ход игрока
    private static void playerMove () {
        int x, y;
        do {
            System.out.println("Введите координаты в диапозоне от 1 до " + SIZE_Y);
            System.out.print ("Координата Х: ");
            x = scan.nextInt() - 1;
            System.out.print ("Координата Y: ");
            y = scan.nextInt() - 1;
        } while (!checkMove(y, x));
        dotField(y, x, PlAYER_DOT);
    }

    // ход компьютера
    private static void aiMove () {
        int x, y;
        // блокировка ходов игрока
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkLineHorisont(v, h, PlAYER_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }
                    if (v - SIZE_WIN > -2) {
                        if (checkDiagUp(v, h, PlAYER_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiagDown(v, h, PlAYER_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkLineVertical(v, h, PlAYER_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkLineHorisont(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }
                    if (v - SIZE_WIN > -2 ) {
                        if (checkDiagUp(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiagDown(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkLineVertical(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }
        // случайный ход
        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y, x));
        dotField(y, x, AI_DOT);
    }

    // ход компьютера по горизонтали
    private static boolean MoveAiLineHorisont(int v, int h, char dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((field[v][j] == EMPTY_DOT)) {
                field[v][j] = dot;
                return true;
            }
        }
        return false;
    }

    // ход компьютера по вертикали
    private static boolean MoveAiLineVertical(int v, int h, char dot) {
        for (int i = v; i < SIZE_WIN; i++) {
            if ((field[i][h] == EMPTY_DOT)) {
                field[i][h] = dot;
                return true;
            }
        }
        return false;
    }

    // проверка заполнения всей линии по диагонале вверх
    private static boolean MoveAiDiaUp(int v, int h, char dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((field[v + i][h + j] == EMPTY_DOT)) {
                field[v + i][h + j] = dot;
                return true;
            }
        }
        return false;
    }

    // проверка заполнения всей линии по диагонале вниз
    private static boolean MoveAiDiaDown(int v, int h, char dot) {
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((field[i + v][i + h] == EMPTY_DOT)) {
                field[i + v][i + h] = dot;
                return true;
            }
        }
        return false;
    }

    // проверка заполнения выбранного для хода игроком
    private static boolean checkMove(int y, int x) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return false;
        else if (!(field[y][x] == EMPTY_DOT)) return false;
        return true;
    }

    // проверка на ничью (все  ячейки поля заполнены ходами)
    private  static boolean fullField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        System.out.println("DRAW!");
        return true;
    }

    // проверка победы
    private static boolean checkWin(char dot) {
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h<SIZE_X; h++) {
                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) return true;
                    if (v - SIZE_WIN > -2) {
                        if (checkDiagUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiagDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }

    // проверка заполнения всей линии по диагонале вверх
    private static int checkDiagUp(int v, int h, char dot) {
        int count=0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((field[v+i][h+j] == dot)) count++;
        }
        return count;
    }

    // проверка заполнения всей линии по диагонале вниз
    private static int checkDiagDown(int v, int h, char dot) {
        int count = 0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((field[i + v][i + h] == dot)) count++;
        }
        return count;
    }

    // проверка заполнения всей линии по горизонтали
    private static int checkLineHorisont(int v, int h, char dot) {
        int count = 0;
        for (int j = h; j < SIZE_WIN + h; j++) {
            if ((field[v][j] == dot)) count++;
        }
        return count;
    }

    // проверка заполнения всей линии по вертикали
    private static int checkLineVertical(int v, int h, char dot) {
        int count = 0;
        for (int i = v; i < SIZE_WIN + v; i++) {
            if ((field[i][h] == dot)) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        emtpyField ();
        printField();
        do {
            playerMove();
            System.out.println("*Ваш ход*");
            printField();
            if (checkWin(PlAYER_DOT)) {
                System.out.println("YOU WIN!");
                break;
            } else if (fullField()) break;
            aiMove();
            System.out.println("*Ход компьютера*");
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("AI WIN!");
                break;
            } else if (fullField()) break;
        } while (true);
    }
}

