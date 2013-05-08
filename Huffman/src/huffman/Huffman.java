package huffman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import huffman.Node;
import huffman.MapValueComparator;

public class Huffman
{
	public void encrypt(String path)
	{
		HashMap<Byte, Integer> freqDic = new HashMap<Byte, Integer>();
		freqDic = readFile(path);
		MapValueComparator mvc = new MapValueComparator(freqDic);
		TreeMap<Byte, Integer> sortedFreqDic = new TreeMap<Byte, Integer>(mvc);
		sortedFreqDic.putAll(freqDic);
		buildTree(sortedFreqDic);
	}
	
	public void decrypt(String path)
	{
		
	}

    private TreeSet<Node> buildTree(TreeMap<Byte, Integer> sortedFreqDic)
    {
        TreeSet<Node> tree = new TreeSet<Node>();
        for(Map.Entry<Byte, Integer> entry : sortedFreqDic.entrySet())
        	  tree.add(new huffman.Node(entry.getKey(), entry.getValue(), null, null));

        while (tree.size() > 1)
        {
            Node tempNode1 = (Node) tree.first();
            tree.remove(tempNode1);
            Node tempNode2 = (Node) tree.first();
            tree.remove(tempNode2);

            Node merged = new Node(tempNode1, tempNode2);
            tree.add(merged);
        }
        
        return tree;
    }
    
    //reading file based on
    //http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
	private static HashMap<Byte, Integer> readFile(String path)
	{
		HashMap<Byte, Integer> freqDic = new HashMap<Byte, Integer>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileChannel ch = fis.getChannel( );
		ByteBuffer bb = null;
		try {
			bb = ByteBuffer.allocateDirect((int)ch.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int nRead;
		try {
			while ((nRead=ch.read(bb)) != -1)
			{
				bb.position(0);
				bb.limit(nRead);
				while (bb.hasRemaining())
				{
					byte temp = bb.get();
					if (freqDic.containsKey(temp))
						freqDic.put(temp, freqDic.get(temp) + 1);
					else
						freqDic.put(temp, 1);
				}
				bb.clear();
			}
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return freqDic;
	}
}
