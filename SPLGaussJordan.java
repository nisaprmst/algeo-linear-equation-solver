public class SPLGaussJordan {

public static Matrix solveGaussJordan(Matrix M){
    int i, j, k;
    double x;
    double[][] M_in = M.getMatrix();
    int n_brs = M.getRow();
    int n_kol = M.getColumn();
    double[][] z = new double[n_brs][n_kol];
    Matrix result = new Matrix(z);

    //make reduction echelon tapi belom 1 1
    for(j = 0 ; j < n_brs ; j++){
        for (i = 0 ; i < n_brs ; i++){
            if (i != j){
                if (M_in[j][j] != 0){
                    x = M_in[i][j]/M_in[j][j];
                    for (k=0 ; k < n_brs+1 ; k++) {
                        M_in[i][k] = M_in[i][k] - x*M_in[j][k];
                    }
                }
            }
        }
    }
    
    //bikin jadi 1 1
    for (int p = 0 ; p < n_brs ; p++){
        int q = 0;
        while (M_in[p][q]== 0 && q<n_kol-1){
            q++;
        }
        if (q != n_kol-1){
            for (int r = q+1 ; r < n_kol ; r++){
                //System.out.print(y);
                //System.out.print("\n");
                M_in[p][r] = M_in[p][r] / M_in[p][q];
            }
            M_in[p][q] = 1;
        }
    }
    result.setRow(n_brs);
    result.setColumn(n_kol);
    result.setMatrix(M_in);
    return result;
}

public static void showResult(Matrix matrix){
    //untuk nampilin hasil spl sesuai kondisi

}

//buat test
public static void main(String[] args){
    double arr [][] = {{1,5,6,9, 7},{1,5,6,9, 7},{5,6,8,9, 15},{0,0,8,3,10}}; 
    Matrix matrix = new Matrix (arr);
    matrix.show();
    System.out.print("==========================\n");
    matrix = solveGaussJordan(matrix);
    matrix.show();
}

}