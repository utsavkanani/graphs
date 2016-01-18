package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geography.GeographicPoint;


public class RoadEdge {

    private GeographicPoint point1;
    private GeographicPoint point2;
    
   
    private String roadName;
    private String roadType;
    
    // Length in km
    private double length;
    
    public RoadEdge(GeographicPoint pt1, GeographicPoint pt2, String roadName,
                        String roadType, double length)
    {
        point1 = pt1;
        point2 = pt2;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }
    
    
    /** Two road segments are equal if they have the same start and end points
     *  and they have the same road name.
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof RoadEdge)) {
            return false;
        }
        
        RoadEdge other = (RoadEdge)o;
        boolean ptsEqual = false;
        if (other.point1.equals(this.point1) && other.point2.equals(this.point2)) {
            ptsEqual = true;
        }
        if (other.point2.equals(this.point1) && other.point1.equals(this.point2))
        {
            ptsEqual = true;
        }
        return this.roadName.equals(other.roadName) && ptsEqual && this.length == other.length;
    }
    
    // get hashCode
    public int hashCode()
    {
        return point1.hashCode() + point2.hashCode();
    }
    
    // return road segment as String
    public String toString()
    {
        String toReturn = this.roadName + ", " +this.roadType + " [" + point1 + "; " + point2 + "]";
        
        return toReturn;
    }

    // get the length of the road segment
    public double getLength() { return this.length; }
    
    
    // given one end, return the other.
    public geography.GeographicPoint getOtherPoint(geography.GeographicPoint point) {
        if(point.equals(point1)) {
            return point2;
        }
        if(point.equals(point2)) {
            return point1;
        }

        System.out.println("ERROR!! : in RoadSegment::getOtherPoint Neither point matched");
        return null;
    }


    
}

