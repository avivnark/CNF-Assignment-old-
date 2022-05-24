import java.util.Arrays;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;

public class Assignment2 
{

	
	/*-----------------------
	 *| Part A - tasks 1-11 |
	 * ----------------------*/
	
	// task 1
	public static boolean isSquareMatrix(boolean[][] matrix) 
	{
		boolean square=true;
		if(matrix==null||matrix.length==0)//checks whether matrix is proper
			square=false;
		for(int i=0;square && i<matrix.length; i++)
		{
			if(matrix[i]==null || matrix[i].length!=matrix.length)
				square=false;//checks whether the matrix is square
		}
		return square;
	}
	
	// task 2
	public static boolean isSymmetricMatrix(boolean[][] matrix) 
	{
		boolean symmetric=true;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix[i].length; j++)
			{
				if(matrix[j][i]!=matrix[i][j])//checks whether the matrix is symmetric or not.
				{
					symmetric=false;
				}
			}
		}
		return symmetric;

	}

	// task 3
	public static boolean isAntiReflexiveMatrix(boolean[][] matrix) 
	{
		boolean reflexive=true;
		for(int i=0; i<matrix.length; i++)
		{
				if(matrix[i][i]==true)//checks whether matrix is reflexive or not
					reflexive=false;
		}
		return reflexive;
	}
	
	// task 4
	public static boolean isLegalInstance(boolean[][] matrix) 
	{
		boolean legal=false;
		if(isSquareMatrix(matrix) && isSymmetricMatrix(matrix) & isAntiReflexiveMatrix(matrix))//checks if matrix is square, symmetric and reflexive.
			legal=true;
		return legal;
		
	}
	
	// task 5
	public static boolean isPermutation(int[] array) 
	{
		boolean permutation=true;
		int[] checkmatrix= new int [array.length];	
		for(int i=0; permutation & i<array.length; i++)
		{
			if(array[i]>array.length-1)
				permutation=false;
			else	
				checkmatrix[array[i]]++;
		}
		for(int j=0; permutation & j<array.length; j++)
		{
			if(checkmatrix[j]!=1)
				permutation=false;//if a certain value is in array twice, array isn't a permutation
		}
		return permutation;
		
	}
	
	// task 6
	public static boolean hasLegalSteps(boolean[][] flights, int[] tour) 
	{
		boolean steps=true;
		for(int i=0; steps & i<tour.length-1; i++)
		{
			if(flights[tour[i]][tour[i+1]]=false)//checks whether the steps are legal or not
				steps=false;
		}
		if(steps & !(flights[tour[tour.length-1]][tour[0]]==true))
			steps=false;
		return steps;
	}
	
	// task 7
	public static boolean isSolution(boolean[][] flights, int[] tour) 
	{
		if(tour.length!=flights.length)//checks if the length of tour is not equal to the length of flights
			throw new UnsupportedOperationException("the length of tour is not equal to the length of flights"); 
		boolean solution=false;
		if(tour[0]==0 & hasLegalSteps(flights,tour) & isPermutation(tour))//checks whether the values match hagdara2 requirements.
		{
			solution=true;
		}
		return solution;
	}
	
	// task 8
	public static boolean evaluate(int[][] cnf, boolean[] assign) 
	{
		boolean and=true;
		for(int i=0; i<cnf.length & and; i++)//when and=false, we can stop and return a false.
		{
			boolean or=false;
			for(int j=0; j<cnf[i].length & !or; j++)//when or=true, we can stop and move on to the next value of i.
			{
				int temp=cnf[i][j];
				if(temp>0)
					or=assign[temp];
				if(temp<0)
					or=!assign[-temp];//negative index of array is illegal;
			}
			if(or==false)
				and=false;
		}
		return and;
	}
	
	// task 9
	public static int[][] atLeastOne(int[] lits) 
	{
		int cnf[][] = new int[1][lits.length];
		cnf[0] = lits;//creating a cnf that is always true
		return cnf;
	}

	// task 10
	public static int[][] atMostOne(int[] lits) 
	{		
		int cnf[][] = new int[((lits.length)*(lits.length-1))/2][2];//the length of cnf is the number of options for couples of values in lits.
		int counter=0;//in order to have an index for cnf.
		for(int i=0;i<lits.length;i++)
		{
			for(int j=i+1;j<lits.length; j++, counter++)
			{
				cnf[counter][0]=-lits[i];
				cnf[counter][1]=-lits[j];
			}

		}
		return cnf;
	}
	
	// task 11
	public static int[][] exactlyOne(int[] lits) 
	{
		int[][] atleast= atLeastOne(lits);
		int[][] atmost= atMostOne(lits);
		int[][] cnf= new int[atleast.length+atmost.length][];
		for(int i=0;i<atmost.length;i++)
		{
			cnf[i]=atmost[i];
		}
		cnf[cnf.length-1]=atleast[0];
		return cnf;//combines both atleastone and atmostone in order to get exactlyone
		
	}
	
	/*------------------------
	 *| Part B - tasks 12-20 |
	 * -----------------------*/
	
	// task 12a
	public static int map(int i, int j, int n) 
	{
		int k=i*n+j+1;//Calculating k
		return k;
	}
	
	// task 12b
	public static int[] reverseMap(int k, int n) 
	{
		int i=(k-1)/n;;//Calculating i
		int j=(k-1)%n;;//Calculating j
		int[] pair = {i,j};
		return pair;
	}
	
	// task 13
	public static int[][] oneCityInEachStep(int n) 
	{
		int[][] cnf=new int[((n)*(n-1)/2+1)*n][];
		int[][] cnfi;
		int index=0;
		for(int i=0; i<n; i++)
		{
			int[] lits=new int[n];
			for(int j=0; j<n; j++)
			{
				lits[j]=map(i,j,n);//Creates a literals array with k values in each step
			}
			cnfi=exactlyOne(lits);//Creates a temporary cnf for the current i that makes sure we visit one city in each step
			for(int m=0; m<cnfi.length; m++)
			{
				cnf[index]=cnfi[m];//Adds the temporary cnf to the bigger one
				index++;
			}
		}
		return cnf;
	}

	// task 14
	public static int[][] eachCityIsVisitedOnce(int n) 
	{
		//Same program as in task 13 except here we make sure each city is visited once
		int[][] cnf=new int[((n)*(n-1)/2+1)*n][];
		int[][] cnfj;
		int index=0;
		for(int j=0; j<n; j++)
		{
			int[] lits=new int[n];
			for(int i=0; i<n; i++)
			{
				lits[i]=map(i,j,n);
			}
			cnfj=exactlyOne(lits);
			for(int m=0; m<cnfj.length; m++)
			{
				cnf[index]=cnfj[m];
				index++;
			}
		}
		return cnf;
	}
	
	// task 15
	public static int[][] fixSourceCity(int n) 
	{
		int[][]cnf= {{1}};//Makes sure the trip starts in city 0
		return cnf;
	}
	
	// task 16
	public static int[][] noIllegalSteps(boolean[][] flights) 
	{
		int n=flights.length;
		int index=0;
		int counter=0;
		for(int j=0; j<n; j++)
		{
			for(int k=0; k<n; k++)
			{
				if(flights[j][k]==false & j!=k)
				{
					counter++;//Allows us to determine the length of our cnf
				}
			}
		}
		counter=counter*n;		
		int[][] cnf=new int[counter][];
		for(int i=0;i<n;i++)
		{
			for(int j=0; j<n; j++)
			{
				for(int k=0; k<n; k++)
				{
					int current, nextstep;
					if(flights[j][k]==false & j!=k)
					{
						current=map(i,j,n);//Finds the value for the current step
						nextstep=map((i+1)%n,k,n);//Finds the value for the next step
						int[] lits={current,nextstep};
						int[][] currentcnf=atMostOne(lits);//makes sure we either do the current step or the next one since we can't fly between their cities
						for(int m=0; m<currentcnf.length;m++)
						{
							cnf[index]=currentcnf[m];//Adds the temporary cnf to the bigger one
							index++;

						}
					}

				}
			}
			
		}
		return cnf;

	}

	// task 17
	public static int[][] encode(boolean[][] flights) 
	{
		//Here we combine all the cnfs from the programs above in order to have all the terms applied
		int n=flights.length;
		int[][] oneInEachStep=oneCityInEachStep(n);
		int[][] eachIsVisitedOnce=eachCityIsVisitedOnce(n);
		int[][] fixSource=fixSourceCity(n);
		int[][] noIllegalSteps=noIllegalSteps(flights);
		int[][] allTermsCNF=new int[oneInEachStep.length+eachIsVisitedOnce.length+fixSource.length+noIllegalSteps.length][];
		int index=0;
		for(int i=0; i<oneInEachStep.length; i++, index++)
		{
			allTermsCNF[index]=oneInEachStep[i];
		}
		for(int i=0; i<eachIsVisitedOnce.length; i++, index++)
		{
			allTermsCNF[index]=eachIsVisitedOnce[i];
		}
		for(int i=0; i<fixSource.length; i++, index++)
		{
			allTermsCNF[index]=fixSource[i];
		}
		for(int i=0; i<noIllegalSteps.length; i++, index++)
		{
			allTermsCNF[index]=noIllegalSteps[i];
		}
		return allTermsCNF;
		
	}

	// task 18
	public static int[] decode(boolean[] assignment, int n) 
	{
		if(assignment==null)//makes sure assignment is proper
		{
			throw new IllegalArgumentException("assignment is null");		
		}
		if(assignment.length!=n*n+1)//makes sure assignment is proper
		{
			throw new IllegalArgumentException("assignment's length isn't n*n+1");		
		}
		int[] tour=new int [n];
		for(int i=1; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				if(assignment[(map(i,j,n))])
				{
					tour[i]=j;//Adds the city that is visited in each step according to assignment
				}
			}
		}
		return tour;
	}
	
	// task19
	public static int[] solve(boolean[][] flights) 
	{
		int[] output=null;
		int n=flights.length;
		if(!isLegalInstance(flights))
		{
			throw new IllegalArgumentException("Illegal argument");//Checks whether flights is legal 		
		}
		int[][] cnf=encode(flights);
		SATSolver.init(n*n);
		SATSolver.addClauses(cnf);//Adds the cnf to SATSolver
		boolean[] assignment=SATSolver.getSolution();
		if(assignment==null)
		{
			throw new IllegalArgumentException("TIMEOUT");//Checks whether a solution was found or not	

		}
		else if(assignment.length==n*n+1)
		{
			int[] tour=decode(assignment,n);
			if(isSolution(flights,tour))//Checks whether the solution is legal or not	
			{
				output=tour;
			}
		}
		else
		{
			throw new IllegalArgumentException("not a proper solution");		

		}
     	return output;

	}
	
	// task20
	public static boolean solve2(boolean[][] flights)//checks whether another solution is possible or not
	{
		int[] tour=solve(flights);//Gets an array of a tour for flights
		int n=flights.length;
		boolean anothersolution=false;
		int[][] cnf=encode(flights);
		SATSolver.init(n*n);
		SATSolver.addClauses(cnf);//Adds the cnf to SATSolver
		int[] samelits=new int[n];
		for(int i=0; i<n; i++)//Makes sure we won't get the same solution from SATSolver by making the current solution illegal
		{		
			samelits[i]=-map(i,tour[i],n);
		}
		SATSolver.addClause(samelits);//Adding the clause with the current result
		int[] reverselits=new int[n];
		for(int i=1; i<n; i++)
		{
			int[] reversemap=reverseMap(-reverselits[i],n);
			reverselits[i]=reversemap[1];
			if(i!=1)
			{
				int current=reverselits[i];
				reverselits[i]=reverselits[reverselits.length-i];
				reverselits[reverselits.length-i]=current;
			}
		}
		for(int i=0; i<reverselits.length; i++)
		{
			reverselits[i]=-map(i,reverselits[i],n);
		}
		SATSolver.addClause(reverselits);//Adding the clause with the new terms
		boolean[] assignment=SATSolver.getSolution();
		if(assignment==null)	
		{
			throw new IllegalArgumentException("TIMEOUT");//Checks whether a solution was found or not	
		}
		if(assignment.length!=n*n+1||isSolution(flights,decode(assignment,n)))
		{
			anothersolution=false;//Checks whether the solution is legal or not	
	    }
		return anothersolution;
	}
	
}
