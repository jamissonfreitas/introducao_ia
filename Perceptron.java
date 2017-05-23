package project;
import java.util.Random;
import java.util.Arrays;

public class Perceptron {
	private Random random;
	private double taxa_aprendizado = 0.2;
	private double limiar = 0;
	private int bias_index = -1;
	private double[] pesos;
	private int numEntradas = 0;
	
	public Perceptron(int numEntradas, int numSaidas, int semente){
		this.random = new Random(semente);
		this.numEntradas = numEntradas;
		this.bias_index = numEntradas;
		this.pesos = new double[numEntradas+1];
		this.init();
	}
	
	private void init()
	{
		for(int i = 0; i <= this.numEntradas; i++)
		{
			this.pesos[i] = (2*this.random.nextDouble())-1;
		}
	}
	
	public void treinar(double[][] entradas, double[] saidas)
	{
		for(int i=0; i<entradas.length; i++){
			
			double saidaObtida = this.calcular(entradas[i]);
			
			double error = saidas[i] - saidaObtida;
			
			if(error != 0)
				this.ajustarPeso(entradas[i], saidas[i], saidaObtida, error);
		}
			
	}
	
	private double ativacaoDegrau(double value)
	{
		if(value < this.limiar)
			return 0;
		else
			return 1;
	}
	
	public double calcular(double[] entradas)
	{		
		double entradaLiquida = 0;
		for(int i =0; i < this.numEntradas; i++)
		{
			entradaLiquida += entradas[i] * this.pesos[i];
		}
		
		entradaLiquida += (this.pesos[this.bias_index] * 1);
		
		return this.ativacaoDegrau(entradaLiquida);
	}

	public void ajustarPeso(double[] entradas, 
			double saidaEsperada, double saidaObtida, double error)
	{
		double[] novo_pesos = new double[this.numEntradas+1];
		
		for(int i=0; i<this.numEntradas; i++)
		{
			double variacao = error * this.taxa_aprendizado * entradas[i];
			
			novo_pesos[i] = this.pesos[i] + variacao;
		}
		novo_pesos[this.bias_index] = error * this.taxa_aprendizado;
		
		this.pesos = novo_pesos;
	}
	
	public static void main(String[] args) {
		
		Perceptron p = new Perceptron(2, 1, -56);
		System.out.println(Arrays.toString(p.pesos));
		
		System.out.println("Antes do treinamento:");
		System.out.println("0 and 0 :" + p.calcular(new double[]{0, 0}));
		System.out.println("0 and 1 :" + p.calcular(new double[]{0, 1}));
		System.out.println("1 and 0 :" + p.calcular(new double[]{1, 0}));
		System.out.println("1 and 1 :" + p.calcular(new double[]{1, 1}));
		
		// valores lógicos
		double[][] entradas = new double[][]{
			new double[]{0, 0},
			new double[]{0, 1},
			new double[]{1, 0},
			new double[]{1, 1}, 
		};
		
		double[] saidas = new double[]{
				0, 0, 0, 1
		};
		
		// treinamento
		for(int i = 0; i < 10; i++)
		{
			p.treinar(entradas, saidas);
			System.out.println(Arrays.toString(p.pesos));
		}
		
		System.out.println("\nDepois do treinamento:");
		System.out.println("0 and 0 :" + p.calcular(new double[]{0, 0}));
		System.out.println("0 and 1 :" + p.calcular(new double[]{0, 1}));
		System.out.println("1 and 0 :" + p.calcular(new double[]{1, 0}));
		System.out.println("1 and 1 :" + p.calcular(new double[]{1, 1}));

	}

}
