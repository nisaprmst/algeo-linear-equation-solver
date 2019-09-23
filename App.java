import java.util.Scanner;
import java.io.*;

public class App {
    public static void clrScr() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menu() {
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks balikan");
        System.out.println("4. Matriks kofaktor");
        System.out.println("5. Adjoin");
        System.out.println("6. Interpolasi Polinom");
        System.out.println("7. Keluar");
        System.out.println("Pilih menu: ");
    }

    public static void subSPL() {
        System.out.println("Pilih metode");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Crammer");
    }

    public static void subDet() {
        System.out.println("Pilih Metode");
        System.out.println("1. Metode kofaktor");
        System.out.println("2. Metode sarrus (hanya bisa untuk matriks 3x3)");
        System.out.println("3. Metode operasi baris elementer");
    }

    public static void subInv() {
        System.out.println("Pilih Metode");
        System.out.println("1. Metode Gauss-Jordan");
        System.out.println("2. Metode adjoin");
    }

    public static void showSPL(Matrix M, int x) {
        /* Matrix M terdefinisi */
        /* untuk menghitung SPL sesuai menu yang dipilih */
        /* x = 1, 2, 3, 4 */
        if (x == 1) {
            //eliminasi gauss
            SPL.showResultGauss(M);
        } else if (x == 2) {
            //eliminasi gauss jordan
            SPL.showResultGaussJordan(M);
        } else if (x == 3) {
            //metode matriks balikan

        } else if (x == 4) {
            //metode creammer

        }


    }

    public static double getDet (Matrix M, int x) {
        /* Matrix M terdefinisi */
        /* untuk menghitung determinan sesuai menu yang dipilih */
        /* x = 1, 2, 3 */
        if (x == 1) {
            return Dependencies.kofaktorDet(M);
        } else if (x == 2) {
            return Dependencies.sarrusDet(M);
        } else if ( x == 3) {
            return Dependencies.detOBE(M);
        } else return 0;
    }

    public static Matrix getInvers (Matrix M, int x) {
        /* Matrix M terdefinisi */
        /* mengembalikan matriks invers sesuai menu yang dipilih */
        /* x = 1, 2 */
        if (x == 1) {
            return Dependencies.inversGauss(M);
        } else if (x == 2) {
            return Dependencies.inversAdj(M);
        } else return M;
    }

    public static Matrix inputMatrix(String filename){
        int[] bentukMatriks = BacaFile.jumlahData(filename);

        double[][] arr = new double[bentukMatriks[0]][bentukMatriks[1]];

        try {
            File file = new File(filename + ".txt");

            Scanner scanner = new Scanner(file);

            String s;
            String[] sSplit;
            double[] arrTemp = new double[bentukMatriks[1]];
            int count = 0;

            while(scanner.hasNextLine()){
                s = scanner.nextLine();
                sSplit = s.split(" ");
                for (int i = 0; i < bentukMatriks[1]; i++){
                    arr[count][i] = Double.parseDouble(sSplit[i]);
                }
                count++;
            }
            Matrix matrix = new Matrix(arr);
            scanner.close();
            return matrix;
        } catch(FileNotFoundException e){
            System.out.println("File tidak ditemukan");

            Matrix matrix = new Matrix(arr);
            return matrix;
        }
    }

    /*Dilakukan pembacaan terhadap sistem persamaan linear*/
    public static Matrix inputPersamaanMatrix(String filename) {
        Matrix matrix = inputMatrix(filename);

        /*Dilakukan penambahan apabila bentuk tidak */
        while (matrix.getRow() < matrix.getColumn() - 1) {
            matrix.setRow(matrix.getRow() + 1);
            double[][] arr = new double[matrix.getRow()][matrix.getColumn()];

            for (int i = 0; i < matrix.getRow(); i++) {
                if (i != matrix.getRow() - 1) {
                    for (int j = 0; j < matrix.getColumn(); j++){
                        arr[i][j] = matrix.getMatrix()[i][j];
                    }
                } else {
                    for (int j = 0; j < matrix.getColumn(); j++){
                        arr[i][j] = 0;
                    }
                } 
            }
            matrix.setMatrix(arr);
        }
        return matrix;
    }


    public static void main(String[] args) {
        Matrix M = new Matrix();
        Matrix kof = new Matrix();
        Matrix inv = new Matrix();
        Matrix adj = new Matrix();
        Matrix spl = new Matrix();
        double det;
        int mn, sub;
        boolean keluar = false;
        Scanner scan = new Scanner(System.in);
        String end;
        Interpolasi interpolasi = new Interpolasi(); // instance buat interpolasi
        while (!keluar) {
            menu();
            mn = scan.nextInt();
            clrScr();
            if (mn == 1) { // SPL
                subSPL();
                sub = scan.nextInt();
                clrScr();
                Matrix.inputSPL(spl);
                showSPL(spl, sub);
            } else if (mn == 2) { // determinan
                subDet();
                sub = scan.nextInt();
                clrScr();
                System.out.println("Masukkan matrix nxn:");
                Matrix.inputNxN(M);
                det = getDet(M, sub);
                System.out.println("Determinannya adalah :" + det);
            } else if (mn == 3) { // invers
                // hanya ada jika determinannya tidak = 0
                subInv();
                sub = scan.nextInt();
                clrScr();
                System.out.println("Masukkan matriks nxn:");
                Matrix.inputNxN(M);
                if (Dependencies.kofaktorDet(M) == 0) {
                    System.out.println("Tidak ada invers");
                } else {
                    inv = getInvers(M, sub);
                    System.out.println("Matriks balikannya adalah:");
                    inv.show();
                }

            } else if (mn == 4) { // kofaktor
                System.out.println("Masukkan matriks nxn:");
                Matrix.inputNxN(M);
                kof = Dependencies.hitungKofaktor(M);
                System.out.println("Matriks kofaktornya adalah:");
                kof.show();
            } else if (mn == 5) { // adjoin
                System.out.println("Masukkan matriks nxn:");
                Matrix.inputNxN(M);
                adj = Dependencies.hitungAdjoin(M);
                System.out.println("Matriks adjoinnya adalah:");
                adj.show();
            } else if (mn == 6) {
                interpolasi.prosedurInterpolasi();
            } else if (mn == 7) {
                keluar = true;
            }
        }

    }
}