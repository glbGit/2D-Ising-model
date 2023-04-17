import java.awt.Color;

import javax.swing.JPanel;

public class Ising implements Runnable {
		
		private int energy;
		private final int N;
		private final int L;
		private final double T;
		private int[] spin;
		
		public Ising(int energy, double T, int L) {
			this.energy = energy;
			this.L = L;
			this.T = T;
			this.N = L * L;
			spin = new int[N];
		}
		
		
		public void run() {
			evolution(GUIApp.p,this.energy);
		}
		

		public void evolution(JPanel[] pixel, int E) {
			int i, delta_E, b1, b2;
			
			for(int k = 0; k < N; k++) {
				if (pixel[k].getBackground().equals(Color.RED)) spin[k] = 1;
				else if (pixel[k].getBackground().equals(Color.BLUE)) spin[k] = -1;
			}
			
			for (int t = 0; GUIApp.running; t++) {
					i = t%(N);
					//borders
					if (i%L == L - 1) b1 = 1;
					else b1 = 0;
					if (i%L == 0) b2 = 1;
					else b2 = 0;
					
					delta_E = 2 * (spin[i] * (spin[(i - L + N)%N] + spin[(i + 1 - b1*L)%N] 
							+ spin[(i + L)%N] + spin[(i - 1 + b2*L)%N]));
					if (delta_E < 0 || Math.random() <= Math.exp(-((double) delta_E)/T)) {
						//flip(pixel[i]);
						spin[i] = -spin[i];
					}
					if (t%N == 0) {
						
						update(spin,pixel);
						
					}
					
			}
			
		}
		
		public void flip(JPanel pixel) {
			if (pixel.getBackground().equals(Color.BLUE)) pixel.setBackground(Color.RED);
			else if (pixel.getBackground().equals(Color.RED)) pixel.setBackground(Color.BLUE);
		}
		
		public void update(int[] spin, JPanel[] p) {
			for (int i = 0; i < N; i++) {
				if (spin[i] == 1) p[i].setBackground(Color.RED);
				else if (spin[i] == -1) p[i].setBackground(Color.BLUE);
			}
				
		}
		
		

	
}
