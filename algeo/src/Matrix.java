package bin;

import java.util.Scanner;

public class Matrix {

    /*** Instance Variables ***/
    private double matrix[][]; // matrix container with M-N size
	private int M; // row size
	private int N; // columns size


	/*** Konstruktor ***/
	public Matrix () {
    /* Untuk membentuk Matrix kosong dengan jumlah baris dan kolom 1 */
		double[][] p = new double[1][1];
		this.M = 1;
		this.N = 1;
		this.matrix = p;
	}

	public Matrix (int m, int n) {
    /* Untuk membentuk Matrix kosong dengan jumlah baris m dan jumlah kolom n */
		double[][] p = new double[m][n];
		this.M = m;
		this.N = n;
		this.matrix = p;
	}

	public Matrix (double[][] data) {
    /* Untuk membentuk Matrix yang komponen matrixnya adalah data */
		int m = data.length;
		int n = data[0].length;
		this.matrix = new double[m][n];

		// melakukan pengkopian data dari data masukan ke matrix
		for (int i = 0; i < m; i++){
			for (int j = 0; j < n; j++){
				this.matrix[i][j] = data[i][j];
			}
		}
		this.M = m;
		this.N = n;
	}

	/*** Getter ***/
	public int getRow(){
    /* Mengembalikan jumlah baris Matrix */
		return this.M;
	}

	public int getColumn(){
    /* Mengembalikan jumlah kolom Matrix */
		return this.N;
	}

	public double[][] getMatrix(){
    /* Mengembalikan komponen matrix */
		return this.matrix;
	}

	/*** Setter ***/
	public void setRow(int m){
    /* Untuk mengubah elemen M dari Matrix menjadi m */
		this.M = m;
	}

	public void setColumn(int n){
    /* Untuk mengubah elemen N dari Matrix menjadi n */
		this.N = n;
	}
	
	public void setMatrix(double[][] arr){
    /* Untuk mengubah elemen matrix dari Matrix menjadi arr */
		this.matrix = arr;
	}

	public void setElmtMatrix(double x, int i, int j){
    /* Untuk mengubah elemen matrix baris i kolom j dengan nilai x */
		Matrix newMatrix = new Matrix(matrix);
		newMatrix.getMatrix()[i][j] = x;
		this.matrix = newMatrix.getMatrix(); 
	}
	
    /*** Operasi Pada Matrix ***/
	public void swap_row (int i, int j){
	/* I. S. Matrix terdefinisi */
    /* F. S. Matriks baris i telah bertukar tempat dengan matriks baris j */
		/* KAMUS LOKAL */
		int row;
		double arr[][];
		/* INISIALISASI */
		row = M;
		arr = matrix;
		/* ALGORITMA */
		for (int k = 0;k<=row; k++){
			double temp = arr[i][k];
			arr[i][k] = arr[j][k];
			arr[j][k] = temp;
		}
		matrix = arr;
	}

	public static Matrix delBrsMatrix (Matrix M, int idx_brs) {
	/* Matrix M terdefinisi, idx_brs <= baris M*/
    /* Mengembalikan Matrix yang menghilangkan baris ke idx_brs */
		/* KAMUS LOKAL */
		int i, j;
		int new_b;
		double[][] matriks = new double[M.getRow()-1][M.getColumn()];
		Matrix result = new Matrix(matriks);
		/* ALGORITMA */
		new_b = 0;
		for (i=0 ; i < M.getRow() ; i++){
			if (i != idx_brs){
				for (j = 0 ; j < M.getColumn() ; j++){
					matriks[new_b][j] = M.getMatrix()[i][j];
				}
				new_b++;
			}
		}
		result.setColumn(M.getColumn());
		result.setMatrix(matriks);
		result.setRow(M.getRow() - 1);
		return result;
	}
	
	public static Matrix delKolMatrix (Matrix M, int idx_kol) {
	/* Matrix M terdefinisi, idx_kol <= kolom M*/
    /* Mengembalikan Matrix yang menghilangkan kolom ke idx_kol */
		/* KAMUS LOKAL */
		int i, j;
		int new_k;
		double[][] matriks = new double[M.getRow()][M.getColumn()-1];
		Matrix result = new Matrix(matriks);
		/* ALGORITMA */
		for (i=0 ; i < M.getRow() ; i++){
			new_k = 0;
			for (j = 0 ; j < M.getColumn() ; j++){
				if (j != idx_kol){
					matriks[i][new_k] = M.getMatrix()[i][j];
					new_k++;
				}
			}
		}
		result.setColumn(M.getColumn()-1);
		result.setMatrix(matriks);
		result.setRow(M.getRow());
		return result;
	}

	public static Matrix multiplication (Matrix M, Matrix N) {
	/* Matrix M dan N terdefinisi dan jumlah kolom M = jumlah baris N */
    /* Mengembalikan matriks hasil perkalian MxN */
		/* KAMUS LOKAL */
		Matrix res = new Matrix(M.getRow(), N.getColumn());
		double[][] matriks = new double[M.getRow()][N.getColumn()];
		int i, j, k;
		/* ALGORITMA */
		for (i = 0; i < M.getRow(); i++) {
			for (j = 0; j < N.getColumn(); j++) {
				matriks[i][j] = 0;
				for (k = 0; k < M.getColumn(); k++) {
					matriks[i][j] += M.getMatrix()[i][k] * N.getMatrix()[k][j];
				}
			}
		}
		res.setMatrix(matriks);
		return res;
	}

