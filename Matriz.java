public class Matriz{
    
    public static void main (String [] a){
        //imprimeMatriz(matriz);
        //adicionaMatriz(matriz, fator, modo, index);
        //multiplicaMatriz(matriz, fator, modo, index);
        //int[][] resultado = somaMatrizes(matriz1, matriz2);
    }
    
    /**** IMPRIME MATRIZ ****/
    
    public static void imprimeMatriz (int array[][]){
        int[][] mat = array;
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
     public static void imprimeMatriz (int array[]){
        int[] mat = array;
        for (int i = 0; i < mat.length; i++){
            System.out.print(mat[i] + " ");
        }
    }
    
     public static void imprimeMatriz (String array[][]){
        String[][] mat = array;
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    /**** ADICIONA UMA CONSTANTE AOS ELEMENTOS DA MATRIZ ****/
    
    public static void adicionaMatriz (int array[][], int num){
        int[][] mat = array;
        int constant = num;
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                mat[i][j] = mat[i][j] + constant;
            }
        }        
    }
    
    public static void adicionaMatriz (int array[][], int num1, int num2, int num3){
        int[][] mat = array;
        int constant = num1;
        int mode = num2;
        int index = num3;
        switch (mode){
            case 0:
                for (int i = 0; i < mat[0].length; i++){
                        mat[index][i] = mat[index][i] + constant;
                }
                break;
            case 1:
                for (int i = 0; i < mat.length; i++){
                        mat[i][index] = mat[i][index] + constant;
                }
                break;                
        }
    }
    
    /**** MULTIPLICA ELEMENTOS DA MATRIZ POR UM FATOR CONSTANTE ****/
    
    public static void multiplicaMatriz (int array[][], int num){
        int[][] mat = array;
        int constant = num;
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                mat[i][j] = mat[i][j] * constant;
            }
        }
    }
    
    public static void multiplicaMatriz (int array[][], int num1, int num2, int num3){
        int[][] mat = array;
        int constant = num1;
        int mode = num2;
        int index = num3;
        switch (mode){
            case 0:
                for (int i = 0; i < mat[0].length; i++){
                        mat[index][i] = mat[index][i] * constant;
                }
                break;
            case 1:
                for (int i = 0; i < mat.length; i++){
                        mat[i][index] = mat[i][index] * constant;
                }
                break;                
        }
    }
    
    /**** SOMA DUAS MATRIZES COM DIMENSÕES (m x n) IGUAIS ****/
    
    public static int[][] somaMatrizes (int array1[][], int array2[][]){
        int[][] mat1 = array1;
        int[][] mat2 = array2;
        int[][] soma = new int[mat1.length][mat1[0].length];
        for (int i = 0; i < mat1.length; i++){
            for (int j = 0; j < mat1[0].length; j++){
                soma[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
        return soma;
    }
}