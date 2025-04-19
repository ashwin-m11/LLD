
public class Main {
    

    public void searchRestaurant (Location location) {
        //assuming data is not out of range
        Quadrant globalQuadrant = new Quadrant(5, new Location(0,0), 100, 100);
        Quadrant quadrant = globalQuadrant.findQuadrant(location);

        
    }



}