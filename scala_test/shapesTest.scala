trait MeasureablePolygon {
    def perimeter(): Float 
    def area(): Float 
}

trait ReportablePolygon extends MeasureablePolygon {
    def report() = {
        println(this + ", perimeter: " + this.perimeter() + ", area: " + this.area())
    }
}

class Rectangle(h: Float, w: Float) extends ReportablePolygon {
    val height = h
    val width = w
    val perimeterLen = height * 2 + width * 2
    val areaSize = height * width
    
    override def perimeter = perimeterLen

    override def area = areaSize

    override def toString = "Rectangle: " + height + " x " + width
}

class Square(side: Float) extends Rectangle(side, side)

class Triangle(s1: Float, s2: Float, s3: Float) extends ReportablePolygon {
    val side1 = s1
    val side2 = s2
    val side3 = s3
    val perimeterLen = side1 + side2 + side3
    // Heron's formula for the area of a triangle
    val s = perimeterLen / 2
    val areaSize = math.sqrt(s * (s - side1) * (s - side2) * (s - side3)).toFloat

    override def perimeter = perimeterLen

    override def area = areaSize

    override def toString = "Triangle: " + side1 + " x " + side2 + " x " + side3
}

val r = new Rectangle(5, 2)
r.report()

val s = new Square(5)
s.report()

val t = new Triangle(3,4,5)
t.report()
