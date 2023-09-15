#include <linux/kernel.h>
#include <linux/time.h>
#include <linux/syscalls.h>

/* SYSCALL_DEFINE1 is used for taking input from userspsce */
/* It takes 3 argumants first is method name second input type third is input variable name */
SYSCALL_DEFINE1(elapsed, int, pid)
{	
	/* path to the process folder */
	char processId[512] = {'\0'};

	/* error code of vfs_stat method */
	int error;
	/* file space variable initialised with current space which is user space */
	mm_segment_t old_fs = get_fs();
	/* kstat struct type it will be filled with vfs_stat method */
	struct kstat stat;	
	/* timespec struct type it will be filled with getnstimeofday method */
	struct timespec current_time;

	/* consturct path to process folder */
	sprintf(processId, "/proc/%d", pid);
	/* test print if its consturcted correctly */
	printk("elapsed : %s\n", processId);

	/* changes current space with kernel space which is needed to access /proc folder */
	set_fs(KERNEL_DS);
	/* call to vfs_stat method it will fill stat variable */
	error = vfs_stat (processId, &stat);
	/* sets working space to old space which is userspace */
	set_fs(old_fs);

	/* test print if its correct time */
	if (stat.mtime.tv_sec > 0)
	{
		printk("elapsed|test start time: %lld\n", stat.mtime.tv_sec);
	}
	else
	{
		printk("elapsed|test start time error : %lld\n", stat.mtime.tv_sec);
		printk("elapsed|test start time error : please check pid\n");
		return -1;
	}

	/* call to getnstimeofday method it will fill current_time variable */
	getnstimeofday(&current_time);

	/* test print if its correct time */
	if (current_time.tv_sec > 0)
	{
		printk("elapsed|test current time: %ld\n", current_time.tv_sec);
	}
	else
	{
		printk("elapsed|test current time error: %ld\n", current_time.tv_sec);
		return -1;
	}

	/* result of the syscall */
	printk("elapsed|elapsed time: %lld seconds\n", current_time.tv_sec - stat.mtime.tv_sec);

	return 0;
}
