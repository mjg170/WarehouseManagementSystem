import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class node {
	private node left = null;
	private node right = null;
	private transaction transaction = null;
	private byte[] hashCode;
	
	public node(transaction transaction) throws NoSuchAlgorithmException{
		this.transaction = transaction;
		hashCode = calculateHashCode();
	}
	
	public node(node left, node right) throws NoSuchAlgorithmException{
		this.left = left;
		this.right = right;
		hashCode = calculateHashCode();
	}
	
	public node(node left) throws NoSuchAlgorithmException{
		this.left = left;
		hashCode = calculateHashCode();
	}
	
	public void setChild(node child){
		if(this.left == null){
			this.left = child;
		}
		else if(this.right == null){
			this.right = child;
		}
	}
	
	public node getLeft(){
		return left;
	}
	
	public node getRight(){
		return right;
	}
	
	public byte[] getHashCode(){
		return hashCode;
	}
	
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
