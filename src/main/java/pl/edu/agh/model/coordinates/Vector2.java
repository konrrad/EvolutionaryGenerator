package pl.edu.agh.model.coordinates;

import java.util.Objects;

public class Vector2 {
    public final int X;
    public final int Y;

    public Vector2(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return X == vector2.X && Y == vector2.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    public boolean lessOrEqual(Vector2 other) {
        return this.X <= other.X && this.Y <= other.Y;
    }

    public boolean biggerOrEqual(Vector2 other) {
        return this.X >= other.X && this.Y >= other.Y;
    }

    public Vector2 add(Vector2 other)
    {
        return new Vector2(this.X+ other.X,this.Y+ other.Y);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
