
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
    Quadrant parentQuadrant;

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

    public Quadrant (int noOfRestaurants, Location centre, float halfHeight, float halfWidth, Quadrant parentQuadrant) {
        this.noOfRestaurants = noOfRestaurants;
        this.status = QuadrantStatus.EMPTY;
        this.centre = centre;
        this.halfHeight = height;
        this.halfWidth = width
        this.restaurants = new ArrayList<>();
        this.parentQuadrant = parentQuadrant;
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
                Quadrant subQuadrant = findSubQuadrant(restaurant.location);
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
    }

    private Quadrant findSubQuadrant(Location location) {
        if (location.x >= this.centre.x && location.y >= this.centre.y) return this.NE;
        if (location.x >= this.centre.x && location.y < this.centre.y) return this.SE;
        if (location.x < this.centre.x && location.y >= this.centre.y) return this.NW;
        return this.SW;
    }


    private Quadrant createSubQuadrants() {
        Location nwCentreLocation = new Location(this.centre.x - halfWidth/2, this.centre.y + halfHeight/2)
        this.NW = new Quadrant(this.noOfRestaurants, nwCentreLocation, halfHeight/2, halfWidth/2, this);
        Location neCentreLocation = new Location(this.centre.x + halfWidth/2, this.centre.y + halfHeight/2)
        this.NE = new Quadrant(this.noOfRestaurants, neCentreLocation, halfHeight/2, halfWidth/2, this);
        Location swCentreLocation = new Location(this.centre.x - halfWidth/2, this.centre.y - halfHeight/2)
        this.SW = new Quadrant(this.noOfRestaurants, swCentreLocation, halfHeight/2, halfWidth/2, this);
        Location seCentreLocation = new Location(this.centre.x + halfWidth/2, this.centre.y - halfHeight/2)
        this.SE = new Quadrant(this.noOfRestaurants, seCentreLocation, halfHeight/2, halfWidth/2, this);
    }

    public Quadrant findQuadrant(Location location) {
        if (this.status == DIVIDED) {
            Quadrant subQuadrant =  findSubQuadrant(Location location);
            return subQuadrant.findQuadrant(location);
        }
        return this;
    }

    public Set<Restaurant> searchNearbyRestaurants(Location location, int count, Set<Restaurant> restaurants, int iteration, Map<Quadrant, Boolean> visitedQuadrant) {
        Quadrant quadrant = findQuadrant(location);
        this.searchNearbyRestaurants(count, restaurants, iteration, visitedQuadrant);
    }

    public Set<Restaurant> searchNearbyRestaurants(int count, Set<Restaurant> restaurants, int iteration, Map<Quadrant, Boolean> visitedQuadrant) {
        if (iteration <= 0 ) return restaurants;
        if (visitedQuadrant.find(this)) return restaurants;
        visitedQuadrant.put(this, true);
        if (this.status == DIVIDED){
            this.NE.searchNearbyRestaurants(count, restaurants, iteration, visitedQuadrant);
            this.NW.searchNearbyRestaurants(count, restaurants, iteration, visitedQuadrant);
            this.SE.searchNearbyRestaurants(count, restaurants, iteration, visitedQuadrant);
            this.SW.searchNearbyRestaurants(count, restaurants, iteration, visitedQuadrant);
            this.parentQuadrant.searchNearbyRestaurants(count, restaurants, iteration - 1, visitedQuadrant)
            return restaurants;
        } else {
            List<Restaurant> list = this.restaurants;
            for (int i = 0; i < list.length; i++){
                restaurants.add(restaurant);
                count -- ;
            } 
            if (count > 0){
                iteration --;
                return this.parentQuadrant.searchNearbyRestaurants(count, restaurant, iteration, visitedQuadrant);
            }
            return restaurants;
        }


    }

    public List<Restaurant> searchNearbyRestaurants(Location location, int count, int promiximity, int iteration) {
        if (location.x > this.centre.x + halfWidth || location.x < this.centre.x - halfWidth) return new ArrayList<>();
        if (location.y > this.centre.y + halfHeight || location.y < this.centre.y - halfHeight) return new ArrayList<>();
        locationsToSearch.add(new Location(location.x + promiximity, location.y + promiximity));
        locationsToSearch.add(new Location(location.x + promiximity, location.y - promiximity));
        locationsToSearch.add(new Location(location.x - promiximity, location.y + promiximity));
        locationsToSearch.add(new Location(location.x - promiximity, location.y - promiximity));
        Map<Quadrant, Boolean> visitedQuadrant = new HashMap<>();
        Set<Restaurant> restaurants = new Hashset<>();
        locationsToSearch.forEach(location -> searchNearbyRestaurants(location, count, promiximity, restaurants, iteration, visitedQuadrant));
    }




}