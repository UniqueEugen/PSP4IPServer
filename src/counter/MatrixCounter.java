package counter;

import java.util.stream.IntStream;

public class MatrixCounter implements  GetData{
    private Integer matrixSize;
    private Integer[][] matrix;
    public MatrixCounter(String data){
        String mData[] = data.split(" ");
        this.matrixSize = Integer.parseInt(mData[0]);
        matrix= new Integer[matrixSize][matrixSize];
        final int[] count = {1};
        IntStream.range(0, matrixSize)
                .forEach(row -> IntStream.range(0, matrixSize)
                        .forEach(column ->{
                            matrix[row][column] = Integer.parseInt(mData[count[0]]);
                            count[0]++;
                        }));
    }
    public String count(){
        System.out.println(matrixSize);
        Double mainD=0.0;
        Double subD=0.0;
        for(int i=0; i<= matrixSize-1; i++){
            mainD+=matrix[i][i];
            subD+=matrix[i][matrixSize-i-1];
            System.out.println(mainD+ " " +subD);
        }
        Double answer = (mainD/matrixSize)/(subD/matrixSize);
        System.out.println(answer);
        if (answer==0) return "0";
        else return  answer.toString();
    }
}
