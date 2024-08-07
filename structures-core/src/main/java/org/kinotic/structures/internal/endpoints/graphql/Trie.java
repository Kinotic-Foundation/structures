package org.kinotic.structures.internal.endpoints.graphql;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
public class Trie<T> {
    TrieNode<T> root;

    public Trie() {
        root = new TrieNode<>();
    }

    /**
     * Inserts a word and its additional data into the trie
     * @param key the word to insert, must only be alpha characters
     * @param value the additional data to store for the word
     */
    public void insert(String key, T value) {
        key = key.toLowerCase();
        TrieNode<T> p = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (p.children[index] == null) {
                p.children[index] = new TrieNode<>();
            }
            p = p.children[index];
        }
        if(p.value != null){
            throw new IllegalArgumentException("Duplicate key: " + key);
        }
        p.isEndOfWord = true;
        p.value = value; // Store the additional data
    }

    /**
     * Finds the value stored for a given prefix
     * @param fullString the full string to find the prefix data for
     * @return the data stored for the prefix or null if not found
     */
    public T findValue(String fullString) {
        fullString = fullString.toLowerCase();
        TrieNode<T> p = root;
        for (int i = 0; i < fullString.length(); i++) {
            int index = fullString.charAt(i) - 'a';

            // Character is not in the trie
            if(index < 0 || index > 25){
                return null;
            }

            if (p.children[index] == null) {
                return null; // No match found
            }

            p = p.children[index];
            if (p.isEndOfWord) {
                return p.value; // Return the additional data
            }
        }
        return null; // No match found
    }

    static class TrieNode<T> {
        @SuppressWarnings("unchecked")
        TrieNode<T>[] children = new TrieNode[26];
        boolean isEndOfWord;
        T value; // Stores additional data

        TrieNode() {
            isEndOfWord = false;
            value = null;
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }
    }
}


