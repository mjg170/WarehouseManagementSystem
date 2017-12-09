import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class node implements Serializable{
	private node left = null;
	private node right = null;
	private transaction transaction = null;
	private byte[] hashCode;
	
	//Constructor for a leaf node with a transaction
	public node(transaction transaction) throws NoSuchAlgorithmException{
		this.transaction = transaction;
		hashCode = calculateHashCode();
	}
	
	//Constructor for a branch node without transactions
	public node(node left, node right) throws NoSuchAlgorithmException{
		this.left = left;
		this.right = right;
		hashCode = calculateHashCode();
	}
	
	//Constructor for a branch node without transactions and only one left child 
	public node(node left) throws NoSuchAlgorithmException{
		this.left = left;
		hashCode = calculateHashCode();
	}
	
	public node(){
		//Empty Constructor
	}
	
	//Sets the child for the node
	public void setChild(node child){
		if(this.left == null){
			this.left = child;
		}
		else if(this.right == null){
			this.right = child;
		}
	}
	
	//Gets the left node 
	public node getLeft(){
		return left;
	}
	
	//Gets the right node
	public node getRight(){
		return right;
	}
	
	//Gets the HashCode of the node
	public byte[] getHashCode(){
		return hashCode;
	}
	
	//Calculates the hashCode of the node
	private byte[] calculateHashCode() throws NoSuchAlgorithmException{
		if(left == null && right == null){
			return MessageDigest.getInstance("SHA-256").digest(transaction.getTransaction().getBytes(StandardCharsets.UTF_8));
		}
		else if(right == null){
			byte[] tempHash = new byte[left.getHashCode().length + 1];
			System.arraycopy(left.getHashCode(), 0, tempHash, 0, left.getHashCode().length);
			tempHash[tempHash.length - 1] = -1;
			return tempHash;
		}
		else{
			byte[] tempHash = new byte[left.getHashCode().length + right.getHashCode().length];
			System.arraycopy(left.getHashCode(), 0, tempHash, 0, left.getHashCode().length);
			System.arraycopy(right.getHashCode(), 0, tempHash, left.getHashCode().length, right.getHashCode().length);
			return tempHash;
		}
	}
}
