import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class block implements Serializable{
	private node head = null;
	private block previous = null;
	private block next = null;
	private int number;
	public transaction[] transactions = null;
	
	public byte[] hashCode;
	public long timeStamp = System.currentTimeMillis();
	private int nonce = 0;
	
	//Constructor for a block
	public block(block previous, transaction[] transactions) throws NoSuchAlgorithmException{
		this.transactions = transactions;
		head = buildMarkovTree(transactions);
		this.previous = previous;
		number = previous.getNumber() + 1;
		previous.setNext(this);
		hashCode = calculateHashCode();
	}
	
	//Constructor for the very first block
	public block(String string){
		//Genesis Constructor
		hashCode = new byte[32];
		number = 1;
		for(byte bite: hashCode){
			bite = -1;
		}
	}
	
	public block(){
		//Empty Constructor
	}
	
	//Builds a Markov Tree to calculate the hashcode
	private node buildMarkovTree(transaction[] transactions) throws NoSuchAlgorithmException{
		node left = null;
		node right = null;
		node node = null;
		
		node[] buildArray = new node[(int)Math.ceil(Math.log(transactions.length)/Math.log(2))];
		node[] scanArray = new node[100];
		
		for(int i = 0, j = 0; i < transactions.length; i++){
			if(left == null && right == null && i != transactions.length - 1){
				left = new node(transactions[i]);
			}
			else if(left == null && right == null && i == transactions.length - 1){
				left = new node(transactions[i]);
				node = new node(left);
				buildArray[j] = node;
				j++;
				node = null;
				left = null;
				right = null;
			}
			else if(left != null && right == null){
				right = new node(transactions[i]);
				node = new node(left, right);
				buildArray[j] = node;
				j++;
				node = null;
				left = null;
				right = null;
			}
		}
		
		while(buildArray.length != 1){
			scanArray = buildArray;
			buildArray = new node[(int)Math.ceil(Math.log(scanArray.length)/Math.log(2))];
			
			for(int i = 0, j = 0; i < scanArray.length; i++){
				if(left == null && right == null && i != scanArray.length - 1){
					left = new node(scanArray[i]);
				}
				else if(left == null && right == null && i == scanArray.length - 1){
					left = new node(scanArray[i]);
					node = new node(left);
					buildArray[j] = node;
					j++;
					node = null;
					left = null;
					right = null;
				}
				else if(left != null && right == null){
					right = new node(scanArray[i]);
					node = new node(left, right);
					buildArray[j] = node;
					j++;
					node = null;
					left = null;
					right = null;
				}
			}
		}
		return buildArray[0];
	}
	
	//Returns the block's hashCode
	public byte[] getHashCode(){
		return hashCode;
	}
	
	//Returns the list of transactions
	public transaction[] getTransactions(){
		return transactions;
	}
	
	//Returns the block's next block
	public block getNext(){
		return next;
	}
	
	//Gets the number that the block is 
	public int getNumber(){
		return number;
	}
	
	//Sets the block's next block
	public void setNext(block next){
		this.next = next;
	}
	
	//Calculates the hashCode from the previous block and hashCode of the Markov Tree
	private byte[] calculateHashCode() throws NoSuchAlgorithmException {
		byte[] tempHash;
		byte[] byteNonce;
		byte[] byteTimeStamp;
		
		for(nonce = 0; hashCode == null || hashCode[0] != 0 || hashCode[1] != 0 || hashCode[2] != 0 || hashCode[3] == 0; nonce++){
			ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
			
			bb.putInt(nonce);
			byteNonce = bb.array();
			
			bb = ByteBuffer.allocate(Long.BYTES);
			bb.putLong(timeStamp);
			byteTimeStamp = bb.array();
			
			tempHash = new byte[previous.getHashCode().length + head.getHashCode().length + byteTimeStamp.length + byteNonce.length];
			
			System.arraycopy(previous.getHashCode(), 0, tempHash, 0, previous.getHashCode().length);
			System.arraycopy(head.getHashCode(), 0, tempHash, previous.getHashCode().length, head.getHashCode().length);
			System.arraycopy(byteTimeStamp, 0, tempHash, previous.getHashCode().length + head.getHashCode().length, byteTimeStamp.length);
			System.arraycopy(byteNonce, 0, tempHash, previous.getHashCode().length + head.getHashCode().length + byteTimeStamp.length, byteNonce.length);
			hashCode = MessageDigest.getInstance("SHA-256").digest(tempHash);
		}
		return hashCode;
	}
}
