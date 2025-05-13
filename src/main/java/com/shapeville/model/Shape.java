package com.shapeville.model;

/**
 * 基础形状类，提供所有形状的共同属性和方法
 */
public class Shape {
    // 基本属性
    private String name;        // 形状名称
    private String type;        // 形状类型
    private double area;        // 面积
    private double perimeter;   // 周长
    private int angles;         // 角的数量
    private String color;       // 颜色
    
    // 构造函数
    public Shape() {
        this.name = "未命名形状";
        this.type = "未知类型";
        this.color = "白色";
    }
    
    public Shape(String name, String type) {
        this.name = name;
        this.type = type;
        this.color = "白色";
    }
    
    public Shape(String name, String type, double area, double perimeter, int angles) {
        this.name = name;
        this.type = type;
        this.area = area;
        this.perimeter = perimeter;
        this.angles = angles;
        this.color = "白色";
    }
    
    // 基本 getter 和 setter 方法
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getArea() {
        return area;
    }
    
    public void setArea(double area) {
        this.area = area;
    }
    
    public double getPerimeter() {
        return perimeter;
    }
    
    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
    
    public int getAngles() {
        return angles;
    }
    
    public void setAngles(int angles) {
        this.angles = angles;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    // 通用方法
    
    /**
     * 判断是否为正多边形
     * @return 是否为正多边形
     */
    public boolean isRegular() {
        // 默认实现，子类可以重写
        return false;
    }
    
    /**
     * 计算形状面积
     * @return 形状面积
     */
    public double calculateArea() {
        // 默认返回已存储的面积，子类应重写此方法提供具体计算
        return this.area;
    }
    
    /**
     * 计算形状周长
     * @return 形状周长
     */
    public double calculatePerimeter() {
        // 默认返回已存储的周长，子类应重写此方法提供具体计算
        return this.perimeter;
    }
    
    /**
     * 检查形状是否为封闭形状
     * @return 是否为封闭形状
     */
    public boolean isClosed() {
        // 默认大多数形状是封闭的
        return true;
    }
    
    /**
     * 检查是否为二维形状
     * @return 是否为二维形状
     */
    public boolean is2D() {
        // 默认为二维形状
        return true;
    }
    
    /**
     * 检查是否为三维形状
     * @return 是否为三维形状
     */
    public boolean is3D() {
        // 默认不是三维形状
        return false;
    }
    
    /**
     * 获取形状的角度总和
     * @return 角度总和
     */
    public double getAngleSum() {
        // 多边形内角和公式：(n-2)*180
        if (angles > 2) {
            return (angles - 2) * 180;
        }
        return 0;
    }
    
    /**
     * 检查是否为圆形或椭圆形
     * @return 是否为圆形或椭圆形
     */
    public boolean isCircular() {
        return false;
    }
    
    /**
     * 形状描述
     * @return 形状的文字描述
     */
    public String getDescription() {
        return "这是一个" + name + "，类型为" + type + 
               "，面积为" + area + "，周长为" + perimeter + 
               "，有" + angles + "个角。";
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Shape shape = (Shape) obj;
        
        if (Double.compare(shape.area, area) != 0) return false;
        if (Double.compare(shape.perimeter, perimeter) != 0) return false;
        if (angles != shape.angles) return false;
        if (!name.equals(shape.name)) return false;
        if (!type.equals(shape.type)) return false;
        return color.equals(shape.color);
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + type.hashCode();
        temp = Double.doubleToLongBits(area);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(perimeter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + angles;
        result = 31 * result + color.hashCode();
        return result;
    }
}