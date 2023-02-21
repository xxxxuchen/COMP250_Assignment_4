import java.util.ArrayList;
import java.util.HashSet;

public class ContactFinder {
    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n^2) where n is the number of circles.
    public static HashSet<ContactResult> getContactsNaive(ArrayList<Circle> circles) {

        HashSet<ContactResult> results = new HashSet<>();
        for(int i = 0; i < circles.size(); i++){
            for(int j = 0; j < circles.size(); j++){
                if( circles.get(i).id != circles.get(j).id){
                    if(circles.get(i).isContacting(circles.get(j)) != null){
                        results.add(circles.get(i).isContacting(circles.get(j)));
                    }
                }
            }
        }
        return results;
    }

    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n*log(n)) where n is the number of circles.
    public static HashSet<ContactResult> getContactsBVH(ArrayList<Circle> circles, BVH bvh) {
        HashSet<ContactResult> results1 = new HashSet<>();
        for(Circle circle : circles){
            HashSet<ContactResult> result = getContactBVH(circle,bvh);
            results1.addAll(result);
        }
        return results1;
    }

    // todo for students
    // Takes a single circle c and a BVH bvh.
    // Returns a HashSet of ContactResult objects representing contacts between c
    // and the circles contained in the leaves of the bvh.
    public static HashSet<ContactResult> getContactBVH(Circle c, BVH bvh) {

        if(bvh == null){
            return null;
        }

        if(!c.getBoundingBox().intersectBox(bvh.boundingBox)){
            return new HashSet<ContactResult>();
        }

        if(bvh.containedCircle != null && c.id != bvh.containedCircle.id && c.isContacting(bvh.containedCircle)!= null){
            HashSet<ContactResult> results = new HashSet<>();
            results.add(c.isContacting(bvh.containedCircle));
            return results;
        }

        HashSet<ContactResult> finalResults = new HashSet<>();

        if(bvh.child1 != null){
            HashSet<ContactResult> res1 = getContactBVH(c, bvh.child1);
            if(res1.size() != 0){
                finalResults.addAll(res1);
            }

        }
        if(bvh.child2 != null){
            HashSet<ContactResult> res2 = getContactBVH(c, bvh.child2);
            if(res2.size() != 0){
                finalResults.addAll(res2);
            }
        }
        return finalResults;
    }
}
