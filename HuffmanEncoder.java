/*authors: Jacinta Esi Amoawah badu
* & Theresah Owusu
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HuffmanEncoder {
    private HuffmanTree tree;

    public HuffmanEncoder() {
        this.tree = null;
    }

    public HuffmanEncoder(HuffmanTree tree) {
        this.tree = tree;
    }

    /**
     * @return the tree
     */
    public HuffmanTree getTree() {
        return tree;
    }
 /*----------------The encoding part of the work ------------------------------------*/
    public String encode(String message) {

        if (tree == null) {
            this.tree = new HuffmanTree(message);
        }

        HashMap<Character, String> codeMap = tree.getCode();

        System.out.println("Codemap: " + codeMap);

        StringBuffer codeBuilder = new StringBuffer();

/* using for loop to loop through the input message 
*and giving it respective key(in binary form)
*/
        for (char character : message.toCharArray()) {
            codeBuilder.append(codeMap.getOrDefault(character, ""));
        }

        return codeBuilder.toString();

    }
 /* ----------------------------End of the encoding code ---------------------------------**/

 /*-------- Decoding the encoded message  using the huffman tree --------------*/
    public String decode(String encodedMessage) {
        Objects.requireNonNull(tree, "Need to encode before decoding");

        return decodeWithTreeHelper(encodedMessage);
    }

    public String decodeWithCodeMap(String encodedMessage, HashMap<Character, String> codeMap) {
    
        Map<String, Character> reversedCodeMap = codeMap.entrySet().stream().collect(
            Collectors.toMap(entry -> entry.getValue(), entry -> entry.getKey()));



        StringBuffer currentTokenBuffer = new StringBuffer();
        StringBuffer messageBuilder = new StringBuffer();

        for (char bit : encodedMessage.toCharArray()) {
            currentTokenBuffer.append(bit);
            if (reversedCodeMap.containsKey(currentTokenBuffer.toString())) {
                messageBuilder.append(reversedCodeMap.get(currentTokenBuffer.toString()));
                currentTokenBuffer.setLength(0); // Empty the string buffer
            }
        }

        return messageBuilder.toString();

    }

    private String decodeWithTreeHelper(String encodedMessage) {

        // Start decoding at the root of the huffman tree
        Node current = this.tree.root;

        StringBuffer messageBuilder = new StringBuffer();

        for (char bit : encodedMessage.toCharArray()) {

            // Move left or right down the huffman Tree
            // depending on the current bit
            current = bit == '0' ? current.leftChild : current.rightChild;

            // if we reach a leaf node, 
            // add it to the decoded message
            // and then go back to the root of the tree to decode next character
            if (current instanceof CharacterNode) {
                messageBuilder.append(((CharacterNode) current).character);
                current = this.tree.root;
            }
        }

        return messageBuilder.toString();
    }

    public String decodeWithTree(HuffmanTree tree, String encodedMessage) {

        return null;

    }
}
