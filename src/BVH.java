import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class BVH implements Iterable<Circle>{
    Box boundingBox;

    BVH child1;
    BVH child2;
    static byte  in = (byte)(Byte.MAX_VALUE + 1);

    Circle containedCircle;

    // todo for students
    public BVH(ArrayList<Circle> circles) {

        boundingBox = buildTightBoundingBox(circles);
        if(circles.size() == 0){
            return;
        }
        if(circles.size() == 1){
            containedCircle = circles.get(0);
            return;
        }

        ArrayList<Circle> left = split(circles,boundingBox)[0];
        ArrayList<Circle> right = split(circles,boundingBox)[1];
        child1 = new BVH(left);
        child2 = new BVH(right);


    }

    public void draw(Graphics2D g2) {
        this.boundingBox.draw(g2);
        if (this.child1 != null) {
            this.child1.draw(g2);
        }
        if (this.child2 != null) {
            this.child2.draw(g2);
        }
    }

    // todo for students
    public static ArrayList<Circle>[] split(ArrayList<Circle> circles, Box boundingBox) {

        ArrayList<Circle>[] splitArray = new ArrayList[2];

        if(boundingBox.getHeight() >= boundingBox.getWidth()){
            double midPoint = boundingBox.getMidY();
            ArrayList<Circle> left = new ArrayList<>();
            ArrayList<Circle> right = new ArrayList<>();
            for(Circle circle : circles){
                if(circle.position.y <= midPoint){
                    left.add(circle);
                }else{
                    right.add(circle);
                }
            }
            splitArray[0] = left;
            splitArray[1]= right;
            return splitArray;

        }else{
            double midPoint = boundingBox.getMidX();
            ArrayList<Circle> left = new ArrayList<>();
            ArrayList<Circle> right = new ArrayList<>();
            for(Circle circle : circles){
                if(circle.position.x <= midPoint){
                    left.add(circle);
                }else{
                    right.add(circle);
                }
            }
            splitArray[0] = left;
            splitArray[1]= right;
            return splitArray;
        }

    }

    // returns the smallest possible box which fully encloses every circle in circles
    public static Box buildTightBoundingBox(ArrayList<Circle> circles) {
        Vector2 bottomLeft = new Vector2(Float.POSITIVE_INFINITY);
        Vector2 topRight = new Vector2(Float.NEGATIVE_INFINITY);

        for (Circle c : circles) {
            bottomLeft = Vector2.min(bottomLeft, c.getBoundingBox().bottomLeft);
            topRight = Vector2.max(topRight, c.getBoundingBox().topRight);
        }

        return new Box(bottomLeft, topRight);
    }

    // METHODS BELOW RELATED TO ITERATOR

    // todo for students
    @Override
    public Iterator<Circle> iterator() {
        return new BVHIterator(this);
    }

    public class BVHIterator implements Iterator<Circle> {

        // todo for students
        LinkedList<Circle> list = new LinkedList<>();

        public BVHIterator(BVH bvh) {

//            if(bvh.containedCircle != null){
//                list.addLast(bvh.containedCircle);
//                return;
//            }
//            new BVHIterator(bvh.child1);
//            new BVHIterator(bvh.child2);

            findCircle(bvh);

        }
        private void findCircle(BVH bvh){
            if(bvh == null){
                return;
            }
            if(bvh.containedCircle != null){
                list.addLast(bvh.containedCircle);
                return;
            }
            findCircle(bvh.child1);
            findCircle(bvh.child2);
        }

        // todo for students
        @Override
        public boolean hasNext() {
            return list.size() != 0;
        }

        // todo for students
        @Override
        public Circle next() {
            return list.removeFirst();

        }
    }

    public static void main(String[] args) {
        System.out.println(in);
    }
}