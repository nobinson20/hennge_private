
import java.lang.reflect.UndeclaredThrowableException;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.stream.Collectors;


/**
 * This is an example implementation of the OATH TOTP algorithm. Visit
 * www.openauthentication.org for more information.
 *
 * @author Johan Rydell, PortWise, Inc.
 */
public class TOTP {
    private TOTP() {
    }

    /**
     * This method uses the JCE to provide the crypto algorithm. HMAC computes a
     * Hashed Message Authentication Code with the crypto hash algorithm as a
     * parameter.
     *
     * @param crypto:   the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @param keyBytes: the bytes to use for the HMAC key
     * @param text:     the message or text to be authenticated
     */
    private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * This method converts a HEX string to Byte[]
     *
     * @param hex: the HEX string
     *
     * @return: a byte array
     */
    private static byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++)
            ret[i] = bArray[i + 1];
        return ret;
    }

    private static final long[] DIGITS_POWER
            // 0 1 2 3 4 5 6 7 8 9
            = { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L , 10000000000L};

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key:          the shared secret, HEX encoded
     * @param time:         a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     *          digits
     */
    public static String generateTOTP(String key, String time, String returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA1");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key:          the shared secret, HEX encoded
     * @param time:         a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     *          digits
     */
    public static String generateTOTP256(String key, String time, String returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA256");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key:          the shared secret, HEX encoded
     * @param time:         a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     *          digits
     */
    public static String generateTOTP512(String key, String time, String returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA512");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key:          the shared secret, HEX encoded
     * @param time:         a value that reflects a time
     * @param returnDigits: number of digits to return
     * @param crypto:       the crypto function to use
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     *          digits
     */
    public static String generateTOTP(String key, String time, String returnDigits, String crypto) {
        // int codeDigits = Integer.decode(returnDigits).intValue();

        long codeDigits2 = Long.decode(returnDigits).longValue();
        int codeDigits = (int) codeDigits2;

        String result = null;
        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16)
            time = "0" + time;
        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha(crypto, k, msg);
        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
        long otp = binary % DIGITS_POWER[codeDigits];
        result = Long.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }




    public static void main(String[] args) {

        //String seed64 = "6e6f62696e736f6e323040676d61696c2e636f6d48454e4e47454348414c454e4745303033";

        String seed64 = "6e6f62696e736f6e3230406e617665722e636f6d4844454348414c4c454e4745303033"; //HDECHALLENGE003

        long unixTime = Instant.now().getEpochSecond();

        long T0 = 0L;
        long X = 30L;


        // Seed for HMAC-SHA1 - 20 bytes
        String seed = "3132333435363738393031323334353637383930";
        // Seed for HMAC-SHA256 - 32 bytes
        String seed32 = "3132333435363738393031323334353637383930" +
                "313233343536373839303132";
        // Seed for HMAC-SHA512 - 64 bytes
//        String seed64 = "3132333435363738393031323334353637383930" +
//                "3132333435363738393031323334353637383930" +
//                "3132333435363738393031323334353637383930" +
//                "31323334";
//        long T0 = 0;
//        long X = 30;
        long testTime[] = {59L, unixTime};
        String steps = "0";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println(
                    "+---------------+-----------------------+" +
                            "------------------+--------+--------+");
            System.out.println(
                    "| Time(sec) | Time (UTC format) " +
                            "| Value of T(Hex) | TOTP | Mode |");
            System.out.println(
                    "+---------------+-----------------------+" +
                            "------------------+--------+--------+");
            for (int i=0; i<testTime.length; i++) {
                long T = (testTime[i] - T0)/X;
                steps = Long.toHexString(T).toUpperCase();
                while (steps.length() < 16) steps = "0" + steps;
                String fmtTime = String.format("%1$-11s", testTime[i]);
                String utcTime = df.format(new Date(testTime[i]*1000));
                System.out.print("| " + fmtTime + " | " + utcTime +
                        " | " + steps + " |");
                System.out.println(generateTOTP(seed, steps, "8",
                        "HmacSHA1") + "| SHA1 |");
                System.out.print("| " + fmtTime + " | " + utcTime +
                        " | " + steps + " |");
                System.out.println(generateTOTP(seed32, steps, "8",
                        "HmacSHA256") + "| SHA256 |");
                System.out.print("| " + fmtTime + " | " + utcTime +
                        " | " + steps + " |");
                System.out.println(generateTOTP(seed64, steps, "10",
                        "HmacSHA512") + "| SHA512 |");
                System.out.println(
                        "+---------------+-----------------------+" +
                                "------------------+--------+--------+");

                String hexcode;
                //hexcode = "nobinson20@gmail.com:" + generateTOTP(seed64, unixTimeString, "10", "HmacSHA512");
                hexcode = "nobinson20@naver.com:" + generateTOTP(seed64, steps, "10", "HmacSHA512");


                System.out.println(hexcode);

                byte[] encoded = Base64.getEncoder().encode(hexcode.getBytes());

                System.out.println("encodedBytes \n" + new String(encoded));



                byte[] test = Base64.getEncoder().encode("nobinson20@gmail.com:0042456108".getBytes());

                System.out.println("nobinson20@gmail.com is " + new String(test));


                System.out.println(
                        "+---------------+-----------------------+" +
                                "------------------+--------+--------+");

                String authorizationValue = new String(encoded);

                //////////////////////http post request////////////
                ////////      Uncomment when sending a request /////////////////
//                var values = new HashMap<String, String>() {{
//                    put("contact_email", "nobinson20@gmail.com");
//                    put ("github_url", "https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b");
//                }};
//
//                var objectMapper = new ObjectMapper();
//                String requestBody = objectMapper
//                        .writeValueAsString(values);
//
//                HttpClient client = HttpClient.newHttpClient();
//                HttpRequest request = HttpRequest.newBuilder()
//                        .header("Authorization","Basic "+authorizationValue)
//                        .header("Content_Type", "application/json")
//                        .header("Accept", "*/*")
//                        .uri(URI.create("https://api.challenge.hennge.com/challenges/003"))
//                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                        .build();
//
//                HttpResponse<String> response = client.send(request,
//                        HttpResponse.BodyHandlers.ofString());
//
//                System.out.println(request.headers()+"\n"+requestBody);
//                System.out.println(" body " +response.body() + " headers " + response.toString());


            }
        }catch (final Exception e){
            System.out.println("Error : " + e);
        }




    }

}

