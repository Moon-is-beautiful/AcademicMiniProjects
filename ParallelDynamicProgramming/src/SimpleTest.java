package main;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {
	@Test
    public void testBST_p1() {
        double frequency[] = {0.1,0.7,0.2};
        double expected = 1.3;
        optimal_BST_Parallel optimalBST = new optimal_BST_Parallel(frequency);
        optimalBST.solve();
        double result = optimalBST.getSolution();
        System.out.println("optimal cost for BST "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test
    public void testBST_s1() {
        double frequency[] = {0.1,0.7,0.2};
        double expected = 1.3;
        optimal_BST_sequantial optimalBST = new optimal_BST_sequantial(frequency);
        double result = optimalBST.solve();
        System.out.println("optimal cost for BST "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test
    public void testBST_p2() {
        double frequency[] = {0.12, 0.05, 0.18, 0.08, 0.1, 0.07, 0.15, 0.09, 0.11, 0.05};
        double expected = 2.61;
        optimal_BST_Parallel optimalBST = new optimal_BST_Parallel(frequency);
        optimalBST.solve();
        double result = optimalBST.getSolution();
        System.out.println("optimal cost for BST "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test
    public void testBST_s2() {
        double frequency[] = {0.12, 0.05, 0.18, 0.08, 0.1, 0.07, 0.15, 0.09, 0.11, 0.05};
        double expected = 2.61;
        optimal_BST_sequantial optimalBST = new optimal_BST_sequantial(frequency);
        double result = optimalBST.solve();
        System.out.println("optimal cost for BST "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test 
	public void testMatrix_p1() {
        double dimensions[] = {30.0,10.0,30.0,2.0};
        double expected = 1200.0;
        matrix_multiplication_parallel mp = new matrix_multiplication_parallel(dimensions);
        mp.solve();
        double result = mp.getSolution();
        System.out.println("optimal cost for Matrix Multiplication "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test
    public void testMatrix_s1() {
        double dimensions[] = {30.0,10.0,30.0,2.0};
        double expected = 1200.0;
        matrix_multiplication_sequential ms = new matrix_multiplication_sequential(dimensions);
        double result = ms.solve();
        System.out.println("optimal cost for Matrix Multiplication "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }

	@Test 
	public void testMatrix_p2() {
        double dimensions[] = {30.0,10.0,30.0,2.0,7.0,9.0,12.0,34.0};
        double expected = 4398.0;
        matrix_multiplication_parallel mp = new matrix_multiplication_parallel(dimensions);
        mp.solve();
        double result = mp.getSolution();
        System.out.println("optimal cost for Matrix Multiplication "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
	
	@Test
    public void testMatrix_s2() {
        double dimensions[] = {30.0,10.0,30.0,2.0,7.0,9.0,12.0,34.0};
        double expected = 4398.0;
        matrix_multiplication_sequential ms = new matrix_multiplication_sequential(dimensions);
        double result = ms.solve();
        System.out.println("optimal cost for Matrix Multiplication "+result);
        assertEquals(expected, result, 0.0001); // Use delta for comparing floating-point numbers
    }
    @Test
    public void testKnapsack_p1() {
        int[] weights = {3, 1, 3, 4, 2};
        int[] values = {2, 2, 4, 5, 3};
        int maxWeight = 7;
        double expected = 10.0;
        knapsack_parallel knapsack = new knapsack_parallel(weights, values, maxWeight);
        knapsack.solve();
        double result = knapsack.getSolution();
        System.out.println("Maximum value achievable: " + result);
        assertEquals(expected, result, 0.0001);

    }

    @Test
    public void testKnapsack_p2() {
        int[] weights = {1, 2, 5, 6};
        int[] values = {1, 6, 18, 22};
        int maxWeight = 10;
        double expected = 29.0;
        knapsack_parallel knapsack = new knapsack_parallel(weights, values, maxWeight);
        knapsack.solve();
        double result = knapsack.getSolution();
        System.out.println("Maximum value achievable: " + result);
        assertEquals(expected, result, 0.0001);

    }

    @Test
    public void testKnapsack_s1() {
        int[] weights = {3, 1, 3, 4, 2};
        int[] values = {2, 2, 4, 5, 3};
        int maxWeight = 7;
        int expected = 10;
        int result = knapsack_sequential.knapsack_sequential(weights, values, maxWeight);
        System.out.println("Maximum value achievable: " + result);
        assertEquals(expected, result);

    }

    @Test
    public void testKnapsack_s2() {
        int[] weights = {1, 2, 5, 6};
        int[] values = {1, 6, 18, 22};
        int maxWeight = 10;
        int expected = 29;
        int result = knapsack_sequential.knapsack_sequential(weights, values, maxWeight);
        System.out.println("Maximum value achievable: " + result);
        assertEquals(expected, result);

    }
    
    @Test
    public void testIncseq_p1() {
        int[] sequence = {35, 38, 27, 45, 32};
        int expected = 3;
        sequence_parallel sq = new sequence_parallel(sequence);
        sq.solve();
        int result = sq.getSolution();
        System.out.println("Maximum length of  increasing sequence: " + result);
        assertEquals(expected, result);

    }
    
    @Test
    public void testIncseq_p2() {
        int[] sequence = {1, 3, 4, 5,7};
        int expected = 5;
        sequence_parallel sq = new sequence_parallel(sequence);
        sq.solve();
        int result = sq.getSolution();
        System.out.println("Maximum length of  increasing sequence: " + result);
        assertEquals(expected, result);

    }
	
    @Test
    public void testIncseq_s1() {
        int[] sequence = {35, 38, 27, 45, 32};
        int expected = 3;
        sequence_sequential sq = new sequence_sequential();
        int result = sq.sequence_sequential(sequence);
        System.out.println("Maximum length of  increasing sequence: " + result);
        assertEquals(expected, result);

    }
    
    @Test
    public void testIncseq_s2() {
        int[] sequence = {1, 3, 4, 5,7};
        int expected = 5;
        sequence_sequential sq = new sequence_sequential();
        int result = sq.sequence_sequential(sequence);
        System.out.println("Maximum length of  increasing sequence: " + result);
        assertEquals(expected, result);

    }
	
}
