package Util;

public class Vector2D {
	public double x;
	public double y;

	public Vector2D() {
		x = 0.0;
		y = 0.0;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D vec) {
		x = vec.x;
		y = vec.y;
	}
	
	public Vector2D addLocal(Vector2D addend) {
		x += addend.x;
		y += addend.y;
		return this;
	}

	public Vector2D add(Vector2D addend) {
		return new Vector2D(x + addend.x, y + addend.y);
	}

	public Vector2D subLocal(Vector2D subtrahend) {
		x -= subtrahend.x;
		y -= subtrahend.y;
		
		return this;
	}
	
	public Vector2D sub(Vector2D subtrahend) {
	   return new Vector2D(x - subtrahend.x, y - subtrahend.y);
	}
	
	public Vector2D divLocal(double scalar) {
		if(scalar != 0) {
			x /= scalar;
			y /= scalar;
		}
		
		return this;
	}

	public Vector2D div(double scalar) {
	   return new Vector2D(x / scalar, y / scalar);
	}

	public Vector2D mulLocal(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}
	
	public Vector2D mul(double scalar) {
	   return new Vector2D(x * scalar, y * scalar);
	}

	public Vector2D set(Vector2D vec) {
		x = vec.x;
		y = vec.y;
		return this;
	}
	
	public Vector2D set(double x, double y) {
	   this.x = x;
	   this.y = y;
	   return this;
	}

	public boolean equals(Vector2D vec) {
		return (x == vec.x) && (y == vec.y);
	}
	
	public double dotProduct(Vector2D vec) {
		return vec.x*x + vec.y*y;
	}

	public double getMagnitude() {
		return Math.sqrt((x*x) + (y*y));
	}

	public Vector2D getUnitVector() {
		double mag = getMagnitude();
		return new Vector2D(x/mag, y/mag);
	}
	
	public static Vector2D unitVectorFromAngle(double angle) {
		return new Vector2D(Math.cos(angle), Math.sin(angle));
	}

	public Vector2D projectOn(Vector2D ProjVec) {
		Vector2D vec = ProjVec.getUnitVector();
		return vec.mulLocal(dotProduct(vec));
	}
	
	@Override
	public String toString() {
		return "<" + Double.toString(x) + ", " + Double.toString(y) + ">";
	}
}
