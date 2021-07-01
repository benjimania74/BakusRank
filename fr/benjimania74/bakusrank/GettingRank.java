package fr.benjimania74.bakusrank;

public class GettingRank {
    public static String[] rankInfo(String username){
        GetFiles getFiles = new GetFiles();
        String data[] = {getFiles.getPrefix(username), getFiles.getSuffix(username), getFiles.getChatSeparator(), getFiles.getColor(username)};
        return data;
    }
}
