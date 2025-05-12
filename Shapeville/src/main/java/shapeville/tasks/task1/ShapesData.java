package shapeville.tasks.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapesData {
    private static final List<Shape2D> SHAPES_2D = List.of(
        new Shape2D("circle", "A perfectly round shape"),
        new Shape2D("rectangle", "A shape with 4 sides and 4 right angles"),
        new Shape2D("triangle", "A shape with 3 sides"),
        new Shape2D("oval", "An elongated circle")
        // Add more shapes as needed
    );
    
    private static final List<Shape3D> SHAPES_3D = List.of(
        new Shape3D("cube", "A box-shaped object with 6 equal square faces"),
        new Shape3D("sphere", "A perfectly round 3D shape"),
        new Shape3D("cylinder", "A tube-shaped object with circular ends"),
        new Shape3D("pyramid", "A shape with a polygon base and triangular sides")
        // Add more shapes as needed
    );
    
    public static List<Shape2D> get2DShapes() {
        List<Shape2D> shuffled = new ArrayList<>(SHAPES_2D);
        Collections.shuffle(shuffled);
        return shuffled;
    }
    
    public static List<Shape3D> get3DShapes() {
        List<Shape3D> shuffled = new ArrayList<>(SHAPES_3D);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}