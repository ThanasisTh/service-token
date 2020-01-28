package core;

import dtupay.DtuPayCustomerRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTokenPool
{
    private DtuPayCustomerRepresentation customer =
            new DtuPayCustomerRepresentation("Test", "Customer", "1234", "4321");

    @BeforeEach
    public void initiate()
    {
        TokenPool.getTokens().clear();
        TokenPool.assignToken(customer.getCprNumber(), 0);
    }

    @DisplayName("Test TokenPool.assignToken()")
    @Test
    public void testAssignToken()
    {
        boolean customerExists = TokenPool.getTokens().containsKey(customer.getCprNumber());
        if (customerExists)
        {
            int tokensRequested = 1;
            List<String> tokens = TokenPool.assignToken(customer.getCprNumber(), tokensRequested);
            if (tokens != null)
            {
                int size = tokens.size();
                assertEquals(size, tokensRequested);
            }
        }
    }

    @DisplayName("Test TokenPool.assignToken()")
    @Test
    public void testCannotAssignMoreTokens()
    {
        boolean customerExists = TokenPool.getTokens().containsKey(customer.getCprNumber());
        if (customerExists)
        {
            int tokensRequested = 2;
            List<String> tokens = TokenPool.assignToken(customer.getCprNumber(), tokensRequested);
            if (tokens != null)
            {
                int size = tokens.size();
                assertEquals(size, tokensRequested);
            }
            tokensRequested += 1;
            tokens = TokenPool.assignToken(customer.getCprNumber(), tokensRequested);
            if (tokens != null)
            {
                int size = tokens.size();
                assertTrue(size < tokensRequested);
            }
        }
    }

    @DisplayName("Test TokenPool.isValid()")
    @Test
    public void testTokenIsValid()
    {
        boolean customerExists = TokenPool.getTokens().containsKey(customer.getCprNumber());
        if (customerExists)
        {
            int tokensRequested = 1;
            List<String> tokens = TokenPool.assignToken(customer.getCprNumber(), tokensRequested);
            if (tokens != null)
            {
                String plainTextToken = tokens.get(0);
                String hashedToken = hashToken(plainTextToken);
                boolean isTokenValid = TokenPool.isValid(customer.getCprNumber(), hashedToken);
                assertTrue(isTokenValid);
            }
        }
    }

    @DisplayName("Test TokenPool.consumeToken()")
    @Test
    public void testConsumeToken()
    {
        boolean customerExists = TokenPool.getTokens().containsKey(customer.getCprNumber());
        if (customerExists)
        {
            int tokensRequested = 1;
            List<String> tokens = TokenPool.assignToken(customer.getCprNumber(), tokensRequested);
            if (tokens != null)
            {
                String token = tokens.get(0);
                boolean isTokenConsumed = TokenPool.consumeToken(customer.getCprNumber(), token);
                assertTrue(isTokenConsumed);
            }
        }
    }

    private String hashToken(String token)
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
