package Components;
import DataTypes.NodeID;

public class Packet {

	private int id;
	private int type;
	private NodeID sourceNode;
	private NodeID destinationNode;
	private int size;
	private double generationTime;
	public Packet(	int id, int type, NodeID sourceNode, NodeID destinationNode, int size, double generationTime) {
	
		this.setId(id);
		this.setDestinationNode(destinationNode);
		this.setGenerationTime(generationTime);
		this.setType(type);
		this.setSourceNode(sourceNode);
		this.setSize(size);
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public NodeID getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(NodeID sourceNode) {
		this.sourceNode = sourceNode;
	}
	public NodeID getDestinationNode() {
		return destinationNode;
	}
	public void setDestinationNode(NodeID destinationNode) {
		this.destinationNode = destinationNode;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getGenerationTime() {
		return generationTime;
	}
	public void setGenerationTime(double generationTime) {
		this.generationTime = generationTime;
	}
	
}
