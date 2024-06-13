import java.math.BigInteger;

public class ectf20p10 {
    public static void main(String[] args) {
        BigInteger n = new BigInteger("14393188157100504374851319373504728765762087532393762203410288659620743314524046223483350199");
        BigInteger e = new BigInteger("65537");
        BigInteger c = new BigInteger("10247955272908064997771727568918647737311526262165262458875076213296879253353684001266750329");

        BigInteger p = new BigInteger("97");
        BigInteger q = new BigInteger("148383383062891797678879581170151842946000902395811981478456584119801477469320064159622167");

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger d = e.modInverse(phi);
        BigInteger m = c.modPow(d, n);

        String plaintext = new String(m.toByteArray());
        System.out.println(plaintext);
    }
}