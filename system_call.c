/*fork_exec_wait.c*/
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char * argv[]){
  //arguments for ls, will run: ps -elf
  char * ls_args[3] = { "ps", "-elf", NULL} ;
  pid_t c_pid, pid;
  int status;
  c_pid = fork();
  if (c_pid == 0){
    /* CHILD */
    printf("Child: executing ps\n");
    //execute ls
    execvp( ls_args[0], ls_args);
    printf("Child: finished\n");//this statement will not be displayed
    //only get here if exec failed
    perror("execvp failed");
  }else if (c_pid > 0){
    /* PARENT */
    if( (pid = wait(&status)) < 0){
      perror("wait");
      _exit(1);
    }
    printf("Parent: finished\n");
  }else{
    perror("fork failed");
    _exit(1);
  }
  return 0; //return success
}

