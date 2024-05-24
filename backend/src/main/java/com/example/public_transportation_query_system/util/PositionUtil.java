package com.example.public_transportation_query_system.util;

public class PositionUtil {

    // 地球半径，单位米
    public static final double EARTH_RADIUS = 6371000.0;

    /**
     * 反余弦计算公式计算经纬度距离
     * @param longitude1 经度 1
     * @param latitude1 纬度 1
     * @param longitude2 经度 2
     * @param latitude2 纬度 2
     * @return
     */
    public static double getDistanceDouble(Double longitude1, Double latitude1, Double longitude2, Double latitude2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(longitude1);// A经弧度
        double radiansAY = Math.toRadians(latitude1);// A纬弧度
        double radiansBX = Math.toRadians(longitude2);// B经弧度
        double radiansBY = Math.toRadians(latitude2);// B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX) + Math.sin(radiansAY) * Math.sin(radiansBY);

        // log.info("cos = " + cos);//值域[-1,1]
        double acos = Math.acos(cos);// 反余弦值

        // log.info("acos = " + acos);//值域[0,π]
        // log.info("∠AOB = " + Math.toDegrees(acos));//球心角 值域[0,180]
        return EARTH_RADIUS * acos;// 最终结果
    }
}
