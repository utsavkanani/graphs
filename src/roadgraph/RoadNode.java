package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class RoadNode {
    private GeographicPoint location = null;
    private List<RoadEdge> edges = null;
    
    public RoadNode(GeographicPoint node, ArrayList<RoadEdge> nodeEdges) {
        setLocation(node);
        setEdges(nodeEdges);
    }

    public GeographicPoint getLocation() {
        return location;
    }

    protected void setLocation(GeographicPoint location) {
        this.location = location;
    }

    public List<RoadEdge> getEdges() {
        return edges;
    }

    public int getNumEdges() {
        if(edges == null) return 0;
        return edges.size();
    }
    
    protected void setEdges(List<RoadEdge> edges) {
        this.edges = edges;
    }

    public void addEdge(RoadEdge edge) {
        if(edge != null)
            this.edges.add(edge);
    }
    
    public void addEdges(List<RoadEdge> edges) {
        if(edges != null || edges.size() > 0) {
            this.edges.addAll(edges);
        }
            
    }
    
    /** Two road segments are equal if they have the same start and end points
     *  and they have the same road name.
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof RoadNode)) {
            return false;
        }
        
        RoadNode other = (RoadNode)o;
        boolean ptsEqual = false;
        if (other.getLocation().equals(this.location)) {
            ptsEqual = true;
        }
        boolean edgesEqual = false;
        if (other.getEdges().equals(this.edges)) {
            edgesEqual = true;
        }

        return ptsEqual && edgesEqual;
    }
    
    // get hashCode
    public int hashCode()
    {
        return location.hashCode();
    }
    
    // return road segment as String
    public String toString()
    {
        String toReturn = "Location: " + this.location;// + ", Edges:";
        /*
        for(RoadEdge tmp : edges) {
            toReturn += " " + tmp.toString() + ",";
        }
        StringBuilder str = new StringBuilder(toReturn);
        str.setCharAt(toReturn.lastIndexOf(","), ' ');
        return str.toString();
        */
        return toReturn;
    }
}
