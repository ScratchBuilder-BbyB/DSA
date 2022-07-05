package graph.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Tries {

  public static void main(String[] args) {
    Node trie = new Node();
    addPrefix(trie, "abc");
    addPrefix(trie, "abcd");
    addPrefix(trie, "abce");
    addPrefix(trie, "abcf");
    System.out.println(exists(trie, "abcf"));
  }


  private static boolean exists(Node root, String prefix){
    Node currentNode = root;
    char[] arr = prefix.toCharArray();
    for(int i = 0; i < arr.length; i++){
      char c = arr[i];
      if(currentNode.map.containsKey(c)){
        currentNode = currentNode.map.get(c);
        if(i == arr.length - 1){
          return currentNode.isLastNode && currentNode.metaData.equals(prefix);
        }
      }else{
        break;
      }
    }

    return false;
  }

  private List<String> getWordsWithPrefix(Node root, String prefix){
      List<String> words = new ArrayList<>();
      getWordsWithPrefixHelper(root, words, "");
      return words;
  }

  private void getWordsWithPrefixHelper(Node root, List<String> words, String currentWord) {
    if(root.isLastNode){
      words.add(currentWord);
      return;
    }

  }

  private static void addPrefix(Node root, String prefix){
    Node currentNode = root;
    char[] arr = prefix.toCharArray();
    for(int i = 0; i < arr.length; i++){
      char c = arr[i];
      if(currentNode.map.containsKey(c)){
        // navigate
        currentNode = currentNode.map.get(c);
      }else{
        // add here
        Node node = new Node();
        if(i == arr.length - 1){
          node.isLastNode = true;
          node.metaData = prefix;
        }
        currentNode.map.put(c, node);
        currentNode = node;
      }
    }
  }

  private static class Node{
    Map<Character, Node> map;
    boolean isLastNode;
    String metaData;

    public Node() {
      map = new HashMap<>();
    }
  }
}
