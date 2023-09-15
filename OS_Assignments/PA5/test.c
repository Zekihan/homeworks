#include <stdio.h>
#include <stdlib.h>
#include <linux/kernel.h>
#include <sys/syscall.h>
#include <unistd.h>
#include <string.h>
#include <sys/stat.h>
#include <time.h>
#include <fcntl.h>
#include <sys/types.h>

/* test method of sys_elapsed */

int main(int argc, char *argv[])
{
	/* check if there is an argumant */
	if (2 != argc) {
        fprintf(stderr, "Usage: %s pid\n", argv[0]);
        exit(-1);
    }
	/* str to int */
	int pid = atoi(argv[1]);

	/* construct path for test stat */
	char path[512] = {0};
    snprintf(path, 12, "/proc/%d", pid); // puts string into buffer
	printf("%s\n",path);
    struct stat stats;

    /* check if stat fails */
    if (stat(path, &stats) == 0)
    {
  		printf("st_ctime = %ld\n", stats.st_mtime);
    }
    else
    {
        printf("Unable to get file properties.\n");
        printf("Please check whether '%s' file exists.\n", path);
    }

	/* call to sys_elapsed */
	int t = syscall(335,pid);
	printf("System call return : %d\n", t);

	return 0;
}
