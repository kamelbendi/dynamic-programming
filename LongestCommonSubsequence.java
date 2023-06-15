public class LongestCommonSubsequence {
    
    public static int findLCSLength(String A, String B) {
        int m = A.length();
        int n = B.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    public static String findLCS(String A, String B) {
        int m = A.length();
        int n = B.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        int lcsLength = dp[m][n];
        
        StringBuilder lcsBuilder = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                lcsBuilder.insert(0, A.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcsBuilder.toString();
    }
    
    public static void main(String[] args) {
        String[] stringPairs = {
            "ZHTGCSZWS", "HWGRCSANA",
            "TTNCDQA", "JTXCDAE",
            "PLMXWZE", "EPWMZRE"
        };
        
        System.out.println("LCS Length\tLCS\t\tDP Iterations\tREC Iterations");
        System.out.println("----------------------------------------------");
        
        for (int i = 0; i < stringPairs.length; i += 2) {
            String A = stringPairs[i];
            String B = stringPairs[i + 1];
            
            long startTime = System.currentTimeMillis();
            int lcsLength = findLCSLength(A, B);
            long dpEndTime = System.currentTimeMillis();
            String lcs = findLCS(A, B);
            long recEndTime = System.currentTimeMillis();
            
            long dpIterations = (A.length() + 1) * (B.length() + 1);
            long recIterations = countRecursiveIterations(A, B);
            
            System.out.printf("%d\t\t%s\t\t%d\t\t%d%n", lcsLength, lcs, dpIterations, recIterations);
            System.out.println("----------------------------------------------");
            System.out.println("DP Time: " + (dpEndTime - startTime) + " nanoseconds");
            System.out.println("REC Time: " + (recEndTime - dpEndTime) + " nanoseconds");
            System.out.println("----------------------------------------------");
        }
    }
    
    public static int countRecursiveIterations(String A, String B) {
        return countRecursiveIterationsHelper(A, B, A.length(), B.length());
    }
    
    private static int countRecursiveIterationsHelper(String A, String B, int i, int j) {
        if (i == 0 || j == 0) {
            return 1;
        }
        
        if (A.charAt(i - 1) == B.charAt(j - 1)) {
            return 1 + countRecursiveIterationsHelper(A, B, i - 1, j - 1);
        } else {
            int del = countRecursiveIterationsHelper(A, B, i - 1, j);
            int ins = countRecursiveIterationsHelper(A, B, i, j - 1);
            int sub = countRecursiveIterationsHelper(A, B, i - 1, j - 1);
            return 1 + Math.min(Math.min(del, ins), sub);
        }
    }
}
