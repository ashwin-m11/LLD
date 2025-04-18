
public class Quadrant {
    int noOfRestaurants = 5;
    QuadrantStatus status;
    Location centre;
    float halfHeight;
    float halfWidth;
    List<Restaurant> restaurants;
    Quadrant NW;
    Quadrant NE;
    Quadrant SW;
    Quadrant SE;

    public Quadrant (Integer noOfRestaurants, QuadrantStatus status, Location centre, float halfHeight, float halfWidth) {
        this.noOfRestaurants = noOfRestaurants;
        this.status = status;
        this.centre = centre;
        this.halfHeight = height;
        this.halfWidth = width
        this.restaurants = new ArrayList<>();
    }

    public Quadrant (int noOfRestaurants, Location centre, float halfHeight, float halfWidth) {
        this.noOfRestaurants = noOfRestaurants;
        this.status = QuadrantStatus.EMPTY;
        this.centre = centre;
        this.halfHeight = height;
        this.halfWidth = width
        this.restaurants = new ArrayList<>();
    }

    public addRestaurant (Restaurant restaurant) {
        //assuming data is not out of range
        switch (this.status){
            case QuadrantStatus.EMPTY: 
            case QuadrantStatus.PARTIALLY_FILLED:
                this.restaurants.add(restaurant);
                if (this.restaurants.length == this.noOfRestaurants){
                    this.status = Quadrant.FILLED;
                } else this.status = Quadrant.PARTIALLY_FILLED;
                break;
            case Quadrant.DIVIDED:
                Quadrant subQuadrant = findSubQuadrant(restaurant);
                subQuadrant.addRestaurant(restaurant);
                break;
            case Quadrant.FILLED:
                this.createSubQuadrants();
                this.status = Quadrant.DIVIDED;
                var restaurants = this.restaurants;
                restaurants.add(restaurant);
                this.restaurants = null;
                for (int i = 0; i < restaurants.length(); i++){
                    this.addRestaurant(restaurants[i]);
                }
            break;
        }
        if (restaurant.length < noOfRestaurants)
    }

    private Quadrant findSubQuadrant(Restaurant restaurant) {
        if (restaurant.location.x >= this.centre.location.x && restaurant.location.y >= this.centre.y) return this.NE;
        if (restaurant.location.x >= this.centre.location.x && restaurant.location.y < this.centre.y) return this.SE;
        if (restaurant.location.x < this.centre.location.x && restaurant.location.y >= this.centre.y) return this.NW;
        return this.SW;
    }


    private Quadrant createSubQuadrants() {
        Location nwCentreLocation = new Location(this.centre.x - halfWidth/2, this.centre.y + halfHeight/2)
        this.NW = new Quadrant(this.noOfRestaurants, nwCentreLocation, halfHeight/2, halfWidth/2);
        Location neCentreLocation = new Location(this.centre.x + halfWidth/2, this.centre.y + halfHeight/2)
        this.NE = new Quadrant(this.noOfRestaurants, neCentreLocation, halfHeight/2, halfWidth/2);
        Location swCentreLocation = new Location(this.centre.x - halfWidth/2, this.centre.y - halfHeight/2)
        this.SW = new Quadrant(this.noOfRestaurants, swCentreLocation, halfHeight/2, halfWidth/2);
        Location seCentreLocation = new Location(this.centre.x + halfWidth/2, this.centre.y - halfHeight/2)
        this.SE = new Quadrant(this.noOfRestaurants, seCentreLocation, halfHeight/2, halfWidth/2);
    }


    public List<Restaurant> searchNearbyRestaurants(Location location) {

    }




}