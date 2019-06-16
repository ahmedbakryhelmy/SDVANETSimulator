package DataTypes;

public class NodeID {

	public int x;
	public int y;
	
	public NodeID(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean equals(NodeID n) {
		if(this.x == n.x && this.y == n.y) {
			return true;
		}else {
			return false;
		}
	}
}
