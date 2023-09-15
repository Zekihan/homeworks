#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>

void transpose(int *dst, int *src, int dim)
{
    int i, j;
    for (i = 0; i < dim; i++)
    {
        for (j = 0; j < dim; j++)
        {
            dst[j*dim + i] = src[i*dim + j];
        }
    }
}
void transposeMod(int *dst, int *src, int dimension)
{
    int blocksize = 4;
    for (int ii = 0; ii < dimension; ii += blocksize)
    {
        for (int jj = 0; jj < dimension; jj += blocksize)
        {
            for (int i = ii; i < ii + blocksize; ++i)
            {
                for (int j = jj; j < jj + blocksize; ++j)
                {
                    dst[i + j*dimension] = src[j + i*dimension];
                }
            }
        }
    }
}
void init(int *src, int dimension)
{
    int i, j;
    for (i = 0; i < dimension; i++)
    {
        for (j = 0; j < dimension; j++)
        {
            int xy = i*dimension + j;
            src[xy] = xy;
        }
    }
}
void print(int *src,int dim)
{
    int i, j;
    for (i = 0; i < dim; i++){
        for (j = 0; j < dim; j++){
            printf("%d\t",src[i*dim + j]);
        }
        printf("\n");
    }
}
void test(int dim)
{
    int *src = malloc(dim*dim*sizeof(int));
    int *dst = malloc(dim*dim*sizeof(int));
    init(src,dim);

    clock_t begin = clock();
    clock_t end = clock();
   
    begin = clock();
    srand(time(NULL));

    transpose(dst,src,dim);
    
    end = clock();
    printf("Elapsed time for %dx%d: %f\n", dim, dim, ((double)(end-begin))/CLOCKS_PER_SEC);
}
void testMod(int dim)
{
    int *src = malloc(dim*dim*sizeof(int));
    int *dst = malloc(dim*dim*sizeof(int));
    init(src,dim);

    clock_t begin = clock();
    clock_t end = clock();
   
    begin = clock();
    srand(time(NULL));

    transposeMod(dst,src,dim);
    
    end = clock();
    printf("Elapsed time for %dx%d: %f\n", dim, dim, ((double)(end-begin))/CLOCKS_PER_SEC);
}
int main(int argc, char* argv[])
{    
    if (argc != 3)
    {
        return -1;
    }

    int dim = atoi(argv[1]);
    int mod = atoi(argv[2]);
    
    if(mod > 0)
    {
        test(dim);
    }
    else
    {
        testMod(dim);
    }
    exit(0);
}

