package org.kinotic.structures.internal.api.services.util;

public class StructureHelper {

    /**
     * Function will validate the index name, ensures we can use it for creating an
     * elasticsearch index.
     *
     * Function will throw an ${@link IllegalStateException} in the event the passed
     * in value is invalid.
     *
     * @param indexName
     * @throws IllegalStateException
     */
    public static void indexNameValidation(String indexName) throws IllegalStateException {
        //    255 characters in length
        //    must be lowercase
        //    must not start with '_', '-', or '+'
        //    must not be '.' or '..' -
        //
        //    must not contain the following characters
        //    '\\', '/', '*', '?', '"', '<', '>', '|', ' ', ',', '#', ':', ';'
        if(indexName.startsWith("_")
                || indexName.startsWith("-")
                || indexName.startsWith("+")
                || indexName.startsWith(".")
                || indexName.startsWith("..")
                || indexName.contains("\\")
                || indexName.contains("/")
                || indexName.contains("*")
                || indexName.contains("?")
                || indexName.contains("\"")
                || indexName.contains("<")
                || indexName.contains(">")
                || indexName.contains("|")
                || indexName.contains(" ")
                || indexName.contains(",")
                || indexName.contains("#")
                || indexName.contains(":")
                || indexName.contains(";")
                || indexName.contains("..")
                || indexName.getBytes().length > 255){
            throw new IllegalStateException("namespace and id combined are not in correct format, \ncannot start with _ - +\ncannot contain . .. \\ / * ? \" < > | , # : ; \ncannot contain a space or be longer than 255 bytes");
        }
    }
}
