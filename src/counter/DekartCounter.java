package counter;

import java.util.stream.IntStream;

public class DekartCounter implements GetData{
    private double x;
    private double y;
    public DekartCounter(String data){
        String mData[] = data.split(" ");
        this.x = Double.parseDouble(mData[0]);
        this.y = Double.parseDouble(mData[1]);
    }
    public String count(){
        return   x > 0 && y > 0 ? "I координатная четверть"
                : x < 0 && y > 0 ? "II координатная четверть"
                : x < 0 && y < 0 ? "III координатная четверть"
                : x > 0 && y < 0 ? "IV координатная четверть"
                : x==0 && y==0 ?"Лежит в точке начала координат"
                : x==0  ? "Лежит на оси X"
                :"Лежит на оси Y";
    }
}
