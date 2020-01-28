package core;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class TokenPool
{
    private static Map<String, List<String>> tokens = new HashMap<>();

    public static Map<String, List<String>> getTokens()
    {
        return tokens;
    }

    public static boolean isValid(String cprNumber, String hash)
    {
        if (tokens.containsKey(cprNumber))
        {
            return tokens.get(cprNumber).contains(hash);
        }
        return false;
    }

    public static boolean consumeToken(String cprNumber, String token)
    {
        String hash = hashToken(token);

        if (isValid(cprNumber, hash))
        {
            tokens.get(cprNumber).remove(hash);
            return true;
        }
        return false;
    }

    public static List<String> assignToken(String cprNumber, int tokensRequested)
    {
        if (!tokens.containsKey(cprNumber))
        {
            tokens.put(cprNumber, new ArrayList<>());
        }

        List<String> hashedTokens = tokens.get(cprNumber);
        List<String> newTokens = new ArrayList<>();

        if (tokensRequested <= 5 && tokensRequested >= 1 && hashedTokens.size() <= 1)
        {
            for (int i = 0; i < tokensRequested; i++)
            {
                String token = UUID.randomUUID().toString();
                newTokens.add(token);
                hashedTokens.add(hashToken(token));
            }
            return newTokens;
        }
        return null;
    }

    private static String hashToken(String token)
    {
        try
        {
            byte[] bytes = token.getBytes();
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytes);
            BigInteger b = new BigInteger(1, digest);
            return b.toString(16);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "00000000000000000000000000000000";
        }
    }
}
