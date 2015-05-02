public class BitboardPerformance {

	public static void main(String[] args) {
		int n = 10000000;
		int[] x = new int[n];
		int[] y = new int[n];
		int[] w = new int[n];
		int[] h = new int[n];
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = (int) (Math.random() * 100);
			y[i] = (int) (Math.random() * 100);
			w[i] = (int) (Math.random() * 100);
			h[i] = (int) (Math.random() * 100);
			v[i] = (int) (Math.random() * 100);
		}

		int isect1 = 0;
		int j = n;
		long s1 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			j--;
			if (x[i] < x[j] + w[j] && x[i] + w[i] > x[j] && y[i] < y[j] + h[j] && y[i] + h[i] > y[j]) {
				isect1++;
			}
		}
		long e1 = System.nanoTime();

		int isect2 = 0;
		j = n;
		long s2 = System.nanoTime();
		for (int i = 0; i < n; i++) {
			j--;
			if ((v[i] & v[j]) != 0) {
				isect2++;
			}
		}
		long e2 = System.nanoTime();

		System.out.println((e1 - s1) + "," + isect1);
		System.out.println((e2 - s2) + "," + isect2);
	}

}