	public static Matrix minor (Matrix M, int idx_brs, int idx_kol) {
	/* Matrix M terdefinisi dan harus nxn, idx_brs <= baris M, idx_kol <= kolom M */
    /* Mengembalikan sub matriks/minor Matrix yang menghilangkan baris ke idx_brs dan kolom ke idx_kol */
		/* KAMUS LOKAL */
        int i, j;
        int new_b, new_k;
		double[][] matriks = new double[M.getRow()-1][M.getColumn()-1];
		int size = M.getRow();
		Matrix result = new Matrix(matriks);
		/* ALGORITMA */
        new_b = 0;
        for (i=0 ; i< size ; i++){
            new_k = 0;
            if (i != idx_brs){
                for (j = 0 ; j < size ; j++){
                    if (j != idx_kol){
                        matriks[new_b][new_k] = M.getMatrix()[i][j];
                        new_k++;
                    }
                }
                new_b++;
            }
        }
		result.setColumn(M.getColumn() - 1);
		result.setMatrix(matriks);
		result.setRow(M.getRow() - 1);
        return result;
	}
	
	public static Matrix transpose (Matrix M) {
	/* Matrix M terdefinisi, tidak harus nxn */
    /* Mengembalikan Matrix transpose baris dan kolomnya */
    /* Apabila M adalah matrix 2x3, maka keluarannya adalah matrix 3x2 */
		/* KAMUS LOKAL */
		Matrix matrix = new Matrix(M.getColumn(), M.getRow());
		int i, j;
		/* ALGORITMA */
		for (i = 0 ; i < M.getRow() ; i++){
            for (j = 0 ; j < M.getColumn() ; j++){
				matrix.getMatrix()[j][i] = M.getMatrix()[i][j];
				// baris dan kolom dipertukarkan
            }
		}
		matrix.setColumn(M.getRow());
		matrix.setRow(M.getColumn());
		return matrix;
	}

    /*** Fungsi dan Prosedur Lain ***/
	public Boolean isSquare() {
    /* Mengembalikan true apabila ukuran Matrix nxn */
		return (this.M == this.N);
	}

    public void show() {
	/* I. S. Matrix terdefinisi */
    /* F. S. Matrix ditampilkan pada layar */
		/* ALGORITMA */
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.N; j++) 
                System.out.printf("%9.4f ", matrix[i][j]);
            System.out.println();
        }
	}

	public static void input(Matrix M) {
	/* I.S. Matrix M sembarang */
    /* F.S. terbentuk matriks M terdefinisi dengan jumlah baris dan kolom sesuai input */
		/* KAMUS LOKAL */
		int row, col;
		Scanner scan = new Scanner(System.in);
		/* ALGORITMA */
		System.out.println("Masukkan jumlah baris:");
		row = scan.nextInt();
		System.out.println("Masukkan jumlah kolom:");
		col = scan.nextInt();
		double[][] matriks = new double[row][col];
		System.out.println("Masukkan elemen matriks:");
		for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) 
                matriks[i][j] = scan.nextDouble();
		}
		M.setMatrix(matriks);
		M.setRow(row);
		M.setColumn(col);
	}

	public static void inputSPL(Matrix M) {
	/* I.S. Matrix M sembarang */
    /* F.S. terbentuk Matrix M terdefinisi */
    /* Matrix M berupa Matrix augmented */
		/* KAMUS LOKAL */
		int row, col;
		double[][] matriks;
		Scanner scan = new Scanner(System.in);
		/* ALGORITMA */
		System.out.println("Masukkan jumlah baris:");
		row = scan.nextInt();
		System.out.println("Masukkan jumlah variable:");
		col = scan.nextInt();
		col += 1;
		if (row < col-1){
			matriks = new double[col-1][col];
		} else {
			matriks = new double[row][col];
		}
		System.out.println("Masukkan elemen matriks:");
		for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matriks[i][j] = scan.nextDouble();
			}
		}
		if (row < (col-1)){
			for (int i = row ; i < (col-1) ; i++) {
				for (int j = 0 ; j < col ; j++){
					matriks[i][j] = 0;
				}
			}
			M.setRow(col-1);
		} else {
			M.setRow(row);
		}
		M.setMatrix(matriks);
		M.setColumn(col);
	}

	public static void inputNxN(Matrix M) {
	/* I.S. Matrix M sembarang */
    /* F.S. terbentuk Matrix M terdefinisi */
    /* Matrix M berupa Matrix berukuran nxn */
		/* KAMUS LOKAL */
		int row, col;
		Scanner scan = new Scanner(System.in);
		/* ALGORITMA */
		System.out.println("Masukkan n:");
		row = scan.nextInt();
		col = row;
		double[][] matriks = new double[row][col];
		System.out.println("Masukkan elemen matriks:");
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				matriks[i][j] = scan.nextDouble();
			}
		}
		M.setMatrix(matriks);
		M.setRow(row);
		M.setColumn(col);
	}
}
