/*authors: Jacinta Esi Amoawah badu
* & Theresah Owusu
*/

import java.util.Scanner;
public class HuffmanDemo {

    public static void main(String[] args) {

        HuffmanEncoder encoder = new HuffmanEncoder();
    
        System.out.println(" please enter the text to encode and decode");
        Scanner input=  new Scanner(System.in);
        String message= input.nextLine();

        System.out.println("Message: " + message);

        String encodedMessage = encoder.encode(message);
        System.out.println("Encoded message (using huffman tree): " + encodedMessage);

        String decodedMessage = encoder.decode(encodedMessage);
        System.out.println("Decoded message (using huffman tree): " + decodedMessage);

        String decodedMessageWithMapOnly = encoder.decodeWithCodeMap(encodedMessage, encoder.getTree().getCode());
        System.out.println("Decoded message (no huffman tree): " + decodedMessageWithMapOnly);

        input.close();
    }
 
}
