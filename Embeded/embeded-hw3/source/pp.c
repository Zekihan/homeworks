#include <stdlib.h>
#include <time.h>
#include <stdio.h>

// Direct polynomial evaluation
double poly(double a[], double x, int degree)
{
	long int i;
	double result = a[0];
	double xpwr = x;
	for (i = 1; i <= degree; i++)
	{
		result += a[i] * xpwr;
		xpwr = x * xpwr;
	}
	return result;
}

// Direct polynomial evaluation with optimizations
double poly_optimized(double a[], double x, int degree)
{
	int i;
	int limit = degree - 1;
	double result1 = a[0];
	double result2 = 0;
	double xs = x * x;
	double xpwr1 = x;
	double xpwr2 = xs;
	for (i = 1; i <= limit; i += 2)
	{
		result1 += a[i] * xpwr1;
		xpwr1 *= xs;

		result2 += a[i + 1] * xpwr2;
		xpwr2 *= xs;
	}

	for (; i <= degree; i++)
	{
		result1 += a[i] * xpwr1;
		xpwr1 *= xs;
	}
	return result1 + result2;
}

// Horner's polynomial evaluation
double horner_poly(double a[], double x, int degree)
{
	long int i;
	double result = a[degree];
	for (i = degree - 1; i >= 0; i--)
		result = a[i] + (x * result);
	return result;
}

// Horner's polynomial evaluation with optimizations
double horner_poly_optimized(double a[], double x, int degree)
{
	long int i;
	double result = a[degree];
	for (i = degree - 1; i >= 4; i -= 4)
	{
		result = a[i - 3] + (x * (a[i - 2] + (x * (a[i - 1] + (x * (a[i] + (x * result)))))));
	}
	for (; i >= 0; i--)
	{
		result = a[i] + (x * result);
	}
	return result;
}

// Mixed polynomial evaluation
double mixed(double a[], double x, int degree)
{
	long int i;
	double result = a[0];
	double xpwr = x;
	for (i = 1; i <= degree - 2; i += 2)
	{
		result += xpwr * (a[i] + (a[i + 1] * x));
		xpwr *= x * x;
	}
	for (; i <= degree; i++)
	{
		result += xpwr * (a[i]);
		xpwr *= x;
	}
	return result;
}

// Mixed polynomial evaluation with optimizations
double mixed_optimized(double a[], double x, int degree)
{
	int i;
	int limit = degree - 1;
	double result = a[0];
	double xpwr = x;
	double xs = x * x;
	for (i = 1; i <= degree - 8; i += 8)
	{
		result += xpwr * (((a[i] + (a[i + 1] * x))) +
						  xs * (((a[i + 2] + (a[i + 3] * x))) +
								xs * (((a[i + 4] + (a[i + 5] * x))) +
									  (xs * (a[i + 6] + (a[i + 7] * x))))));
		xpwr *= xs * xs * xs * xs;
	}
	for (; i <= degree; i++)
	{
		result += xpwr * (a[i]);
		xpwr *= x;
	}
	return result;
}

void main()
{
	int size = 1000;
	double a[1000] = {0.0};
	srand((unsigned int)time(NULL));

	for (int i = 0; i < 1000; i++)
	{
		a[i] = ((double)rand() / (double)(RAND_MAX));
	}

	// calls to the functions
	clock_t time;

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		mixed(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("mixed: %f\n", (double)time / CLOCKS_PER_SEC);

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		mixed_optimized(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("mixed optimized: %f\n", (double)time / CLOCKS_PER_SEC);

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		poly(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("poly: %f\n", (double)time / CLOCKS_PER_SEC);

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		poly_optimized(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("poly optimized: %f\n", (double)time / CLOCKS_PER_SEC);

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		horner_poly(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("horner: %f\n", (double)time / CLOCKS_PER_SEC);

	time = clock();					  //starts time
	for (int i = 0; i < 1000000; i++) //call multiple times to get significant values
	{
		horner_poly_optimized(a, 2, 999);
	}
	time = clock() - time; //ends time
	printf("horner optimized: %f\n", (double)time / CLOCKS_PER_SEC);
}
