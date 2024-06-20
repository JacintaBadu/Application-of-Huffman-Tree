/*authors: Jacinta Esi Amoawah badu
* & Theresah Owusu
*/
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.PriorityQueue;
import java.util.Comparator;

// Internal node of Huffman Tree
class Node {
    long frequency;
    Node leftChild;
    Node rightChild;

    public Node(long frequency) {
        this.frequency = frequency;
    }

}

//node class for the leaf nodes (actual characters of message)
class CharacterNode extends Node {
    char character;

    public CharacterNode(char character, long frequency) {
        super(frequency);
        this.character = character;
    }
}

// Comparator class for nodes
// Used to sort nodes by priority
class NodeComparator implements Comparator<Node> {
    public int compare(Node left, Node right) {
        return Long.compare(left.frequency, right.frequency);
    }
}

// Huffman tree. Used to generate huffman code
// Also used to encode and decode messages
public class HuffmanTree {

    Node root;

    public HuffmanTree(String message) {

        buildHuffmanTree(message);
    }

    private void buildHuffmanTree(String message) {

        // Create a mapping of every character in the message and it's count
        Map<Character, Long> FrequencyDistributor = message.chars()
                .mapToObj(i -> (char) i).collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()));

        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new NodeComparator());

        priorityQueue.addAll(
                FrequencyDistributor.entrySet().stream()
                        .map(entry -> new CharacterNode(entry.getKey(),entry.getValue()))
                        .collect(Collectors.toList()));



        while (priorityQueue.size() > 1) {

            // extracting the first and second minimum nodes.
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            // new node to store the sum of the minimum nodes
            Node internalNode = new Node(left.frequency + right.frequency);

            //moving left and right to extract the left and right children to add
            internalNode.leftChild = left;
            internalNode.rightChild = right;

            // store the final sum as the root node when size reduced to 1
            root = internalNode;

            // and add to the priority-queue.
            priorityQueue.add(internalNode);
        }
    }


    public HashMap<Character, String> getCode() {
        
        var map = new HashMap<Character, String>();

        getCodeHelper(this.root, map, "");

        return map;
    }

    public void getCodeHelper(Node root, HashMap<Character, String> cacheMap, String currentMessage) {

        //traversing the tree from root to down if there are other nodes apart from root
        if (root instanceof CharacterNode) {
            CharacterNode leaf = (CharacterNode) root;
            cacheMap.put(leaf.character, currentMessage);
            return;
        }

        /*call for leftChild and rightChild of the generated tree 
        *and assigning 0 if left and 1 if right . */
        getCodeHelper(root.leftChild, cacheMap, currentMessage + "0");
        getCodeHelper(root.rightChild, cacheMap, currentMessage + "1");

    }

}
