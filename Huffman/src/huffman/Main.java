package huffman;

public class Main {

	public static void main(String[] args) 
	{
		long startTime = System.currentTimeMillis();
		args = new String[1];
		args[0] = "-";
		if (args[0] == "-e")
		{
			new Huffman().encrypt("C:\\test.txt");
		} else if (args[0] == "-d")
		{
			new Huffman().decrypt("C:\\test.txt");
		}
		else
		{
			System.out.println("Usage : \n\t-e <path>\tEncrypt file\n\t-d <path>\tDecrypt file");
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time : " + (endTime - startTime) + " milliseconds");
	}
}
